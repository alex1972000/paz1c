package upjs.sk.pazacinkaren.Interfaces;

import java.util.List;

import upjs.sk.pazacinkaren.Dao.EntityNotFoundException;
import upjs.sk.pazacinkaren.Models.Order;


public interface OrderDao {
	
	Order saveOrder(Order order) throws EntityNotFoundException;
	
	List<Order> getAllForHistory();

	boolean deleteOrder(long orderID) throws EntityNotFoundException;
	
	List<Order> getAll();
	
	List<Order> getOrderById(long id) throws EntityNotFoundException;
}
