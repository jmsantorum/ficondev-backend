package es.ficondev.web.modelutil.model.bookingservice;

import es.ficondev.web.modelutil.model.board.Board;
import es.ficondev.web.modelutil.model.booking.Booking;

public class BoardExtended {

	private Integer size = 0;
	private Integer people = 0;
	private Integer free = 0;
	private Board board;
	
	public BoardExtended(Board board) {
		this.board = board;
		this.size = board.getSize();
		for(Booking bg : board.getBookings()) {
			people += bg.getPeople();
		}
		this.free = this.size - this.people;
	}
	
	public Integer getSize() {
		return size;
	}
	
	public void setSize(Integer size) {
		this.size = size;
	}
	
	public Integer getPeople() {
		return people;
	}
	
	public void setPeople(Integer people) {
		this.people = people;
	}
	
	public Integer getFree() {
		return free;
	}
	
	public void setFree(Integer free) {
		this.free = free;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
}
