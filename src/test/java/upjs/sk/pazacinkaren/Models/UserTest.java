package upjs.sk.pazacinkaren.Models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class UserTest {

	User user1;
	User user2;

	List<User> listUserov;

	@BeforeEach
	void setUp() throws Exception {
		listUserov = new ArrayList<User>();
		user1 = new User();
		user1.setId(1L);
		user1.setName("Jano");
		user1.setSurname("Kovalkoviè");
		user1.setEmail("jano.kovalkovic@upjs.sk");
		user1.setIsicCardNumber(15152530);
		user1.setTelNumber(915186969);
		user1.setPassword("1234");
		user2 = new User();
		user2.setId(2L);
		user2.setName("Patka");
		user2.setSurname("Kovalkovièová");
		user2.setEmail("patka.kovalkovicova@upjs.sk");
		user2.setIsicCardNumber(26156330);
		user2.setTelNumber(915186969);
		user2.setPassword("4321");
		listUserov.add(user1);
		listUserov.add(user2);

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		assertNotNull(listUserov);
		assertTrue(listUserov.size() > 0);
		assertNotEquals(user1, user2);
		assertTrue(user1.getTelNumber() == user2.getTelNumber());
		User user3 = new User();
		int sizeOfUserList = listUserov.size();
		listUserov.add(user3);
		assertEquals(sizeOfUserList + 1, listUserov.size());
		assertNull(user3.getName());
	}
}
