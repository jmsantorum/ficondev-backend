package es.ficondev.web.modelutil.model.booking;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import es.ficondev.web.modelutil.model.account.physical.Client;
import es.ficondev.web.modelutil.model.board.Board;

@Entity
public class Booking implements Serializable {

	private static final long serialVersionUID = -6329645270447375815L;

	private Long		bookingId;
	
	private Client		client;
	private Calendar	day;
	private Integer		people;
	private Boolean		share;
	private Board		board;
	
	private Long		version;
	
	public Booking() {
	}

	public Booking(Client client, Calendar day, Integer people, Boolean share, Board board) {
		this.client	= client;
		this.day 	= day;
		this.people = people;
		this.share 	= share;
		this.board 	= board;
	}
	
	@SequenceGenerator( // It only takes effect for
			name = "BookingIdGenerator", // databases providing identifier
			sequenceName = "BookingSeq") // generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "BookingIdGenerator")
	public Long getBookingId() {
		return bookingId;
	}
	
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="accountId")
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDay() {
		return day;
	}
	
	public void setDay(Calendar day) {
		this.day = day;
	}
	
	public Integer getPeople() {
		return people;
	}
	
	public void setPeople(Integer people) {
		this.people = people;
	}
	
	public Boolean getShare() {
		return share;
	}
	
	public void setShare(Boolean share) {
		this.share = share;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="boardId")
	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	@Version
	public Long getVersion() {
		return version;
	}
	
	public void setVersion(Long version) {
		this.version = version;
	}
}
