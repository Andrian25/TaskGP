package com.example.hotelreservation.domain.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class HotelAddress
{
    private String country;
    private String city;
    private String street;
    private Integer houseNumber;
    private String postCode;
}
