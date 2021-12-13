package com.epam.jwd.service.dto.mapper.user_account;

import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.entity.user_account.Passport;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.user_account.PassportDTO;

/**
 * PassportDTOMapper class which implements DTOMapper for PassportDTO and Passport entity
 * with Integer id
 *
 * @author mikh
 * @see DTOMapper
 */
public class PassportDTOMapper implements DTOMapper<PassportDTO, Passport, Integer> {

    /**
     * Method for converting Passport to PassportDTO
     *
     * @see DTOMapper#convertToDTO(AbstractEntity)
     */
    @Override
    public PassportDTO convertToDTO(Passport passport) {

        PassportDTO passportDTO = new PassportDTO();
        passportDTO.setId(passport.getId());
        passportDTO.setSeriaAndNumber(passport.getSeriaAndNumber());
        passportDTO.setPersonalNumber(passport.getPersonalNumber());
        passportDTO.setExpirationDate(passport.getExpirationDate());

        return passportDTO;
    }

    /**
     * Method for converting PassportDTO to Passport
     *
     * @see DTOMapper#convertToDTO(AbstractEntity)
     */
    @Override
    public Passport convertToEntity(PassportDTO passportDTO) {

        Passport passport = new Passport();
        passport.setId(passportDTO.getId());
        passport.setSeriaAndNumber(passportDTO.getSeriaAndNumber());
        passport.setPersonalNumber(passportDTO.getPersonalNumber());
        passport.setExpirationDate(passportDTO.getExpirationDate());

        return passport;
    }
}
