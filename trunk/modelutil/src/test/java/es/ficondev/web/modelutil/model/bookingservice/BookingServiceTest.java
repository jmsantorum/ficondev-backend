package es.ficondev.web.modelutil.model.bookingservice;

import static es.ficondev.web.modelutil.model.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static es.ficondev.web.modelutil.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.ficondev.web.modelutil.exceptions.DuplicateInstanceException;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;
import es.ficondev.web.modelutil.exceptions.NotAvailableException;
import es.ficondev.web.modelutil.model.account.physical.Client;
import es.ficondev.web.modelutil.model.accountservice.AccountService;
import es.ficondev.web.modelutil.model.board.Board;
import es.ficondev.web.modelutil.model.booking.Booking;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class BookingServiceTest {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private AccountService accountService;
	
	@Test
	public void testBoard() throws DuplicateInstanceException, InstanceNotFoundException, NotAvailableException {
		Board b1 = bookingService.addBoard("m1", 6);
		Board b2 = bookingService.addBoard("m2", 8);
		Board b3 = bookingService.addBoard("m3", 4);
		
		assertEquals(3, bookingService.countAllBoards());
		
		assertEquals(Integer.valueOf(6), bookingService.findBoardByCode("m1").getSize());
		
		b2.setSize(10);
		bookingService.saveBoard(b2);
		assertEquals(Integer.valueOf(10), bookingService.findBoardByCode("m2").getSize());
		
		bookingService.removeBoard(b3.getBoardId());
		assertEquals(2, bookingService.countAllBoards());
	}
	
	@Test
	public void testMakeBooking() throws DuplicateInstanceException, NotAvailableException, InstanceNotFoundException {
		Board b1 = bookingService.addBoard("m1", 2);
		Board b2 = bookingService.addBoard("m2", 4);
		Board b3 = bookingService.addBoard("m3", 6);
		
		Client c1 = new Client("username", "password", "salt", "email@email.es", Locale.ENGLISH, TimeZone.getDefault(), "name", "direction", "666666666");
		Client c2 = new Client("username", "password", "salt", "email@email.es", Locale.ENGLISH, TimeZone.getDefault(), "name", "direction", "666666666");
		Client c3 = new Client("username", "password", "salt", "email@email.es", Locale.ENGLISH, TimeZone.getDefault(), "name", "direction", "666666666");
		Client c4 = new Client("username", "password", "salt", "email@email.es", Locale.ENGLISH, TimeZone.getDefault(), "name", "direction", "666666666");
		
		c1 = (Client) accountService.saveAccount(c1);
		c2 = (Client) accountService.saveAccount(c2);
		c3 = (Client) accountService.saveAccount(c3);
		c4 = (Client) accountService.saveAccount(c4);
		
		Calendar day = Calendar.getInstance();
		
		Booking r1 = bookingService.makeBooking(c1.getAccountId(), day, 3, true);
		System.out.println("sol1:"+r1.getBoard().getCode());
		assertEquals(b2.getBoardId(), r1.getBoard().getBoardId());

		Booking r2 = bookingService.makeBooking(c2.getAccountId(), day, 4, false);
		System.out.println("sol2:"+r2.getBoard().getCode());
		assertEquals(b3.getBoardId(), r2.getBoard().getBoardId());

		Booking r3 = bookingService.makeBooking(c3.getAccountId(), day, 2, true);
		System.out.println("sol3:"+r3.getBoard().getCode());
		assertEquals(b1.getBoardId(), r3.getBoard().getBoardId());

		Booking r4 = bookingService.makeBooking(c4.getAccountId(), day, 1, true);
		System.out.println("sol4:"+r4.getBoard().getCode());
		assertEquals(b2.getBoardId(), r4.getBoard().getBoardId());

		try {
			bookingService.makeBooking(c1.getAccountId(), day, 2, true);
			fail();
		} catch(NotAvailableException e) {

		}
		
		List<Booking> rs = bookingService.getAllBookings(0, bookingService.countAllBookings());
		assertEquals(4, rs.size());
		
		bookingService.cancelBooking(r2.getBookingId());
		
		rs = bookingService.getAllBookings(0, bookingService.countAllBookings());
		assertEquals(3, rs.size());
	}

}
