package org.example.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel {
    private BookingStatus bookingStatus;
    @OneToMany(mappedBy = "booking")
    private List<ShowSeat> showSeats;
    private int amount;
    @OneToMany(mappedBy = "booking")
    private List<Payment> payments;
    @ManyToOne
    private User user;
}
