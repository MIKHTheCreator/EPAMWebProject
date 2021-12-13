package com.epam.jwd.service.dto.user_account;

import com.epam.jwd.service.dto.AbstractEntityDTO;

import java.util.Objects;

/**
 * @author mikh
 * CleintDTO class for Client entity
 * @see com.epam.jwd.dao.entity.user_account.Client
 */
public class ClientDTO extends AbstractEntityDTO<Integer> {

    private String username;
    private String email;
    private String password;

    public ClientDTO() {
    }

    public ClientDTO(Integer id) {
        super(id);
    }

    public ClientDTO(Integer id, String username, String email, String password) {
        super(id);
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public ClientDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(username, clientDTO.username)
                && Objects.equals(email, clientDTO.email)
                && Objects.equals(password, clientDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
