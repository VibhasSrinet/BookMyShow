package org.example.bookmyshow.services;

import org.example.bookmyshow.exceptions.ShowNotFoundException;
import org.example.bookmyshow.exceptions.ShowSeatNotAvailableException;
import org.example.bookmyshow.exceptions.UserNotFoundException;
import org.example.bookmyshow.models.*;
import org.example.bookmyshow.repositories.BookingRepository;
import org.example.bookmyshow.repositories.ShowRepository;
import org.example.bookmyshow.repositories.ShowSeatRepository;
import org.example.bookmyshow.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private BookingRepository bookingRepository;
    private PriceCalculatorService priceCalculatorService;
    public BookingService(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, PriceCalculatorService priceCalculatorService, BookingRepository bookingRepository) {
        this.showSeatRepository = showSeatRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.priceCalculatorService = priceCalculatorService;
        this.bookingRepository = bookingRepository;
    }

    public Booking bookMovie(Long userId, Long showId, List<Long> showSeatIds) throws UserNotFoundException, ShowNotFoundException, ShowSeatNotAvailableException, InterruptedException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found!");
        }
        User user = optionalUser.get();
        Optional<Show> optionalShow = showRepository.findById(showId);
        if(optionalShow.isEmpty()){
            throw new ShowNotFoundException("Show not found!");
        }
        Show show = optionalShow.get();
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
        for(ShowSeat showSeat: showSeats){
            if (!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) {
                throw new ShowSeatNotAvailableException("Show seat not available!");
            }
        }
        for(ShowSeat showSeat: showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeatRepository.save(showSeat);
        }
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowSeats(showSeats);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setAmount(priceCalculatorService.calculatePrice(showSeats, show));
        return bookingRepository.save(booking);
    }
}
