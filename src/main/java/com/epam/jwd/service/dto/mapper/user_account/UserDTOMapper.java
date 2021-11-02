package com.epam.jwd.service.dto.mapper.user_account;

import com.epam.jwd.dao.entity.user_account.User;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.user_account.UserDTO;

public class UserDTOMapper implements DTOMapper<UserDTO, User, Integer> {

    @Override
    public UserDTO convertToDTO(User user) {

        return new UserDTO.Builder()
                .withId(user.getId())
                .withFirstName(user.getFirstName())
                .withSecondName(user.getSecondName())
                .withPhoneNumber(user.getPhoneNumber())
                .withAge(user.getAge())
                .withGender(user.getGender())
                .withRole(user.getRole())
                .withClientId(user.getClientId())
                .withPassportId(user.getPassportId())
                .build();
    }

    @Override
    public User convertToEntity(UserDTO userDTO) {
        return new User.Builder()
                .withId(userDTO.getId())
                .withFirstName(userDTO.getFirstName())
                .withSecondName(userDTO.getSecondName())
                .withPhoneNumber(userDTO.getPhoneNumber())
                .withAge(userDTO.getAge())
                .withGender(userDTO.getGender())
                .withRole(userDTO.getRole())
                .withClientId(userDTO.getClientId())
                .withPassportId(userDTO.getPassportId())
                .build();
    }
}
