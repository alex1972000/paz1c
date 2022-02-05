package upjs.sk.pazacinkaren.Dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import upjs.sk.pazacinkaren.Interfaces.RoleDao;
import upjs.sk.pazacinkaren.Models.Role;

class MysqlRoleDaoTest {

	private RoleDao roleDao;
	private Role role1;
	private Role role2;
	private Role role3;
	private Role role4;

	public MysqlRoleDaoTest() {
		DaoFactory.INSTANCE.testing();
		roleDao = DaoFactory.INSTANCE.getRoleDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		role1 = new Role("superAdmin");
		role2 = new Role("visitor");
		role3 = new Role("majitel");
		role4 = new Role("TeplyBrat");

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getByIdtest() {
		Role saved = roleDao.saveRole(role1);
		assertNotNull(role1);
		assertNotNull(role1.getNazov());
		assertNotNull(role1.getId());
		assertNotNull(roleDao.getById(role1.getId()));
		assertEquals(saved, role1);
		assertEquals(saved.getId(), role1.getId());
		assertEquals(role1.getNazov(), saved.getNazov());
		roleDao.deleteRole(role1);
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				roleDao.getById(-1L);
			}
		});
	}

	@Test
	void saveRoleTest() {
		int initialSize = roleDao.getAll().size();
		Role newRole = new Role("superTEST");
		Role savedRole = roleDao.saveRole(newRole);
		assertEquals(newRole.getNazov(), savedRole.getNazov());
		assertNotNull(savedRole.getId());
		List<Role> all = roleDao.getAll();
		assertEquals(initialSize + 1, all.size());

		boolean found = false;
		for (Role role : all) {
			if (role.getId().equals(savedRole.getId())) {
				found = true;
				assertEquals("superTEST", role.getNazov());
				break;
			}
		}
		assertTrue(found);
		roleDao.deleteRole(savedRole);
		Role role = new Role();
		assertThrows(NullPointerException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				roleDao.saveRole(role);
			}
		});
	}

	@Test
	void deleteRoleTest() {
		Role save = roleDao.saveRole(role2);
		assertNotNull(save);
		assertNotNull(role2);
		assertEquals(save, role2);
		Role saved2 = roleDao.deleteRole(role2);
		assertEquals(save, saved2);
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				roleDao.deleteRole(new Role());
			}
		});
	}

	@Test
	void getRoleByNazovTest() {
		Role saved = roleDao.saveRole(role3);
		assertNotNull(roleDao.getRoleByName(saved.getNazov()));
		assertEquals(role3.getId(), saved.getId());
		Role role = new Role();
		assertThrows(EntityNotFoundException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				roleDao.getRoleByName(role.getNazov());
			}
		});
		roleDao.deleteRole(role3);
	}

	@Test
	void getAllTest() {
		roleDao.saveRole(role4);
		List<Role> list = roleDao.getAll();
		assertTrue(list.size() > 0);
		assertNotNull(list);
		// dvoch mame pernamently testovacich admin / normal a jedneho vytvaram v teste.
		assertTrue(list.size() == 3);
		roleDao.deleteRole(role4);
	}
}
