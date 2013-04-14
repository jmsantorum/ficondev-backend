package es.ficondev.web.modelutil.model.board;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficondev.web.modelutil.dao.GenericDaoHibernate;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.model.booking.Booking;

@Repository("boardDao")
public class BoardDaoHibernate extends GenericDaoHibernate<Board, Long> implements BoardDao {

	@Override
	public Board findBoardByCode(String code) throws InstanceNotFoundException {
		Board board = (Board) getSession().createQuery(
				"SELECT b FROM Board b WHERE b.code = :code ")
				.setParameter("code", code)
				.uniqueResult();

		if (code == null) 
			throw new InstanceNotFoundException(code, Board.class.getName());

		return board;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Board> getAllBoards(Integer startIndex, Integer count) {
		return getSession().createQuery(
				"SELECT b FROM Board b " +
				"ORDER BY b.code ASC").
				setFirstResult(startIndex).
				setMaxResults(count).list();
	}

	@Override
	public int countAllBoards() {
		long count = (Long) getSession().createQuery(
				"SELECT COUNT(b) FROM Board b ").
				uniqueResult();

		return (int) count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Board> getAllFreeBoardsByDate(Calendar day, Integer people, Boolean share) {
		
		List<Board> boards = getSession().createQuery(
		"SELECT bd FROM Board bd " +
		"WHERE bd NOT IN " +
		"   ( SELECT bg.board FROM Booking bg WHERE bg.day = :day AND (bg.share = FALSE OR bg.board.size < :people OR :share = FALSE) ) " +
		"   ORDER BY bd.code ASC").
		setParameter("day", day).
		setParameter("people", people).
		setParameter("share", share).
		list();	
		
		List<Board> boards2 = new ArrayList<Board>();
		for(Board board : boards) {
			int count = 0;
			for(Booking b : board.getBookings()) {
				count += b.getPeople();
			}
			if (board.getSize() - count >= people)
				boards2.add(board);
		}
		
		return boards2;
//		return getSession().createQuery(
//				"SELECT bd FROM Board bd " +
//				"WHERE ( bd NOT IN " +
//				"   ( SELECT bg.board FROM Booking bg WHERE bg.day = :day AND (bg.share = FALSE OR bg.board.size < :people OR :share = FALSE) ) ) " +
//				"   AND (( bd.size - ( SELECT SUM(bg.people) FROM Booking bg WHERE bg in elements(bd.bookings) ) ) >= :people) " +
//				"   ORDER BY bd.code ASC").
//				setParameter("day", day).
//				setParameter("people", people).
//				setParameter("share", share).
//				list();	
	}
}
