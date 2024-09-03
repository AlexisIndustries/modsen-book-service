package com.alexisindustries.library.auth.mapper;

import com.alexisindustries.library.auth.model.User;
import com.alexisindustries.library.auth.model.dto.LoginUserDto;
import com.alexisindustries.library.auth.model.dto.RegisterUserDto;
import com.alexisindustries.library.auth.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AutoUserClassMapper {
    AutoUserClassMapper MAPPER = Mappers.getMapper(AutoUserClassMapper.class);

    UserDto mapToUserDto(User user);
    UserDto mapToUserDto(RegisterUserDto registerUserDto);
    User mapToUser(LoginUserDto loginUserDto);
    User mapToUser(RegisterUserDto loginUserDto);
    User mapToUser(UserDto userDtoToSave);
}
