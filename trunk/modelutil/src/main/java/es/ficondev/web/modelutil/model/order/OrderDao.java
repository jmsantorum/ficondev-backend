package es.ficondev.web.modelutil.model.order;

import java.util.List;

import es.ficondev.web.modelutil.dao.GenericDao;

public interface OrderDao extends GenericDao<Order, Long> {

	List<Order>	getAllOrders(int startIndex, int count);
	int			countAllOrders();
	
	List<Order>	getAllOrders(int startIndex, int count, Boolean distributed);
	int			countAllOrders(Boolean distributed);
	
	
}
