package org.example.bookmyshow.controllers;

import org.example.bookmyshow.dtos.BookMovieRequestDto;
import org.example.bookmyshow.dtos.BookMovieResponseDto;
import org.example.bookmyshow.dtos.ResponseStatus;
import org.example.bookmyshow.exceptions.ShowNotFoundException;
import org.example.bookmyshow.exceptions.ShowSeatNotAvailableException;
import org.example.bookmyshow.exceptions.UserNotFoundException;
import org.example.bookmyshow.models.Booking;
import org.example.bookmyshow.models.ShowSeat;
import org.example.bookmyshow.services.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public BookMovieResponseDto bookShow(@RequestBody BookMovieRequestDto bookMovieRequestDto) throws InterruptedException{
        BookMovieResponseDto bookMovieResponseDto = new BookMovieResponseDto();
        try{
            Booking booking = bookingService.bookMovie(bookMovieRequestDto.getUserId(), bookMovieRequestDto.getShowId(), bookMovieRequestDto.getShowSeatIds());
            bookMovieResponseDto.setBookingId(booking.getId());
            bookMovieResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (UserNotFoundException | ShowNotFoundException | ShowSeatNotAvailableException e){
            bookMovieResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return bookMovieResponseDto;
    }
}

