package upjs.sk.pazacinkaren.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import upjs.sk.pazacinkaren.Interfaces.OrderDao;
import upjs.sk.pazacinkaren.Interfaces.PalacinkaDao;
import upjs.sk.pazacinkaren.Models.Order;
import upjs.sk.pazacinkaren.Models.Palacinka;

public class MysqlOrderDao implements OrderDao {

	private JdbcTemplate jdbcTemplate;
	private PalacinkaDao palacinkaDao;

	public MysqlOrderDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		palacinkaDao = DaoFactory.INSTANCE.getPalacinkaDao();

	}

	@Override
	public Order saveOrder(Order order) throws EntityNotFoundException {
		if (order.getId() != null) {
			String sql = "UPDATE `order` SET date = ?, address = ? , user_id = ? WHERE id = ?;";
			int changed = jdbcTemplate.update(sql, order.getDate(), order.getAddress(), order.getIdUser(),
					order.getId());
			insertPalacinkas(order.getKusyPalaciniek(), order.getId());
			if (changed == 1) {
				return order;
			} else {
				throw new EntityNotFoundException("Order" + order.getId() + "not found in our database system.");
			}

		} else {
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("`order`");
			simpleJdbcInsert.usingColumns("date", "address", "user_id");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");

			Map<String, Object> values = new HashMap<String, Object>();
			values.put("date", order.getDate());
			values.put("address", order.getAddress());
			values.put("user_id", order.getIdUser());

			long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
			order.setId(id);
			insertPalacinkas(order.getKusyPalaciniek(), order.getId());
		}

		return order;
	}

	private void insertPalacinkas(Map<Palacinka, Integer> palacinkaList, Long idOrder) {
		if (palacinkaList.size() != 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO `order_has_palacinka` (`order_id`, `palacinka_id`, `count`) VALUES ");
			for (Palacinka palacinka : palacinkaList.keySet()) {
				sb.append("(").append(idOrder).append(',').append(palacinka.getId()).append(",")
						.append(palacinkaList.get(palacinka)).append("),");
			}
			System.out.println(sb.toString());
			String sql = sb.substring(0, sb.length() - 1);
			System.out.println(sql);
			jdbcTemplate.update(sql);
		}
	}

	@Override
	public List<Order> getAllForHistory() {
		String sql = "SELECT o.id, o.date, o.address, o.user_id, oh.palacinka_id, oh.count from `order` as o  join order_has_palacinka oh on o.id = oh.order_id WHERE o.date < now()";

		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Order>>() {
			@Override
			public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Order> orders = new ArrayList<>();
				List<Palacinka> palacinkas = DaoFactory.INSTANCE.getPalacinkaDao().getAll();
				Map<Long, Palacinka> mappedPalacinkas = new HashMap<>();
				for (Palacinka palacinka : palacinkas) {
					mappedPalacinkas.put(palacinka.getId(), palacinka);
				}
				Order order = null;
				while (rs.next()) {
					Long id = rs.getLong("id");
					if (order == null || order.getId() != id) {
						LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
						String address = rs.getString("address");
						Long user_id = rs.getLong("user_id");
						order = new Order(id, date, address, user_id);
						orders.add(order);
					}
					Long palacinkaId = rs.getLong("palacinka_id");
					int count = rs.getInt("count");
					if (palacinkaId != null && count > 0)
						order.getKusyPalaciniek().put(mappedPalacinkas.get(palacinkaId), count);
				}
				return orders;
			}

		});
	}

	@Override
	public List<Order> getAll() {
		String sql = "SELECT o.id, o.date, o.address, o.user_id, oh.palacinka_id, oh.count from `order` as o  join order_has_palacinka oh on o.id = oh.order_id";

		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Order>>() {
			@Override
			public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Order> orders = new ArrayList<>();
				List<Palacinka> palacinkas = DaoFactory.INSTANCE.getPalacinkaDao().getAll();
				Map<Long, Palacinka> mappedPalacinkas = new HashMap<>();
				for (Palacinka palacinka : palacinkas) {
					mappedPalacinkas.put(palacinka.getId(), palacinka);
				}
				Order order = null;
				while (rs.next()) {
					Long id = rs.getLong("id");
					if (order == null || order.getId() != id) {
						LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
						String address = rs.getString("address");
						Long user_id = rs.getLong("user_id");
						order = new Order(id, date, address, user_id);
						orders.add(order);
					}
					Long palacinkaId = rs.getLong("palacinka_id");
					int count = rs.getInt("count");
					if (palacinkaId != null && count > 0)
						order.getKusyPalaciniek().put(mappedPalacinkas.get(palacinkaId), count);
				}
				return orders;
			}

		});
	}

	@Override
	public boolean deleteOrder(long orderID) throws EntityNotFoundException {
		String sql1 = "DELETE FROM order_has_palacinka WHERE order_id = ?;";
		jdbcTemplate.update(sql1, orderID);

		String sql2 = "DELETE FROM `order` WHERE `id` = ?;";
		int changed = jdbcTemplate.update(sql2, orderID);
		if (changed != 1) {
			throw new EntityNotFoundException("Order with id : " + orderID + "do not exists");
		}
		return changed == 1;
	}

	@Override
	public List<Order> getOrderById(long id) throws EntityNotFoundException {
		String sql = "SELECT o.`id`, o.`date`, o.address, o.user_id, op.palacinka_id, op.count FROM `order` o JOIN order_has_palacinka op on o.id = op.order_id WHERE `id` = "
				+ id;
		try {
			return jdbcTemplate.query(sql, new ResultSetExtractor<List<Order>>() {

				@Override
				public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Order> objednavka = new ArrayList<>();
					Order order = null;
					while (rs.next()) {
						long id = rs.getLong("id");
						LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
						String address = rs.getString("address");
						long palacinka_id = rs.getLong("palacinka_id");
						long user_id = rs.getLong("user_id");
						int count = rs.getInt("count");
						Map<Palacinka, Integer> kusyPalaciniek = new HashMap<>();
						Palacinka palacinka = palacinkaDao.getPalacinkaById(palacinka_id);
						kusyPalaciniek.put(palacinka, count);

						order = new Order(id, date, address, palacinka_id, user_id, kusyPalaciniek);
						objednavka.add(order);
					}
					return objednavka;
				}

			});

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Order with: " + id + "not found in our DB.");
		}
	}
}
