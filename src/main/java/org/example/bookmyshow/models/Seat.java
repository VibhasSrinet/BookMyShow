package org.example.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Seat extends BaseModel{
    private String seatNumber;
    @ManyToOne
    private SeatType seatType;
    private int rowVal;
    private int columnVal;
    @ManyToOne
    private Screen screen;
}
