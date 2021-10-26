package com.epam.jwd.dao.entity.user_account;

import com.epam.jwd.dao.entity.AbstractEntity;

import java.time.LocalDate;
import java.util.Objects;

public class Passport extends AbstractEntity<Integer> {

    private String seriaAndNumber;
    private String personalNumber;
    private LocalDate expirationDate;

    public Passport() {
    }

    public Passport(Integer id) {
        super(id);
    }

    public Passport(Integer id, String seriaAndNumber, String personalNumber, LocalDate expirationDate) {
        super(id);
        this.seriaAndNumber = seriaAndNumber;
        this.personalNumber = personalNumber;
        this.expirationDate = expirationDate;
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
        Passport passport = (Passport) o;
        return seriaAndNumber.equals(passport.seriaAndNumber)
                && personalNumber.equals(passport.personalNumber)
                && expirationDate.equals(passport.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seriaAndNumber, personalNumber, expirationDate);
    }

    @Override
    public String toString() {
        return "Passport{" +
                "seriaAndNumber='" + seriaAndNumber + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
