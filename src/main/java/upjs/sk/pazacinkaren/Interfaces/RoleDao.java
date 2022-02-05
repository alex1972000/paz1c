package upjs.sk.pazacinkaren.Interfaces;

import java.util.List;

import upjs.sk.pazacinkaren.Dao.EntityNotFoundException;
import upjs.sk.pazacinkaren.Models.Role;


public interface RoleDao {
	
	
	
	Role getById(long id) throws EntityNotFoundException;
	
	Role saveRole(Role role) throws EntityNotFoundException;
	
	Role deleteRole(Role role) throws EntityNotFoundException;
	
	Long getRoleByName(String nazov) throws EntityNotFoundException;
	
	List<Role> getAll();

}
