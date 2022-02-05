package upjs.sk.pazacinkaren.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import upjs.sk.pazacinkaren.Interfaces.RoleDao;
import upjs.sk.pazacinkaren.Models.Role;

public class MysqlRoleDao implements RoleDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlRoleDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Role getById(long id) throws EntityNotFoundException{
		String sql = "SELECT id, nazov FROM role WHERE id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new RoleRowMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Role with id" + id + "is not in our DS.");
		}
	}

	private class RoleRowMapper implements RowMapper<Role> {
		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			long id = rs.getLong("id");
			String name = rs.getString("nazov");
			return new Role(id, name);
		}
	}

	@Override
	public Role saveRole(Role role) throws EntityNotFoundException {
		if (role.getNazov().length() == 0) {
			return null;
		}

		if (role.getId() == null) {
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("role");
			simpleJdbcInsert.usingColumns("nazov");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("nazov", role.getNazov());
			long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
			role.setId(id);

		} else {
			String sql = "UPDATE `role` SET nazov = ? WHERE id = ?";
			int changed = jdbcTemplate.update(sql, role.getNazov(), role.getId());

			if (changed == 1) {
				return role;
			} else {
				throw new EntityNotFoundException("Role with id: " + role.getId() + " is not found in our DB");
			}
		}
		return role;
	}

	@Override
	public Role deleteRole(Role role) throws EntityNotFoundException {
		String sql = "DELETE FROM `user` WHERE `role_id` = ?;";
		jdbcTemplate.update(sql, role.getId());
		String sql2 = "DELETE FROM `role` WHERE `id` = ?;";
		int changed = jdbcTemplate.update(sql2, role.getId());
		
		if(changed != 1) {
			throw new EntityNotFoundException("Role with id" + role.getId() + "do not exist and cannot be deleted.");
		}
		return role;
	}

	@Override
	public Long getRoleByName(String nazov) throws EntityNotFoundException{

		String sql = "SELECT id, nazov FROM role WHERE nazov = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new RoleRowMapper(), nazov).getId();
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Role with name:" + nazov + "not found in our DB.");
		}
	}

	@Override
	public List<Role> getAll() {
		String sql = "SELECT id, nazov FROM `role`;";
		return jdbcTemplate.query(sql, new RoleRowMapper());
	}
}
