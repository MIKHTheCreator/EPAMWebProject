package com.epam.jwd.service.validator;

import com.epam.jwd.service.dto.AbstractEntityDTO;
import com.epam.jwd.service.exception.ServiceException;

public interface Validator<T extends AbstractEntityDTO<V>, V> {
    void validate(T entity) throws ServiceException;
}
