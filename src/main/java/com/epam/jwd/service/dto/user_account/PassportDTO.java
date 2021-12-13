package com.epam.jwd.service.dto.user_account;

import com.epam.jwd.service.dto.AbstractEntityDTO;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author mikh
 * PassportDTO class for Passport entity
 * @see com.epam.jwd.dao.entity.user_account.Passport
 */
public class PassportDTO extends AbstractEntityDTO<Integer> {

    private String seriaAndNumber;
    private String personalNumber;
    private LocalDate expirationDate;

    public PassportDTO() {
    }

    public PassportDTO(Integer id) {
        super(id);
    }

    public PassportDTO(Integer id, String seriaAndNumber, String personalNumber, LocalDate expirationDate) {
        super(id);
        this.seriaAndNumber = seriaAndNumber;
        this.personalNumber = personalNumber;
        this.expirationDate = expirationDate;
    }

    public PassportDTO(String seriaAndNumber, String personalNumber, LocalDate expirationDate) {
        this.seriaAndNumber = seriaAndNumber;
        this.personalNumber = personalNumber;
        this.expirationDate =expirationDate;
    }

    public String getSeriaAndNumber() {
        return seriaAndNumber;
    }

    public void setSeriaAndNumber(String seriaAndNumber) {
        this.seriaAndNumber = seriaAndNumber;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassportDTO that = (PassportDTO) o;
        return seriaAndNumber.equals(that.seriaAndNumber)
                && personalNumber.equals(that.personalNumber)
                && expirationDate.equals(that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seriaAndNumber, personalNumber, expirationDate);
    }

    @Override
    public String toString() {
        return "PassportDTO{" +
                "seriaAndNumber='" + seriaAndNumber + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
