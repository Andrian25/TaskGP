package com.example.hotelreservation.application.mapper;

import com.example.hotelreservation.api.dto.AddressDto;
import com.example.hotelreservation.api.dto.ArrivalTimeDto;
import com.example.hotelreservation.api.dto.ContactsDto;
import com.example.hotelreservation.api.dto.HotelCreateRequest;
import com.example.hotelreservation.api.dto.HotelDetailsResponse;
import com.example.hotelreservation.api.dto.HotelSummaryResponse;
import com.example.hotelreservation.domain.model.Hotel;
import com.example.hotelreservation.domain.model.HotelAddress;
import com.example.hotelreservation.domain.model.HotelArrivalTime;
import com.example.hotelreservation.domain.model.HotelContacts;

import java.util.HashSet;

public final class HotelDtoMapper
{
    private HotelDtoMapper()
    {
    }

    public static HotelSummaryResponse toSummaryResponse(final Hotel hotel)
    {
        final HotelSummaryResponse dto = new HotelSummaryResponse();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setDescription(hotel.getDescription());
        dto.setAddress(buildAddressString(hotel.getAddress()));
        dto.setPhone(hotel.getContacts() == null ? null : hotel.getContacts().getPhone());
        return dto;
    }

    public static HotelDetailsResponse toDetailsResponse(final Hotel hotel)
    {
        final HotelDetailsResponse dto = new HotelDetailsResponse();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setDescription(hotel.getDescription());
        dto.setBrand(hotel.getBrand());
        dto.setAddress(toAddressDto(hotel.getAddress()));
        dto.setContacts(toContactsDto(hotel.getContacts()));
        dto.setArrivalTime(toArrivalTimeDto(hotel.getArrivalTime()));
        dto.setAmenities(hotel.getAmenities() == null ? new HashSet<>() : new HashSet<>(hotel.getAmenities()));
        return dto;
    }

    public static Hotel toEntity(final HotelCreateRequest request)
    {
        final Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setDescription(request.getDescription());
        hotel.setBrand(request.getBrand());
        hotel.setAddress(toAddress(request.getAddress()));
        hotel.setContacts(toContacts(request.getContacts()));
        hotel.setArrivalTime(toArrivalTime(request.getArrivalTime()));
        return hotel;
    }

    private static HotelAddress toAddress(final AddressDto dto)
    {
        if (dto == null)
        {
            return null;
        }
        final HotelAddress address = new HotelAddress();
        address.setHouseNumber(dto.getHouseNumber());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setPostCode(dto.getPostCode());
        return address;
    }

    private static HotelContacts toContacts(final ContactsDto dto)
    {
        if (dto == null)
        {
            return null;
        }
        final HotelContacts contacts = new HotelContacts();
        contacts.setPhone(dto.getPhone());
        contacts.setEmail(dto.getEmail());
        return contacts;
    }

    private static HotelArrivalTime toArrivalTime(final ArrivalTimeDto dto)
    {
        if (dto == null)
        {
            return null;
        }
        final HotelArrivalTime arrivalTime = new HotelArrivalTime();
        arrivalTime.setCheckIn(dto.getCheckIn());
        arrivalTime.setCheckOut(dto.getCheckOut());
        return arrivalTime;
    }

    private static AddressDto toAddressDto(final HotelAddress address)
    {
        if (address == null)
        {
            return null;
        }
        final AddressDto dto = new AddressDto();
        dto.setHouseNumber(address.getHouseNumber());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setCountry(address.getCountry());
        dto.setPostCode(address.getPostCode());
        return dto;
    }

    private static ContactsDto toContactsDto(final HotelContacts contacts)
    {
        if (contacts == null)
        {
            return null;
        }
        final ContactsDto dto = new ContactsDto();
        dto.setPhone(contacts.getPhone());
        dto.setEmail(contacts.getEmail());
        return dto;
    }

    private static ArrivalTimeDto toArrivalTimeDto(final HotelArrivalTime arrivalTime)
    {
        if (arrivalTime == null)
        {
            return null;
        }
        final ArrivalTimeDto dto = new ArrivalTimeDto();
        dto.setCheckIn(arrivalTime.getCheckIn());
        dto.setCheckOut(arrivalTime.getCheckOut());
        return dto;
    }

    private static String buildAddressString(final HotelAddress address)
    {
        if (address == null)
        {
            return null;
        }
        final StringBuilder builder = new StringBuilder();
        if (address.getHouseNumber() != null)
        {
            builder.append(address.getHouseNumber()).append(' ');
        }
        if (hasText(address.getStreet()))
        {
            builder.append(address.getStreet()).append(", ");
        }
        if (hasText(address.getCity()))
        {
            builder.append(address.getCity()).append(", ");
        }
        if (hasText(address.getPostCode()))
        {
            builder.append(address.getPostCode()).append(", ");
        }
        if (hasText(address.getCountry()))
        {
            builder.append(address.getCountry());
        }
        final String formatted = builder.toString().trim();
        return formatted.endsWith(",") ? formatted.substring(0, formatted.length() - 1) : formatted;
    }

    private static boolean hasText(final String value)
    {
        return value != null && !value.trim().isEmpty();
    }
}
