package upjs.sk.pazacinkaren.Models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class OrderTest {

	Order order;

	@BeforeEach
	void setUp() throws Exception {
		order = new Order();
		LocalDateTime date = LocalDateTime.now();
		order.setAddress("Medická 4");
		order.setIdPalacinka(1L);
		order.setId(2L);
		order.setIdUser(4L);
		order.setDate(date);
		System.out.println(order.getDate());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		assertFalse(order.getAddress().equals(null));
		assertTrue(order.getAddress().equals("Medická 4"));
		assertTrue(order.getAddress() != null);
		assertTrue(order.getId() != null);
		assertTrue(order.getIdPalacinka() != null);
		assertTrue(order.getIdUser() != null);
		assertTrue(order.getDate() != null);
		
		
	}

}
