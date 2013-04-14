package es.ficondev.web.modelutil.model.booking;

import java.util.List;

import es.ficondev.web.modelutil.dao.GenericDao;

public interface BookingDao extends GenericDao<Booking, Long>{

	List<Booking>	getAllBookings(Integer startIndex, Integer count);
	int				countAllBookings();
	
}
