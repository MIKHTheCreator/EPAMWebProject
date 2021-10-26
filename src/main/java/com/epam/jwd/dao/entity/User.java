package com.epam.jwd.dao.entity;


import java.util.Objects;

public class User extends AbstractEntity<Integer>{

    private String firstName;
    private String secondName;
    private String phoneNumber;
    private Integer age;
    private Gender gender;
    private Client client;
    private UserRole role;
    private Passport passport;

    public User() {
    }

    public User(Integer id) {
        super(id);
    }

    public User(Integer id, String firstName, String secondName, String phoneNumber,
                Integer age, Gender gender, Client client, UserRole role, Passport passport) {
        super(id);
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.client = client;
        this.role = role;
        this.passport = passport;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
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
                && client.equals(user.client)
                && role == user.role
                && passport.equals(user.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, phoneNumber, age, gender, client, role, passport);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", client=" + client +
                ", role=" + role +
                ", passport=" + passport +
                '}';
    }

    public static class Builder {

        private Integer id;
        private String firstName;
        private String secondName;
        private String phoneNumber;
        private Integer age;
        private Gender gender;
        private Client client;
        private UserRole role;
        private Passport passport;

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

        public Builder withClient(Client client) {
            this.client = client;
            return this;
        }

        public Builder withRole(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder withPassport(Passport passport) {
            this.passport = passport;
            return this;
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public User build() {
            User user = new User(this.id);
            user.setAge(this.age);
            user.setClient(this.client);
            user.setGender(this.gender);
            user.setFirstName(this.firstName);
            user.setPassport(this.passport);
            user.setPhoneNumber(this.phoneNumber);
            user.setSecondName(this.secondName);
            user.setRole(this.role);

            return user;
        }
    }
}