package com.example.hotelreservation.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactsDto
{
    @NotBlank
    private String phone;
    @Email
    @NotBlank
    private String email;
}
