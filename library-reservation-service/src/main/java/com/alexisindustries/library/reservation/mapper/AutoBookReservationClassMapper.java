package com.alexisindustries.library.reservation.mapper;

import com.alexisindustries.library.reservation.model.BookReservation;
import com.alexisindustries.library.reservation.model.dto.BookReservationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AutoBookReservationClassMapper{
    AutoBookReservationClassMapper MAPPER = Mappers.getMapper(AutoBookReservationClassMapper.class);

    BookReservationDto mapToBookReservationDto(BookReservation bookReservation);
    BookReservation mapToBookReservation(BookReservationDto bookReservationDto);
}
