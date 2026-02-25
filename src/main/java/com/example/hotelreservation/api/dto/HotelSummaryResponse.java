package com.example.hotelreservation.api.dto;

import lombok.Data;

@Data
public class HotelSummaryResponse
{
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
