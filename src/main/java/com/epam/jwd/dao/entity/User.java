package com.epam.jwd.dao.entity;


public class User extends AbstractEntity<Integer>{

    private String firstName;
    private String secondName;
    private String phoneNumber;
    private Integer age;
    private Gender gender;
    private Integer clientId;
    private UserRole role;
    private Integer passportId;

    public User() {
    }

    public User(Integer id) {
        super(id);
    }

    public User(Integer id, String firstName, String secondName, String phoneNumber, Integer age,
                Gender gender, Integer clientId, UserRole role, Integer passportId) {
        super(id);
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.clientId = clientId;
        this.role = role;
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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Integer getPassportId() {
        return passportId;
    }

    public void setPassportId(Integer passportId) {
        this.passportId = passportId;
    }

    public static class Builder {

        private Integer id;
        private String firstName;
        private String secondName;
        private String phoneNumber;
        private Integer age;
        private Gender gender;
        private Integer clientId;
        private UserRole role;
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

        public Builder withClientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder withRole(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder withPassportId(Integer passportId) {
            this.passportId = passportId;
            return this;
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public User build() {
            User user = new User(this.id);
            user.setAge(this.age);
            user.setClientId(this.clientId);
            user.setGender(this.gender);
            user.setFirstName(this.firstName);
            user.setPassportId(this.passportId);
            user.setPhoneNumber(this.phoneNumber);
            user.setSecondName(this.secondName);
            user.setRole(this.role);

            return user;
        }
    }
}