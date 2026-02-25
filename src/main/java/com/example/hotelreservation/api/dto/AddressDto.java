package com.example.hotelreservation.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressDto
{
    @NotNull
    private Integer houseNumber;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    @NotBlank
    private String postCode;
}
