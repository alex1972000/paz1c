package upjs.sk.pazacinkaren.Models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PalacinkaTest {

	Palacinka palacinka1;
	Palacinka palacinka2;
	List<Palacinka> listPalaciniek;

	@BeforeEach
	void setUp() throws Exception {
		listPalaciniek = new ArrayList<Palacinka>();
		palacinka1 = new Palacinka();
		palacinka1.setId(1L);
		palacinka1.setDescription("Skvelá palacinka");
		palacinka1.setName("Ïžemová");
		palacinka1.setPrice(4.5);
		palacinka1.setCurrency("euro");
		palacinka1.setWeight(250);
		palacinka1.setWeightType("Malá");
		listPalaciniek.add(palacinka1);
		palacinka2 = new Palacinka();
		palacinka2.setId(3L);
		palacinka2.setDescription("Skvelá palacinka");
		palacinka2.setName("Èokoladová");
		palacinka2.setPrice(4.5);
		palacinka2.setCurrency("euro");
		palacinka2.setWeight(250);
		palacinka2.setWeightType("Malá");
		listPalaciniek.add(palacinka2);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		assertNotEquals(palacinka1, palacinka2);
		assertFalse(palacinka1.equals(palacinka2));
		assertTrue(listPalaciniek.size() > 0);
		Palacinka palacinka3 = new Palacinka();
		int sizeOfPalacinkaList = listPalaciniek.size();
		listPalaciniek.add(palacinka3);
		assertEquals(sizeOfPalacinkaList + 1, listPalaciniek.size());
		assertNotEquals(palacinka1.hashCode(), palacinka2.hashCode());
		
	}

}
