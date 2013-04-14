package es.ficondev.web.modelutil.model.account.physical;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import es.ficondev.web.modelutil.model.booking.Booking;
import es.ficondev.web.modelutil.model.order.Order;

@Entity
@PrimaryKeyJoinColumn(name="accountId")
public class Client extends PhysicalAccount {

	private static final long serialVersionUID = 7085897946340154206L;

	private Set<Order>		orders = new HashSet<Order>();
	private Set<Booking>	bookings = new HashSet<Booking>();
	
	public Client() {
		super();
	}
	
	public Client(String username, String password, String salt, String email, Locale locale, TimeZone timeZone,
			String name, String direction, String phoneNumber) {
		super(username, password, salt, email, locale, timeZone, name, direction, phoneNumber);
	}
	
	@OneToMany(mappedBy = "client")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.REMOVE})
	public Set<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	public void addOrder(Order order) {
		this.orders.add(order);
	}
	
	public void removeOrder(Order order) {
		this.orders.remove(order);
	}
	
	@OneToMany(mappedBy = "client")
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
	
}
