package com.epam.jwd.service.dto.mapper;

import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.service.dto.AbstractEntityDTO;

/**
 * @param <T> type of DTO object
 * @param <K> type of Entity object
 * @param <V> type of id filed
 * @author mikh
 * Mapper interface whcih describes converter methods from DTO to Entity and in opposite order
 */
public interface DTOMapper<T extends AbstractEntityDTO<V>, K extends AbstractEntity<V>, V> {

    /**
     * Method fo converting Entity to DTO
     *
     * @param entity entity to convert
     * @return DTO object
     * @throws DAOException if it's unable to get entity from DB
     */
    T convertToDTO(K entity) throws DAOException;

    /**
     * Method for converting DTO to Entity
     *
     * @param entityDTO DTO to convert
     * @return Entity object
     */
    K convertToEntity(T entityDTO);
}
