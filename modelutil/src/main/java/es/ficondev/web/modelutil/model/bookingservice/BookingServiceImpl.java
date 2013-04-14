package es.ficondev.web.modelutil.model.bookingservice;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficondev.web.modelutil.exceptions.DuplicateInstanceException;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.exceptions.NotAvailableException;
import es.ficondev.web.modelutil.model.account.AccountDao;
import es.ficondev.web.modelutil.model.account.physical.Client;
import es.ficondev.web.modelutil.model.board.Board;
import es.ficondev.web.modelutil.model.board.BoardDao;
import es.ficondev.web.modelutil.model.booking.Booking;
import es.ficondev.web.modelutil.model.booking.BookingDao;

@Service("bookingService")
@Transactional
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Override
	public Board addBoard(String code, Integer size) throws DuplicateInstanceException {
		Board board = new Board(code, size);
		
		boardDao.save(board);
		
		return board;
	}
	
	@Override
	public void removeBoard(Long boardId) throws InstanceNotFoundException, NotAvailableException {
		Board board = boardDao.find(boardId);
		
		if (board.getBookings().size() > 0)
			throw new NotAvailableException(boardId, Board.class.getName());
		
		boardDao.remove(boardId);
	}
	
	@Override
	public Board saveBoard(Board board) {
		boardDao.save(board);
		
		return board;
	}
	
	@Override
	public Board findBoardByCode(String code) throws InstanceNotFoundException {
		return boardDao.findBoardByCode(code);
	}
	
	@Override
	public List<Board> getAllBoards(Integer startIndex, Integer count) {
		return boardDao.getAllBoards(startIndex, count);
	}
	
	@Override
	public int countAllBoards() {
		return boardDao.countAllBoards();
	}
	
	@Override
	public List<Booking> getAllBookings(Integer startIndex, Integer count) {
		return bookingDao.getAllBookings(startIndex, count);
	}
	
	@Override
	public int countAllBookings() {
		return bookingDao.countAllBookings();
	}
	
	@Override
	public Booking makeBooking(Long clientId, Calendar day, Integer people, Boolean share) throws NotAvailableException, InstanceNotFoundException {
		Client client = (Client) accountDao.find(clientId);
		
		List<Board> freeBoards = boardDao.getAllFreeBoardsByDate(day, people, share);
		
		//Select the best board
		BoardExtended bestBoard = null;
		for(Board board : freeBoards) {
			BoardExtended be = new BoardExtended(board);
			if (bestBoard == null || be.getFree() < bestBoard.getFree())
				bestBoard = be;
		}
		
		if (bestBoard == null) throw new NotAvailableException(clientId, Booking.class.getName());
		Booking booking = new Booking(client, day, people, share, bestBoard.getBoard());
		
		bookingDao.save(booking);
		
		return booking;
	}
	
	@Override
	public void cancelBooking(Long bookingId) throws InstanceNotFoundException {
		Booking booking = bookingDao.find(bookingId);
		booking.getBoard().removeBooking(booking);
		
		boardDao.save(booking.getBoard());
		bookingDao.remove(bookingId);
	}
}
