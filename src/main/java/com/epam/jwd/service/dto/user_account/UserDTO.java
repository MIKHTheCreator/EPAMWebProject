package com.epam.jwd.service.dto.user_account;

import com.epam.jwd.dao.entity.user_account.Gender;
import com.epam.jwd.dao.entity.user_account.Role;
import com.epam.jwd.service.dto.AbstractEntityDTO;

import java.util.Objects;

public class UserDTO extends AbstractEntityDTO<Integer> {

    private String firstName;
    private String secondName;
    private String phoneNumber;
    private Integer age;
    private Gender gender;
    private Role role;
    private Integer clientId;
    private Integer passportId;

    public UserDTO() {
    }

    public UserDTO(Integer id) {
        super(id);
    }

    public UserDTO(Integer id, String firstName, String secondName, String phoneNumber,
                Integer age, Gender gender, Role role, Integer clientId,  Integer passportId) {
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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getPassportId() {
        return passportId;
    }

    public void setPassportId(Integer passportId) {
        this.passportId = passportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return firstName.equals(userDTO.firstName)
                && secondName.equals(userDTO.secondName)
                && phoneNumber.equals(userDTO.phoneNumber)
                && age.equals(userDTO.age)
                && gender == userDTO.gender
                && role == userDTO.role
                && clientId.equals(userDTO.clientId)
                && passportId.equals(userDTO.passportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, phoneNumber, age, gender, role, clientId, passportId);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", role=" + role +
                ", clientId=" + clientId +
                ", passportId=" + passportId +
                '}';
    }
}
