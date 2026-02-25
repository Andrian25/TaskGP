package com.example.hotelreservation.domain.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class HotelArrivalTime
{
    private String checkIn;
    private String checkOut;
}
