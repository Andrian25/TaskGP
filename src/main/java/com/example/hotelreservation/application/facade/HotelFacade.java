package com.example.hotelreservation.application.facade;

import com.example.hotelreservation.api.dto.HotelCreateRequest;
import com.example.hotelreservation.api.dto.HotelDetailsResponse;
import com.example.hotelreservation.api.dto.HotelSummaryResponse;
import com.example.hotelreservation.application.mapper.HotelDtoMapper;
import com.example.hotelreservation.application.service.HotelApplicationService;
import com.example.hotelreservation.domain.model.Hotel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class HotelFacade
{
    private final HotelApplicationService hotelApplicationService;

    public HotelFacade(final HotelApplicationService hotelApplicationService)
    {
        this.hotelApplicationService = hotelApplicationService;
    }

    public List<HotelSummaryResponse> getAllHotelSummaries()
    {
        return hotelApplicationService.findAllHotels().stream().map(HotelDtoMapper::toSummaryResponse).toList();
    }

    public HotelDetailsResponse getHotelDetails(final Long hotelId)
    {
        return HotelDtoMapper.toDetailsResponse(hotelApplicationService.findHotelById(hotelId));
    }

    public List<HotelSummaryResponse> searchHotels(
            final String name,
            final String brand,
            final String city,
            final String country,
            final String amenity)
    {
        return hotelApplicationService.findHotelsByFilters(name, brand, city, country, amenity)
                .stream()
                .map(HotelDtoMapper::toSummaryResponse)
                .toList();
    }

    public HotelSummaryResponse createHotel(final HotelCreateRequest request)
    {
        final Hotel created = hotelApplicationService.createHotel(HotelDtoMapper.toEntity(request));
        return HotelDtoMapper.toSummaryResponse(created);
    }

    public void addAmenities(final Long hotelId, final List<String> amenities)
    {
        hotelApplicationService.addHotelAmenities(hotelId, amenities);
    }

    public Map<String, Long> getHistogram(final String param)
    {
        return hotelApplicationService.buildHistogram(param);
    }
}
