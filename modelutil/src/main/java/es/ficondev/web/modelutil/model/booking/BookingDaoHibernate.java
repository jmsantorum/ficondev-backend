package es.ficondev.web.modelutil.model.booking;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficondev.web.modelutil.dao.GenericDaoHibernate;

@Repository("bookingdDao")
public class BookingDaoHibernate extends GenericDaoHibernate<Booking, Long> implements BookingDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Booking> getAllBookings(Integer startIndex, Integer count) {
		return getSession().createQuery(
				"SELECT b FROM Booking b " +
				"ORDER BY b.bookingId ASC").
				setFirstResult(startIndex).
				setMaxResults(count).list();
	}
	
	@Override
	public int countAllBookings() {
		long count = (Long) getSession().createQuery(
				"SELECT COUNT(b) FROM Booking b ").
				uniqueResult();

		return (int) count;
	}
}
