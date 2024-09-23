package com.alexisindustries.library.mapper;

import com.alexisindustries.library.model.BookGenre;
import com.alexisindustries.library.model.dto.BookGenreDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AutoBookGenreClassMapper {
    BookGenreDto mapToBookGenreDto(BookGenre bookGenre);
    BookGenre mapToBookGenre(BookGenreDto bookGenreDto);
}
