package com.epam.jwd.dao.entity.user_account;

import com.epam.jwd.dao.entity.AbstractEntity;

import java.util.Objects;

public class Client extends AbstractEntity<Integer> {

    private String username;
    private String email;
    private String password;
    private User user;

    public Client() {
    }

    public Client(Integer id) {
        super(id);
    }

    public Client(Integer id, String username, String email, String password, User user) {
        super(id);
        this.username = username;
        this.email = email;
        this.password = password;
        this.user = user;
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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return username.equals(client.username)
                && email.equals(client.email)
                && password.equals(client.password)
                && user.equals(client.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password, user);
    }

    @Override
    public String toString() {
        return "Client{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", user=" + user +
                '}';
    }
}
