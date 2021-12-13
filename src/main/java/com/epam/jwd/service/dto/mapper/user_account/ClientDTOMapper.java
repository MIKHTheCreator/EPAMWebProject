package com.epam.jwd.service.dto.mapper.user_account;

import com.epam.jwd.dao.entity.AbstractEntity;
import com.epam.jwd.dao.entity.user_account.Client;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.user_account.ClientDTO;

/**
 * ClientDTOMapper class which implements DTOMapper for ClientDTO and Client entity
 * with Integer id
 *
 * @author mikh
 * @see DTOMapper
 */
public class ClientDTOMapper implements DTOMapper<ClientDTO, Client, Integer> {

    /**
     * Method for converting Client to ClientDTO
     *
     * @see DTOMapper#convertToDTO(AbstractEntity)
     */
    @Override
    public ClientDTO convertToDTO(Client client) {

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setUsername(client.getUsername());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPassword(client.getPassword());

        return clientDTO;
    }

    /**
     * Method for converting ClientDTO to Client
     *
     * @see DTOMapper#convertToDTO(AbstractEntity)
     */
    @Override
    public Client convertToEntity(ClientDTO clientDTO) {

        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setUsername(clientDTO.getUsername());
        client.setEmail(clientDTO.getEmail());
        client.setPassword(clientDTO.getPassword());

        return client;
    }
}
