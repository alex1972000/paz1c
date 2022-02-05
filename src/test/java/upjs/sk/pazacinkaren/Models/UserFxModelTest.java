package upjs.sk.pazacinkaren.Models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class UserFxModelTest {

	private UserFxModel userFxModel;
	private User user;
	private User user2;

	@BeforeEach
	void setUp() throws Exception {
		userFxModel = new UserFxModel();
		userFxModel.setName("Jano");
		userFxModel.setSurname("Prvý");
		userFxModel.setEmail("jano.prvý@upjs.sk");
		userFxModel.setIsicCardNumber(15248620);
		userFxModel.setLogin("janko");
		userFxModel.setPassword("janko123");
		userFxModel.setRole(1);
		userFxModel.setTelNumber(915186969);

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		assertEquals(userFxModel.getName(), "Jano");
		assertEquals(userFxModel.getSurname(), "Prvý");
		assertEquals(userFxModel.getEmail(), "jano.prvý@upjs.sk");
		assertEquals(userFxModel.getIsicCardNumber(), 15248620);
		assertEquals(userFxModel.getLogin(), "janko");
		assertEquals(userFxModel.getPassword(), "janko123");
		assertEquals(userFxModel.getRole(), 1);
		assertEquals(userFxModel.getTelNumber(), 915186969);
	}

	@Test
	void setUser() {
		user = new User();
		user2 = new User();
		user.setName(userFxModel.getName());
		user.setSurname(userFxModel.getSurname());
		user.setEmail(userFxModel.getEmail());
		user.setIsicCardNumber(userFxModel.getIsicCardNumber());
		user.setLogin(userFxModel.getLogin());
		user.setPassword(userFxModel.getPassword());
		user.setRole(userFxModel.getRole());
		user.setTelNumber(userFxModel.getTelNumber());
		user2.setName(user.getName());
		assertNotNull(user);
		assertNotNull(user2.getName());

		// zdroj: https://www.adam-bien.com/roller/abien/entry/java_fx_2_data_binding
		String expected = user.getName();
		StringProperty from = new SimpleStringProperty();
		StringProperty to = new SimpleStringProperty();
		to.bind(from);
		from.set(expected);
		String actual = to.get();
		assertTrue(actual.equals(expected));
		from.set(user.getSurname());
		String actual2 = to.get();
		assertTrue(actual2.equals(user.getSurname()));
	}

	void getUser() {
		User user3 = new User();
		assertNotNull(user.getName());
		assertNotNull(user.getSurname());
		assertNotNull(user.getEmail());
		assertNotNull(user.getIsicCardNumber());
		assertNotNull(user.getLogin());
		assertNotNull(user.getPassword());
		assertNotNull(user.getRole());
		assertNotNull(user.getTelNumber());
		assertEquals(user.getName(), user2.getName());
		assertEquals(userFxModel, user);
		assertNull(user2.getLogin());
		assertNotNull(user);
		assertNotNull(user2);
		assertNull(user3);
	}
}
