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
import upjs.sk.pazacinkaren.Interfaces.PalacinkaDao;
import upjs.sk.pazacinkaren.Models.Palacinka;

public class MysqlPalacinkaDao implements PalacinkaDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlPalacinkaDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Palacinka> getAll() {
		String sql = "SELECT id, name, description, weight, weightType, price, currency FROM palacinka ORDER BY id;";
		List<Palacinka> palacinkaList = jdbcTemplate.query(sql, new RowMapper<Palacinka>() {

			@Override
			public Palacinka mapRow(ResultSet rs, int rowNum) throws SQLException {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				int weight = rs.getInt("weight");
				String weightType = rs.getString("weightType");
				double price = rs.getDouble("price");
				String currency = rs.getString("currency");
				return new Palacinka(id, name, description, weight, weightType, price, currency);
			}
		});
		return palacinkaList;
	}

	@Override
	public Palacinka savePalacinka(Palacinka palacinka) throws EntityNotFoundException{

		if (palacinka.getName().length() == 0 || palacinka.getDescription().length() == 0 || palacinka.getWeight() == 0
				|| palacinka.getWeightType().length() == 0 || palacinka.getPrice() == 0
				|| palacinka.getCurrency().length() == 0) {
			return null;
		}

		if (palacinka.getId() == null) {

			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("palacinka");
			simpleJdbcInsert.usingColumns("name", "description", "weight", "weightType", "price", "currency");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");

			Map<String, Object> values = new HashMap<String, Object>();
			values.put("name", palacinka.getName());
			values.put("description", palacinka.getDescription());
			values.put("weight", palacinka.getWeight());
			values.put("weightType", palacinka.getWeightType());
			values.put("price", palacinka.getPrice());
			values.put("currency", palacinka.getCurrency());

			long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
			palacinka.setId(id);
		} else {
			String sql = "UPDATE palacinka SET name = ?, description = ?,  weight = ?, weightType = ?, price = ?, currency = ? WHERE id = ?";

			int changed = jdbcTemplate.update(sql, palacinka.getName(), palacinka.getDescription(),
					palacinka.getWeight(), palacinka.getWeightType(), palacinka.getPrice(), palacinka.getCurrency(),
					palacinka.getId());
			if (changed == 1) {
				return palacinka;
			} else {
				throw new EntityNotFoundException("Palacinka with id:" + palacinka.getId() + "not exist in our DB.");
			}

		}
		return palacinka;
	}

	@Override
	public Palacinka getByName(String palacinkaName) throws EntityNotFoundException {
		String sql = "SELECT id, name, description, weight, weightType, price, currency FROM palacinka WHERE name = ?;";

		try {
			// ak sme nasli taku palacinku, ktora ma prislusne meno, tak ju
			// nasetujeme a vratime ju (otaznik v selecte nizssie nahradi meno)
			Palacinka palacinka = jdbcTemplate.queryForObject(sql, new RowMapper<Palacinka>() {

				public Palacinka mapRow(ResultSet rs, int rowNum) throws SQLException {
					Palacinka palacinka = new Palacinka();
					palacinka.setId(rs.getLong("id"));
					palacinka.setName(rs.getString("name"));
					palacinka.setDescription(rs.getString("description"));
					palacinka.setWeight(rs.getInt("weight"));
					palacinka.setWeightType(rs.getString("weightType"));
					palacinka.setPrice(rs.getDouble("price"));
					palacinka.setCurrency(rs.getString("currency"));
					return palacinka;
				}
			}, palacinkaName);
			return palacinka;
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Palacinka with name: " + palacinkaName + "not exist in our DB.");
		}
	}

	@Override
	public Palacinka deletePalacinka(Palacinka palacinka) throws EntityNotFoundException {
		String sql = "DELETE FROM `order_has_palacinka` WHERE `palacinka_id` = ?;";
		jdbcTemplate.update(sql, palacinka.getId());

		String sql2 = "DELETE FROM `palacinka` WHERE `id` = ?;";
		int changed  = jdbcTemplate.update(sql2, palacinka.getId());
		
		if(changed != 1) {
			throw new EntityNotFoundException("Palacinka with " + palacinka.getId() + " not found.");
		}
		return palacinka;
	}

	@Override
	public Palacinka getPalacinkaById(long palacinkaId) throws EntityNotFoundException {
		String sql = "SELECT id, name, description, weight, weightType, price, currency FROM palacinka WHERE id = ?;";

		try {
			// ak sme nasli taku palacinku, ktora ma prislusne meno, tak ju
			// nasetujeme a vratime ju (otaznik v selecte nizssie nahradi meno)
			Palacinka palacinka = jdbcTemplate.queryForObject(sql, new RowMapper<Palacinka>() {

				public Palacinka mapRow(ResultSet rs, int rowNum) throws SQLException {
					Palacinka palacinka = new Palacinka();
					palacinka.setId(rs.getLong("id"));
					palacinka.setName(rs.getString("name"));
					palacinka.setDescription(rs.getString("description"));
					palacinka.setWeight(rs.getInt("weight"));
					palacinka.setWeightType(rs.getString("weightType"));
					palacinka.setPrice(rs.getDouble("price"));
					palacinka.setCurrency(rs.getString("currency"));
					return palacinka;
				}
			}, palacinkaId);
			return palacinka;
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Palacinka with name: " + palacinkaId + "not exist in our DB.");
		}
	}
}
