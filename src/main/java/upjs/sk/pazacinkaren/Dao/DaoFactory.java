package upjs.sk.pazacinkaren.Dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {

	INSTANCE;

	private MysqlUserDao mysqlUserDao;
	private MysqlOrderDao mysqlOrderDao;
	private MysqlPalacinkaDao mysqlPalacinkaDao;
	private MysqlRoleDao mysqlRoleDao;
	private JdbcTemplate jdbcTemplate;
	private boolean testing = false;

	public MysqlRoleDao getRoleDao() {
		if (mysqlRoleDao == null) {
			mysqlRoleDao = new MysqlRoleDao(getJdbcTemplate());
		}
		return mysqlRoleDao;
	}

	// napojenie sa na tabulku palacinka
	public MysqlPalacinkaDao getPalacinkaDao() {
		if (mysqlPalacinkaDao == null) {
			mysqlPalacinkaDao = new MysqlPalacinkaDao(getJdbcTemplate());
		}
		return mysqlPalacinkaDao;
	}

	// napojenie sa na tabulku order
	public MysqlOrderDao getOrderDao() {
		if (mysqlOrderDao == null) {
			mysqlOrderDao = new MysqlOrderDao(getJdbcTemplate());
		}
		return mysqlOrderDao;
	}

	// napojenie sa na tabulku Users
	public MysqlUserDao getUserDao() {
		if (mysqlUserDao == null) {
			mysqlUserDao = new MysqlUserDao(getJdbcTemplate());
		}
		return mysqlUserDao;
	}

	public void testing() {
		this.testing = true;
	}

	// napojenie databazy s projektom
	public JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("PAZacinkaren_admin");
			dataSource.setPassword("admin123.");
			if (testing) {
				dataSource.setUrl("jdbc:mysql://localhost:3306/pazacinkaren_test?serverTimezone=Europe/Bratislava");
			} else {
				dataSource.setUrl("jdbc:mysql://localhost:3306/pazacinkaren?serverTimezone=Europe/Bratislava");
			}

			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}

}
