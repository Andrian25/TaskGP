package com.example.hotelreservation.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArrivalTimeDto
{
    @NotBlank
    private String checkIn;
    private String checkOut;
}
