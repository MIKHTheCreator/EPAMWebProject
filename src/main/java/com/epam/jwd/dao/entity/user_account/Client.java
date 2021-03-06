package com.epam.jwd.dao.entity.user_account;

import com.epam.jwd.dao.entity.AbstractEntity;

import java.util.Objects;

/**
 * Client entity class which extends AbstractEntity class with Integer id field
 * This class describes Client of the bank system
 */
public class Client extends AbstractEntity<Integer> {

    /**
     * String field with client's username
     */
    private String username;
    /**
     * String filed with client's email
     */
    private String email;
    /**
     * String filed with client's password
     */
    private String password;

    /**
     * Constructor without arguments for creating empty Client Object
     *
     * @see Client#Client(Integer)
     * @see Client#Client(Integer, String, String, String)
     */
    public Client() {
    }

    /**
     * Constructor for creating Client object with id
     *
     * @param id client's id
     */
    public Client(Integer id) {
        super(id);
    }

    /**
     * Constructor with all amount of arguments for creating Client object with all properties
     *
     * @param id       client's id
     * @param username client's username
     * @param email    client's email
     * @param password client's password
     */
    public Client(Integer id, String username, String email, String password) {
        super(id);
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
        Client client = (Client) o;
        return username.equals(client.username)
                && email.equals(client.email)
                && password.equals(client.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }

    @Override
    public String toString() {
        return "Client{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password +
                '}';
    }
}
