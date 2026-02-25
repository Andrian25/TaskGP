package com.example.hotelreservation.api.controller;

import com.example.hotelreservation.api.dto.HotelCreateRequest;
import com.example.hotelreservation.api.dto.HotelDetailsResponse;
import com.example.hotelreservation.api.dto.HotelSummaryResponse;
import com.example.hotelreservation.application.facade.HotelFacade;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
public class HotelController
{
    private final HotelFacade hotelFacade;

    public HotelController(final HotelFacade hotelFacade)
    {
        this.hotelFacade = hotelFacade;
    }

    @GetMapping("/hotels")
    public List<HotelSummaryResponse> getHotels()
    {
        return hotelFacade.getAllHotelSummaries();
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelDetailsResponse> getHotelById(@PathVariable final Long id)
    {
        return ResponseEntity.ok(hotelFacade.getHotelDetails(id));
    }

    @GetMapping("/search")
    public List<HotelSummaryResponse> searchHotels(
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String brand,
            @RequestParam(required = false) final String city,
            @RequestParam(required = false) final String country,
            @RequestParam(required = false) final String amenities)
    {
        return hotelFacade.searchHotels(name, brand, city, country, amenities);
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelSummaryResponse> createHotel(@Valid @RequestBody final HotelCreateRequest request)
    {
        final HotelSummaryResponse created = hotelFacade.createHotel(request);
        return ResponseEntity.created(URI.create("/property-view/hotels/" + created.getId())).body(created);
    }

    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<Void> addAmenities(@PathVariable final Long id, @RequestBody final List<String> amenities)
    {
        hotelFacade.addAmenities(id, amenities);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Long>> getHistogram(@PathVariable final String param)
    {
        return ResponseEntity.ok(hotelFacade.getHistogram(param));
    }
}
