package com.example.hotelreservation.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotels")
public class Hotel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String brand;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "houseNumber", column = @Column(name = "house_number")),
            @AttributeOverride(name = "street", column = @Column(name = "street")),
            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "country", column = @Column(name = "country")),
            @AttributeOverride(name = "postCode", column = @Column(name = "post_code"))
    })
    private HotelAddress address;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "phone", column = @Column(name = "phone")),
            @AttributeOverride(name = "email", column = @Column(name = "email"))
    })
    private HotelContacts contacts;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "checkIn", column = @Column(name = "check_in")),
            @AttributeOverride(name = "checkOut", column = @Column(name = "check_out"))
    })
    private HotelArrivalTime arrivalTime;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private Set<String> amenities = new HashSet<>();
}
