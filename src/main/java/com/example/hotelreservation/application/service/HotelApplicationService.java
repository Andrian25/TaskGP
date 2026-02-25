package com.example.hotelreservation.application.service;

import com.example.hotelreservation.common.exception.NotFoundException;
import com.example.hotelreservation.domain.model.Hotel;
import com.example.hotelreservation.domain.repository.HotelRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class HotelApplicationService
{
    private final HotelRepository hotelRepository;

    public HotelApplicationService(final HotelRepository hotelRepository)
    {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> findAllHotels()
    {
        return hotelRepository.findAll();
    }

    public Hotel findHotelById(final Long id)
    {
        return hotelRepository.findById(id).orElseThrow(() -> new NotFoundException("Hotel not found: " + id));
    }

    public List<Hotel> findHotelsByFilters(
            final String name,
            final String brand,
            final String city,
            final String country,
            final String amenity)
    {
        return hotelRepository.findAll(buildSearchSpecification(name, brand, city, country, amenity));
    }

    @Transactional
    public Hotel createHotel(final Hotel hotel)
    {
        return hotelRepository.save(hotel);
    }

    @Transactional
    public void addHotelAmenities(final Long hotelId, final List<String> amenities)
    {
        if (amenities == null || amenities.isEmpty())
        {
            return;
        }
        final Hotel hotel = findHotelById(hotelId);
        final Set<String> sanitizedAmenities = amenities.stream()
                .filter(StringUtils::hasText)
                .collect(HashSet::new, Set::add, Set::addAll);
        hotel.getAmenities().addAll(sanitizedAmenities);
        hotelRepository.save(hotel);
    }

    public Map<String, Long> buildHistogram(final String param)
    {
        final Map<String, Long> histogram = new HashMap<>();
        for (Hotel hotel : hotelRepository.findAll())
        {
            switch (param)
            {
                case "city":
                    if (hotel.getAddress() != null && StringUtils.hasText(hotel.getAddress().getCity()))
                    {
                        histogram.merge(hotel.getAddress().getCity(), 1L, Long::sum);
                    }
                    break;
                case "brand":
                    if (StringUtils.hasText(hotel.getBrand()))
                    {
                        histogram.merge(hotel.getBrand(), 1L, Long::sum);
                    }
                    break;
                case "country":
                    if (hotel.getAddress() != null && StringUtils.hasText(hotel.getAddress().getCountry()))
                    {
                        histogram.merge(hotel.getAddress().getCountry(), 1L, Long::sum);
                    }
                    break;
                case "amenities":
                    if (hotel.getAmenities() != null)
                    {
                        for (String item : hotel.getAmenities())
                        {
                            if (StringUtils.hasText(item))
                            {
                                histogram.merge(item, 1L, Long::sum);
                            }
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported histogram param: " + param);
            }
        }
        return histogram;
    }

    private Specification<Hotel> buildSearchSpecification(
            final String name,
            final String brand,
            final String city,
            final String country,
            final String amenity)
    {
        Specification<Hotel> specification = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        if (StringUtils.hasText(name))
        {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), '%' + name.toLowerCase() + '%'));
        }
        if (StringUtils.hasText(brand))
        {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("brand")), brand.toLowerCase()));
        }
        if (StringUtils.hasText(city))
        {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("address").get("city")), city.toLowerCase()));
        }
        if (StringUtils.hasText(country))
        {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("address").get("country")), country.toLowerCase()));
        }
        if (StringUtils.hasText(amenity))
        {
            specification = specification.and((root, query, criteriaBuilder) ->
            {
                query.distinct(true);
                final Join<Hotel, String> amenityJoin = root.join("amenities", JoinType.LEFT);
                return criteriaBuilder.equal(criteriaBuilder.lower(amenityJoin), amenity.toLowerCase());
            });
        }
        return specification;
    }
}
