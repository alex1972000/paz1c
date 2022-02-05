package upjs.sk.pazacinkaren.Models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleTest {
	
	Role role;
	Role role2;


	@BeforeEach
	void setUp() throws Exception {
		role = new Role(1, "normal");
		role2 = new Role(2, "admin");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		assertNotEquals(role, role2);
		assertEquals(role.getId(), 1);
		assertEquals(role2.getId(), 2);
		assertEquals(role.getNazov(), "normal");
		assertEquals(role2.getNazov(), "admin");
		assertNotEquals(role.getNazov(), role2.getNazov());
		assertNotEquals(role.getId(), role2.getId());
	}

}
