package com.alexisindustries.library.mapper;

import com.alexisindustries.library.model.Book;
import com.alexisindustries.library.model.dto.BookDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AutoBookClassMapper {
    BookDto mapToBookDto(Book book);
    Book mapToBook(BookDto bookDto);
}
