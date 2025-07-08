package cz.dtkacik.roomly.feature.room.dto;

public record LocationDto(
        Long id,
        String country,
        String city,
        String street,
        String streetNumber
) { }
