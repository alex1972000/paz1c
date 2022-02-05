package upjs.sk.pazacinkaren.Models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class PalacinkaFxModelTest {

	private PalacinkaFxModel palacinkaFxModel;
	private Palacinka palacinka;
	private Palacinka palacinka2;

	@BeforeEach
	void setUp() throws Exception {
		palacinkaFxModel = new PalacinkaFxModel();
		palacinkaFxModel.setName("Vanilkova");
		palacinkaFxModel.setDescription("vanilka, ovocie");
		palacinkaFxModel.setWeight(400);
		palacinkaFxModel.setWeightType("L");
		palacinkaFxModel.setPrice(4);
		palacinkaFxModel.setCurrency("euro");

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		assertEquals(palacinkaFxModel.getName(), "Vanilkova");
		assertEquals(palacinkaFxModel.getDescription(), "vanilka, ovocie");
		assertEquals(palacinkaFxModel.getWeight(), 400);
		assertEquals(palacinkaFxModel.getWeightType(), "L");
		assertEquals(palacinkaFxModel.getPrice(), 4);
		assertEquals(palacinkaFxModel.getCurrency(), "euro");
	}

	@Test
	void setUser() {
		palacinka = new Palacinka();
		palacinka2 = new Palacinka();
		palacinka.setName(palacinkaFxModel.getName());
		palacinka.setDescription(palacinkaFxModel.getDescription());
		palacinka.setWeight(palacinkaFxModel.getWeight());
		palacinka.setWeightType(palacinkaFxModel.getWeightType());
		palacinka.setPrice(palacinkaFxModel.getPrice());
		palacinka.setCurrency(palacinkaFxModel.getCurrency());
		palacinka2.setName(palacinka.getName());
		assertNotNull(palacinka);
		assertNotNull(palacinka2.getName());

		// zdroj: https://www.adam-bien.com/roller/abien/entry/java_fx_2_data_binding
		String expected = palacinka.getName();
		StringProperty from = new SimpleStringProperty();
		StringProperty to = new SimpleStringProperty();
		to.bind(from);
		from.set(expected);
		String actual = to.get();
		assertTrue(actual.equals(expected));
		from.set(palacinka.getName());
		String actual2 = to.get();
		assertTrue(actual2.equals(palacinka.getName()));
	}

	void getUser() {
		Palacinka palacinka3 = new Palacinka();
		assertNotNull(palacinka.getName());
		assertNotNull(palacinka.getDescription());
		assertNotNull(palacinka.getWeight());
		assertNotNull(palacinka.getWeightType());
		assertNotNull(palacinka.getPrice());
		assertNotNull(palacinka.getCurrency());
		assertEquals(palacinka.getName(), palacinka2.getName());
		assertEquals(palacinkaFxModel, palacinka);
		assertNull(palacinka2.getName());
		assertNotNull(palacinka);
		assertNotNull(palacinka2);
		assertNull(palacinka3);
	}
}
