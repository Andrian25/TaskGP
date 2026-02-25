package com.example.hotelreservation.domain.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class HotelContacts
{
    private String phone;
    private String email;
}
