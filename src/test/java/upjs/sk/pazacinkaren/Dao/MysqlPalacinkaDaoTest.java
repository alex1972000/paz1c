package upjs.sk.pazacinkaren.Dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import upjs.sk.pazacinkaren.Interfaces.PalacinkaDao;
import upjs.sk.pazacinkaren.Models.Palacinka;

class MysqlPalacinkaDaoTest {

	private PalacinkaDao palacinkaDao;
	private Palacinka palacinka1;
	private Palacinka palacinka2;
	private Palacinka palacinka3;
	private Palacinka palacinka4;

	public MysqlPalacinkaDaoTest() {
		DaoFactory.INSTANCE.testing();
		palacinkaDao = DaoFactory.INSTANCE.getPalacinkaDao();
		palacinka1 = new Palacinka("cokoladová", "poleva palacinka", 150, "L", 5, "euro");
		palacinka2 = new Palacinka("bananová", "banany poleva", 250, "XL", 5, "euro");
		palacinka3 = new Palacinka("jahodová", "jahody slahacka sladka palacinka", 350, "XL", 6, "euro");
		palacinka4 = new Palacinka("cucoriedkova", "cucoriedky slahacka sladka palacinka", 370, "XL", 5.7, "euro");
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getAllTest() {
		palacinkaDao.savePalacinka(palacinka1);
		List<Palacinka> palacinky = palacinkaDao.getAll();
		assertNotNull(palacinky);
		assertTrue(palacinky.size() > 0);
		palacinkaDao.deletePalacinka(palacinka1);

	}

	@Test
	void savePalacinkaTest() {
		int initialSize = palacinkaDao.getAll().size();
		Palacinka palacinka = new Palacinka("ananasová", "ananas slahacka sladka palacinka", 370, "XL", 5.5, "euro");
		Palacinka saved = palacinkaDao.savePalacinka(palacinka);
		assertEquals(palacinka.getName(), saved.getName());
		assertEquals(palacinka.getCurrency(), saved.getCurrency());
		assertEquals(palacinka.getDescription(), saved.getDescription());
		assertEquals(palacinka.getPrice(), saved.getPrice());
		assertEquals(palacinka.getWeight(), saved.getWeight());
		assertEquals(palacinka.getWeightType(), saved.getWeightType());
		assertNotNull(palacinka.getId());
		List<Palacinka> all = palacinkaDao.getAll();
		assertEquals(initialSize + 1, all.size());

		boolean found = false;
		for (Palacinka palacinkos : all) {
			if (palacinkos.getId().equals(saved.getId())) {
				found = true;
				assertEquals("ananasová", palacinkos.getName());
				assertEquals("XL", palacinkos.getWeightType());
				break;
			}
		}
		assertTrue(found);
		palacinkaDao.deletePalacinka(saved);

		Palacinka palacinka2 = new Palacinka();

		assertThrows(NullPointerException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				palacinkaDao.savePalacinka(palacinka2);
			}
		});
	}

	@Test
	void getByNameTest() {
		 palacinkaDao.savePalacinka(palacinka2);
		 Palacinka getPalacinka = palacinkaDao.getByName(palacinka2.getName());
		 Palacinka tester = palacinkaDao.savePalacinka(palacinka3);
		 assertNotEquals(getPalacinka.getName(), tester.getName());
		 assertNotEquals(getPalacinka, tester);
		 assertNotNull(getPalacinka.getId());
		 assertNotNull(getPalacinka.getCurrency());
		 assertNotNull(getPalacinka.getDescription());
		 assertNotNull(getPalacinka.getName());
		 assertNotNull(getPalacinka.getPrice());
		 assertNotNull(getPalacinka.getWeight());
		 assertNotNull(getPalacinka.getWeightType());
		 palacinkaDao.deletePalacinka(palacinka2);
		 palacinkaDao.deletePalacinka(tester);
		 assertThrows(EntityNotFoundException.class, new Executable() {
				@Override
				public void execute() throws Throwable {
					palacinkaDao.getByName("AHojkova");
				}
			});
	}

	@Test
	void deletePalacinkaTest() {
		Palacinka saved = new Palacinka("cokoladová", "poleva palacinka", 150, "L", 5, "euro");
		Palacinka test = palacinkaDao.savePalacinka(saved);
		assertNotNull(saved);
		assertNotNull(saved.getId());
		assertEquals(saved, test);
		System.out.println(palacinkaDao.getAll().size());
		assertTrue(palacinkaDao.getAll().size() > 2);
		Palacinka saved2 = palacinkaDao.deletePalacinka(saved);
		assertEquals(saved2, saved);
		assertTrue(palacinkaDao.getAll().size() == 2);
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				palacinkaDao.deletePalacinka(new Palacinka());
			}
		});

	}
	@Test
	void getPalacinkaById() {
		Palacinka test1 = palacinkaDao.savePalacinka(palacinka4);
		Palacinka palacinkaTestId = palacinkaDao.getPalacinkaById(palacinka4.getId());
		assertNotNull(palacinkaTestId.getName());
		assertNotNull(palacinkaTestId.getCurrency());
		assertNotNull(palacinkaTestId.getDescription());
		assertNotNull(palacinkaTestId.getPrice());
		assertNotNull(palacinkaTestId.getWeightType());
		assertNotNull(palacinkaTestId.getWeight());
		assertEquals(test1.getName(), palacinkaTestId.getName());
		assertEquals(test1.getPrice(), palacinkaTestId.getPrice());
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				palacinkaDao.getPalacinkaById(-1L);
			}
		});
		palacinkaDao.deletePalacinka(palacinka4);
	}
}
