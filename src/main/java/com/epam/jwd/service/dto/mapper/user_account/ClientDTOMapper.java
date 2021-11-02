package com.epam.jwd.service.dto.mapper.user_account;

import com.epam.jwd.dao.entity.user_account.Client;
import com.epam.jwd.service.dto.mapper.DTOMapper;
import com.epam.jwd.service.dto.user_account.ClientDTO;

public class ClientDTOMapper implements DTOMapper<ClientDTO, Client, Integer> {

    @Override
    public ClientDTO convertToDTO(Client client) {

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setUsername(client.getUsername());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPassword(client.getPassword());

        return clientDTO;
    }

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
