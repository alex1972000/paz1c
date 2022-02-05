package upjs.sk.pazacinkaren.Interfaces;

import java.util.List;

import upjs.sk.pazacinkaren.Dao.EntityNotFoundException;
import upjs.sk.pazacinkaren.Dao.EntityUndeletableException;
import upjs.sk.pazacinkaren.Models.User;

public interface UserDao {

	List<User> getAll();

	User getUserByLogin(String login, String password) throws EntityNotFoundException;
	
	User getUserById(Long id) throws EntityNotFoundException;

	User saveUser(User user) throws EntityNotFoundException;

	User promoteUser(User user);

	User demoteUser(User user);
	
	User deleteUser(Long id) throws EntityUndeletableException, EntityUndeletableException;

	User getUserByName(String name) throws EntityNotFoundException;
}
