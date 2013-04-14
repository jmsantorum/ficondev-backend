package es.ficondev.web.modelutil.model.order;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficondev.web.modelutil.dao.GenericDaoHibernate;

@Repository("orderDao")
public class OrderDaoHibernate extends GenericDaoHibernate<Order, Long> implements OrderDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getAllOrders(int startIndex, int count) {
		return getSession().createQuery(
				"SELECT o FROM Order o " +
				"ORDER BY o.orderId ASC").
				setFirstResult(startIndex).
				setMaxResults(count).list();
	}
	
	@Override
	public int countAllOrders() {
		long count = (Long) getSession().createQuery(
				"SELECT COUNT(o) FROM Ordero ").
				uniqueResult();

		return (int) count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getAllOrders(int startIndex, int count, Boolean distributed) {
		return getSession().createQuery(
				"SELECT o FROM Order o " +
				"WHERE o.distributed = :distributed " +
				"ORDER BY o.orderId ASC").
				setParameter("distributed", distributed).
				setFirstResult(startIndex).
				setMaxResults(count).list();
	}
	
	@Override
	public int countAllOrders(Boolean distributed) {
		long count = (Long) getSession().createQuery(
				"SELECT COUNT(o) FROM Order o " +
				"WHERE o.distributed = :distributed ").
				setParameter("distributed", distributed).
				uniqueResult();

		return (int) count;
	}
	
}
