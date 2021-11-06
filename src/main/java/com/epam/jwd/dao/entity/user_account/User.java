package com.epam.jwd.dao.entity.user_account;


import com.epam.jwd.dao.entity.AbstractEntity;

import java.util.Objects;

public class User extends AbstractEntity<Integer> {

    private String firstName;
    private String secondName;
    private String phoneNumber;
    private Integer age;
    private Gender gender;
    private Role role;
    private Integer clientId;
    private Integer passportId;

    public User() {
    }

    public User(Integer id) {
        super(id);
    }

    public User(Integer id, String firstName, String secondName, String phoneNumber,
                Integer age, Gender gender, Role role, Integer clientId, Integer passportId) {
        super(id);
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.role = role;
        this.clientId = clientId;
        this.passportId = passportId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getPassportId() {
        return passportId;
    }

    public void setPassportId(Integer passportId) {
        this.passportId = passportId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return firstName.equals(user.firstName)
                && secondName.equals(user.secondName)
                && phoneNumber.equals(user.phoneNumber)
                && age.equals(user.age)
                && gender == user.gender
                && role == user.role
                && clientId.equals(user.clientId)
                && passportId.equals(user.passportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, phoneNumber, age, gender, role, clientId, passportId);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", role=" + role +
                ", client=" + clientId +
                ", passport=" + passportId +
                '}';
    }

    public static class Builder {

        private Integer id;
        private String firstName;
        private String secondName;
        private String phoneNumber;
        private Integer age;
        private Gender gender;
        private Role role;
        private Integer clientId;
        private Integer passportId;

        public Builder() {
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withSecondName(String secondName) {
            this.secondName = secondName;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withAge(Integer age) {
            this.age = age;
            return this;
        }

        public Builder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder withRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder withPassportId(Integer passportId) {
            this.passportId = passportId;
            return this;
        }

        public Builder withClientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public User build() {
            User user = new User(this.id);
            user.setAge(this.age);
            user.setGender(this.gender);
            user.setFirstName(this.firstName);
            user.setPassportId(this.passportId);
            user.setPhoneNumber(this.phoneNumber);
            user.setSecondName(this.secondName);
            user.setClientId(this.clientId);
            user.setRole(this.role);

            return user;
        }
    }
}