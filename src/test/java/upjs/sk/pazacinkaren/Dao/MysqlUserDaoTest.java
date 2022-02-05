package upjs.sk.pazacinkaren.Dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import upjs.sk.pazacinkaren.Interfaces.UserDao;
import upjs.sk.pazacinkaren.Models.User;

class MysqlUserDaoTest {

	private UserDao userDao;
	private User userBoris;
	private User userAdam;
	private User userBianka;
	private User userSimon;
	
	public MysqlUserDaoTest() {
		DaoFactory.INSTANCE.testing();
		userDao = DaoFactory.INSTANCE.getUserDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		userBoris = new User("Boris", "Hamadej", "boris.hamadej@upjs.sk", 907916003, "borisko265", "alexjeBOH", 55230,
				1);
		userAdam = new User("Adam", "Kundracik", "adam.kundracik@upjs.sk", 907916003, "adamko123", "adamko", 5523230,
				1);
		userBianka = new User("Bianka", "Szepesiova", "bianka.szepesiova@upjs.sk", 915186969, "bianka1234", "bius",
				7562, 1);
		userSimon = new User("Simon", "Huraj", "simon.huraj@upjs.sk", 903158969, "simonko5667", "simine",
				7562, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	// ROWMAPPER JEDINECNE RIADKY.
	// RESULTEXCTRACTOR prepajacie tabulky.

	@Test
	void getAllTest() {
		userDao.saveUser(userBoris);
		List<User> users = userDao.getAll();
		assertNotNull(users);
		assertTrue(users.size() > 0);
		assertTrue(users.size() == 2);
		userDao.deleteUser(userBoris.getId());
	}

	@Test
	void getUserByLoginTest() {
		userDao.saveUser(userAdam);
		System.out.println("save" + userAdam.getId());
		User byLoginAndPassword = userDao.getUserByLogin(userAdam.getLogin(), userAdam.getPassword());
		assertNotNull(byLoginAndPassword.getName());
		assertNotNull(byLoginAndPassword.getSurname());
		assertNotNull(byLoginAndPassword.getEmail());
		assertNotNull(byLoginAndPassword.getIsicCardNumber());
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				userDao.getUserByLogin("ahojky", "1234");
			}
		});
	}
	
	@Test
	void getUserByIdTest() {
		userDao.saveUser(userSimon);
		User userTestId = userDao.getUserById(userSimon.getId());
		assertNotNull(userTestId.getEmail());
		assertNotNull(userTestId.getId());
		assertNotNull(userTestId.getIsicCardNumber());
		assertNotNull(userTestId.getLogin());
		assertNotNull(userTestId.getName());
		assertNotNull(userTestId.getNazovRole());
		assertNotNull(userTestId.getPassword());
		assertNotNull(userTestId);
		assertEquals(userTestId.getName(), userSimon.getName());
		assertEquals(userTestId.getSurname(), userSimon.getSurname());
		assertEquals(userTestId.getLogin(), userSimon.getLogin());
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				userDao.getUserById(-1L);
			}
		});
		userDao.deleteUser(userSimon.getId());
	}

	@Test
	void saveUserTest() {
		int initialSize = userDao.getAll().size();
		User newUser = new User("Alex", "Gajdos", "alex.gajdos@upjs.sk", 915186969, "alexik197", "simonMaMalyPenis",
				154663, 1);
		User savedUser1 = userDao.saveUser(newUser);
		assertEquals(savedUser1.getName(), newUser.getName());
		assertEquals(savedUser1.getSurname(), newUser.getSurname());
		assertNotNull(savedUser1.getId());
		List<User> all = userDao.getAll();
		assertEquals(initialSize + 1, all.size());

		boolean found = false;
		for (User user : all) {
			if (user.getId().equals(savedUser1.getId())) {
				found = true;
				assertEquals("Alex", user.getName());
				assertEquals("Gajdos", user.getSurname());
				break;
			}
		}
		assertTrue(found);
		userDao.deleteUser(savedUser1.getId());
		User user = new User();

		assertThrows(NullPointerException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				userDao.saveUser(user);
			}
		});
	}

	@Test
	void promoteUserTest() {
		userDao.promoteUser(userAdam);
		assertEquals(userDao.getUserByLogin(userAdam.getLogin(), userAdam.getPassword()).getRole(), 2);
	}

	@Test
	void demoteUserTest() {
		userDao.demoteUser(userAdam);
		assertEquals(userDao.getUserByLogin(userAdam.getLogin(), userAdam.getPassword()).getRole(), 1);
	}

	@Test
	void deleteUserTest() {
		User user = new User();
		user.setId(15L);
		assertTrue(user.getId() == 15L);
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				userDao.deleteUser(user.getId());
			}
		});
		User saved = userDao.saveUser(userBianka);
		assertNotNull(userBianka);
		User saved2 = userDao.deleteUser(userBianka.getId());
		assertEquals(saved.getName(), saved2.getName());
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				userDao.deleteUser(-1L);
			}
		});
	}

	@Test
	void getUserByNameTest() {
		User getUser = userDao.getUserByName(userAdam.getName());
		User tester = new User("Alexandra", "Romanova", "alexandra.romanova@upjs.sk", 302162602, "romanova",
				"ahojky123", 116163, 1);
		userDao.saveUser(tester);
		assertNotEquals(getUser.getName(), tester.getName());
		assertNotEquals(getUser, tester);
		userDao.deleteUser(tester.getId());
		assertNotNull(getUser);
		assertNotNull(getUser.getName());
		assertNotNull(getUser.getSurname());
		assertNotNull(getUser.getEmail());
		assertNotNull(getUser.getId());
		assertNotNull(getUser.getIsicCardNumber());
		assertNotNull(getUser.getNazovRole());
		assertNotNull(getUser.getTelNumber());
		assertNotNull(getUser.getRole());
		assertEquals(getUser.getName(), userAdam.getName());
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				userDao.getUserByName("ahoj");
			}
		});
	}
}