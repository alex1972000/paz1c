package upjs.sk.pazacinkaren.Dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import upjs.sk.pazacinkaren.Interfaces.OrderDao;
import upjs.sk.pazacinkaren.Interfaces.PalacinkaDao;
import upjs.sk.pazacinkaren.Interfaces.UserDao;
import upjs.sk.pazacinkaren.Models.Order;
import upjs.sk.pazacinkaren.Models.Palacinka;

class MysqlOrderDaoTest {

	private PalacinkaDao palacinkaDao;
	private UserDao userDao;
	private OrderDao orderDao;
	private Order order1;
	private Order order2;
	private Order order3;
	private Order order4;
	private Order order5;

	public MysqlOrderDaoTest() {
		DaoFactory.INSTANCE.testing();
		orderDao = DaoFactory.INSTANCE.getOrderDao();
		palacinkaDao = DaoFactory.INSTANCE.getPalacinkaDao();
		userDao = DaoFactory.INSTANCE.getUserDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		order1 = new Order(LocalDateTime.now(), "berlinska20", 26L);
		order2 = new Order(LocalDateTime.now(), "kosicka15", 26L);
		order3 = new Order(LocalDateTime.now(), "michalovska5", 26L);
		order4 = new Order(LocalDateTime.of(2021, 11, 7, 21, 00, 15), "borisova3", 26L);
		order5 = new Order(LocalDateTime.of(2021, 12, 7, 20, 00, 15), "alexova4", 26L);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void saveOrderTest() {
		int initialSize = orderDao.getAll().size();
		Order newOrder = new Order(LocalDateTime.now(), "bruselka15", 26L);
		Map<Palacinka, Integer> objednavka = new HashMap<>();
		objednavka.put(palacinkaDao.getAll().get(0), 1);
		newOrder.setKusyPalaciniek(objednavka);
		Order savedOrder1 = orderDao.saveOrder(newOrder);
		assertEquals(savedOrder1.getDate(), newOrder.getDate());
		assertEquals(savedOrder1.getIdUser(), newOrder.getIdUser());
		assertNotNull(savedOrder1.getId());
		List<Order> all = orderDao.getAll();
		assertEquals(initialSize + 1, all.size());

		boolean found = false;
		for (Order order : all) {
			if (order.getId().equals(savedOrder1.getId())) {
				found = true;
				assertEquals("bruselka15", order.getAddress());
				assertEquals(newOrder.getIdUser(), order.getIdUser());
				break;
			}
		}
		assertTrue(found);
		assertThrows(EntityUndeletableException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				userDao.deleteUser(savedOrder1.getIdUser());
			}
		});
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				Order order = new Order();
				order.setId(-15L);
				orderDao.saveOrder(order);
			}
		});
		orderDao.deleteOrder(savedOrder1.getId());
		System.out.println("1 done");
	}

	@Test
	void getAll() {
		Map<Palacinka, Integer> objednavka = new HashMap<>();
		objednavka.put(palacinkaDao.getAll().get(0), 5);
		objednavka.put(palacinkaDao.getAll().get(1), 3);
		order1.setKusyPalaciniek(objednavka);
		orderDao.saveOrder(order1);
		List<Order> orders = orderDao.getAll();
		assertNotNull(orders);
		assertTrue(orders.size() > 0);
		// assertTrue(orders.size() == 1);
		orderDao.deleteOrder(order1.getId());
		System.out.println("2 done");
	}

	@Test
	void getAllForHistoryTest() {
		Map<Palacinka, Integer> objednavka1 = new HashMap<>();
		objednavka1.put(palacinkaDao.getAll().get(0), 10);
		objednavka1.put(palacinkaDao.getAll().get(1), 12);
		order4.setKusyPalaciniek(objednavka1);
		orderDao.saveOrder(order4);
		System.out.println(order4);
		Map<Palacinka, Integer> objednavka = new HashMap<>();
		objednavka.put(palacinkaDao.getAll().get(0), 7);
		objednavka.put(palacinkaDao.getAll().get(1), 5);
		order5.setKusyPalaciniek(objednavka);
		orderDao.saveOrder(order5);
		List<Order> BackList = orderDao.getAllForHistory();
		assertNotNull(BackList);
		assertTrue(BackList.size() > 0);
		orderDao.deleteOrder(order4.getId());
		orderDao.deleteOrder(order5.getId());

	}

	@Test
	void deleteOrderTest() {
		Order order = new Order();
		order.setId(-15L);
		assertTrue(order.getId() == -15L);
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				userDao.deleteUser(order.getId());
			}
		});
		@SuppressWarnings("unused")
		Order saved = orderDao.saveOrder(order2);
		assertNotNull(order2);
		boolean saved2 = orderDao.deleteOrder(order2.getId());
		assertTrue(saved2);
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				orderDao.deleteOrder(-1L);
			}
		});
		System.out.println("4 done");
	}

	@Test
	void getOrderById() {
		Map<Palacinka, Integer> objednavka = new HashMap<>();
		objednavka.put(palacinkaDao.getAll().get(0), 5);
		objednavka.put(palacinkaDao.getAll().get(1), 3);
		order3.setKusyPalaciniek(objednavka);
		orderDao.saveOrder(order3);
		System.out.println("SAHSJAJOSAKSJSOAKSPAJOPSKPAKSOP" + order3);
		List<Order> list = orderDao.getOrderById(order3.getId());
		for (Order savedbyId : list) {
			System.out.println("scammas");
			assertNotNull(savedbyId);
			assertNotNull(savedbyId.getAddress());
			assertNotNull(savedbyId.getDate());
			assertNotNull(savedbyId.getIdUser());
			assertEquals(savedbyId.getIdUser(), order3.getIdUser());
		}
		orderDao.deleteOrder(order3.getId());
		System.out.println("5 done");
	}

}
