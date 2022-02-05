package upjs.sk.pazacinkaren.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import upjs.sk.pazacinkaren.Interfaces.UserDao;
import upjs.sk.pazacinkaren.Models.User;

public class MysqlUserDao implements UserDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlUserDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<User> getAll() { // join s role tabulkou.
		String sql = "Select u.id,name,surname,email,tel_number,login,password,isicCardNumber, role_id, nazov from user u join role r on u.role_id = r.id;";
		return jdbcTemplate.query(sql, new UserRowMapper());
	}

	@Override
	public User getUserByLogin(String login, String password) throws EntityNotFoundException {
		String sql = "SELECT u.id, u.`name`, u.surname, u.email, u.tel_number, u.login, u.`password`, u.isicCardNumber, u.role_id, r.nazov FROM user u  join `role` r on u.role_id = r.id WHERE login = ? and `password` = ?;";
		try {
			// ak sme nasli takeho usera, ktory ma prislusne meno, tak ho
			// nasetujeme a vratime ho (otazniky v selecte sa nizssie nahradia za meno
			User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), login, password);
			return user;
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("User with login name: " + login + "not found in DB!", e);
		}
	}

	@Override
	public User getUserById(Long id) throws EntityNotFoundException {
		String sql = "SELECT u.id, u.`name`, u.surname, u.email, u.tel_number, u.login, u.`password`, u.isicCardNumber, u.role_id, r.nazov FROM user u  join `role` r on u.role_id = r.id WHERE u.id = ?;";
		try {
			// ak sme nasli takeho usera, ktory ma prislusne meno, tak ho
			// nasetujeme a vratime ho (otazniky v selecte sa nizssie nahradia za meno
			User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
			return user;
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("User with:  " + id + " not found in DB!", e);
		}
	}

	@Override
	public User saveUser(User user) throws EntityNotFoundException {
		// kontrolujeme, ci su vsetky textFieldy vyplnené
		if (user.getName().length() == 0 || user.getSurname().length() == 0 || user.getEmail().length() == 0
				|| user.getTelNumber() == 0 || user.getLogin().length() == 0 || user.getPassword().length() == 0
				|| user.getIsicCardNumber() == 0) {
			return null;
		}

		// ak je všetko vyplnené, okrem id (to sa pridava automaticky) zacneme
		// insertovat do databazy
		if (user.getId() == null) {
			if (user.getRole() == 0) {
				user.setRole(1);
			}

			// vybereme s ktorou tabulkou a v nej s ktorými stlpcami chceme pracovat
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("user");
			simpleJdbcInsert.usingColumns("name", "surname", "email", "tel_number", "login", "password",
					"isicCardNumber", "role_id");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");

			// nasetujeme hodnoty ktore budeme insertovat
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("name", user.getName());
			values.put("surname", user.getSurname());
			values.put("email", user.getEmail());
			values.put("tel_number", user.getTelNumber());
			values.put("login", user.getLogin());
			values.put("password", user.getPassword());
			values.put("isicCardNumber", user.getIsicCardNumber());
			values.put("role_id", user.getRole());

			// nasledujuci riadok nám urobí insert do databazy a vrati nam ID ktore bolo
			// pridelene
			long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
			user.setId(id);
		} else {
			String sql = "UPDATE user set name = ?, surname = ?, email = ?, tel_number = ?, login = ?, password = ?, isicCardNumber = ?, role_id = ? WHERE id = ?";
			int changed = jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getEmail(),
					user.getTelNumber(), user.getLogin(), user.getPassword(), user.getIsicCardNumber(), user.getRole(),
					user.getId());
			if (changed == 1) {
				return user;
			} else {
				throw new EntityNotFoundException("User" + user.getId() + "not found in our database system.");
			}
		}
		return user;
	}

	@Override
	public User promoteUser(User user) {
		String sql = "UPDATE `user` SET role_id = ? WHERE login = ?";
		jdbcTemplate.update(sql, DaoFactory.INSTANCE.getRoleDao().getRoleByName(Constants.admin), user.getLogin());

		return user;
	}

	@Override
	public User demoteUser(User user) {
		// 1 je bezny pouzivatel
		String sql = "UPDATE `user` SET role_id = ? WHERE login = ?";
		jdbcTemplate.update(sql, DaoFactory.INSTANCE.getRoleDao().getRoleByName(Constants.normal), user.getLogin());

		return user;
	}

	@Override
	public User deleteUser(Long id) throws EntityUndeletableException, EntityUndeletableException {

		User user = getUserById(id);

		try {
			String sql = "DELETE FROM `order` WHERE `user_id` = ?";
			jdbcTemplate.update(sql, id);

			// vymazanie z userov.
			String sql2 = "DELETE FROM `user` WHERE `id` = ?";
			int changed = jdbcTemplate.update(sql2, id);

			if (changed != 1) {
				throw new EntityNotFoundException("User with " + id + " not found.");
			}
		} catch (DataIntegrityViolationException e) {
			throw new EntityUndeletableException("User has orders with palacinkas on its list.");

		}
		return user;

	}

	@Override
	public User getUserByName(String name) {
		String sql = "SELECT u.id, u.`name`, u.surname, u.email, u.tel_number, u.login, u.`password`, u.isicCardNumber, u.role_id, r.nazov FROM user u  join `role` r on u.role_id = r.id  WHERE u.`name` = ?;";

		try {
			// ak sme nasli takeho usera, ktory ma prislusne meno, tak ho
			// nasetujeme a vratime ho (otazniky v selecte sa nizssie nahradia za meno
			User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), name);
			return user;

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("User with name: " + name + "not found in DB!", e);
		}
	}

	private class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			long id = rs.getLong("id");
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String email = rs.getString("email");
			int telNumber = rs.getInt("tel_number");
			String login = rs.getString("login");
			String password = rs.getString("password");
			int isicCardNumber = rs.getInt("isicCardNumber");
			int role = rs.getInt("role_id");
			String menoRoli = rs.getString("nazov");

			return new User(id, name, surname, email, telNumber, login, password, isicCardNumber, role, menoRoli);
		}
	}
}
