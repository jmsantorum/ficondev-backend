package es.ficondev.web.modelutil.model.bookingservice;

import java.util.Calendar;
import java.util.List;

import es.ficondev.web.modelutil.exceptions.DuplicateInstanceException;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.exceptions.NotAvailableException;
import es.ficondev.web.modelutil.model.board.Board;
import es.ficondev.web.modelutil.model.booking.Booking;

public interface BookingService {

	Board		addBoard(String code, Integer size) throws DuplicateInstanceException;
	void		removeBoard(Long boardId) throws InstanceNotFoundException, NotAvailableException;
	Board		saveBoard(Board board);
	Board		findBoardByCode(String code) throws InstanceNotFoundException;
	List<Board>	getAllBoards(Integer startIndex, Integer count);
	int			countAllBoards();
	
	List<Booking>	getAllBookings(Integer startIndex, Integer count);
	int				countAllBookings();
	Booking 		makeBooking(Long clientId, Calendar day, Integer people, Boolean share) throws NotAvailableException, InstanceNotFoundException;
	void			cancelBooking(Long bookingId) throws InstanceNotFoundException;
}
