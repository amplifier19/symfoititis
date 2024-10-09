package gr.symfoititis.student.services;

import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.ConflictException;
import gr.symfoititis.common.exceptions.NotFoundException;
import gr.symfoititis.student.dao.BookingsDao;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookingsService {
    private final BookingsDao bookingsDao;
    public BookingsService (BookingsDao bookingsDao) {
        this.bookingsDao = bookingsDao;
    }

    public List<Booking> getBookings (String s_id) {
        if (Objects.isNull(s_id) || s_id.isBlank())  {
            throw new BadRequestException("Bad Request");
        }
       return bookingsDao.getBookings(s_id);
    }

    public void addBooking (Booking booking) {
        if (
                Objects.isNull(booking.av_id()) ||
                booking.av_id().compareTo(0) <= 0 ||
                Objects.isNull(booking.s_id()) ||
                booking.s_id().isBlank()
        ) {
            System.out.println(booking.av_id() + " " + booking.s_id());
            throw new BadRequestException("Bad Request");
        }
        try {
            bookingsDao.addBooking(booking);
        } catch (BadSqlGrammarException e) {
            if ("65001".equals(e.getSQLException().getSQLState())) {
               throw new ConflictException("This slot is not available for booking.");
            }
        }
    }

    public void cancelBooking (Integer b_id) {
        if (Objects.isNull(b_id) || b_id.compareTo(0) <= 0) {
           throw new BadRequestException("Bad Request");
        }
        int ret = bookingsDao.cancelBooking(b_id);
        if (ret == 0) {
            throw new NotFoundException(String.format("Booking %d not found.", b_id));
        }
    }
}
