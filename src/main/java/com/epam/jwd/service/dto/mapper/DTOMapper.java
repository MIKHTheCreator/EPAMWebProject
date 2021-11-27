package com.epam.jwd.service.dto.mapper;

import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.exception.DAOException;
import com.epam.jwd.service.dto.AbstractEntityDTO;

public interface DTOMapper<T extends AbstractEntityDTO<V>, K extends AbstractEntity<V>, V> {

    T convertToDTO(K entity) throws DAOException;
    K convertToEntity(T entityDTO);
}
