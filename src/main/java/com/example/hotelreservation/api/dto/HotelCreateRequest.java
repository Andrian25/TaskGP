package com.example.hotelreservation.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HotelCreateRequest
{
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String brand;
    @Valid
    @NotNull
    private AddressDto address;
    @Valid
    @NotNull
    private ContactsDto contacts;
    @Valid
    @NotNull
    private ArrivalTimeDto arrivalTime;
}
