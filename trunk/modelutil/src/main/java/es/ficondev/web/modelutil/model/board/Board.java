package es.ficondev.web.modelutil.model.board;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import es.ficondev.web.modelutil.model.booking.Booking;

@Entity
public class Board implements Serializable {

	private static final long serialVersionUID = -1972993788261232321L;

	private Long	boardId;
	
	private String	code;
	private Integer size;

	private Set<Booking>	bookings	= new HashSet<Booking>();
	
	private Long	version;

	public Board() {
	}

	public Board(String code, Integer size) {
		this.code = code;
		this.size = size;
	}
	
	@SequenceGenerator( // It only takes effect for
			name = "BoardIdGenerator", // databases providing identifier
			sequenceName = "BoardSeq") // generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "BoardIdGenerator")
	public Long getBoardId() {
		return boardId;
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@OneToMany(mappedBy = "board")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.REMOVE})
	public Set<Booking> getBookings() {
		return bookings;
	}
	
	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}
	
	public void addBooking(Booking booking) {
		this.bookings.add(booking);
	}
	
	public void removeBooking(Booking booking) {
		this.bookings.remove(booking);
	}
	
	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
