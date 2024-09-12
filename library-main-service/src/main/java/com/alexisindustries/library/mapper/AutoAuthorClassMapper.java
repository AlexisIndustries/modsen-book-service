package com.alexisindustries.library.mapper;

import com.alexisindustries.library.model.Author;
import com.alexisindustries.library.model.dto.AuthorDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AutoAuthorClassMapper {
    AutoAuthorClassMapper MAPPER = Mappers.getMapper(AutoAuthorClassMapper.class);

    AuthorDto mapToAuthorDto(Author author);
    Author mapToAuthor(AuthorDto authorDto);
}
