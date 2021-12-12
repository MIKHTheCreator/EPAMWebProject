package com.epam.jwd.dao.entity.user_account;

import com.epam.jwd.dao.entity.AbstractEntity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Passport entity class which extends AbstractEntity with Integer id property
 * This class describes typical passport information
 */
public class Passport extends AbstractEntity<Integer> {

    /**
     * String field with passport's seria and number
     */
    private String seriaAndNumber;
    /**
     * String field with passport's personal number
     */
    private String personalNumber;
    /**
     * LocalDate field with passport's expiration date
     *
     * @see LocalDate
     */
    private LocalDate expirationDate;

    /**
     * Constructor without arguments for creating empty passport object
     *
     * @see Passport#Passport(Integer)
     * @see Passport#Passport(Integer, String, String, LocalDate)
     */
    public Passport() {
    }

    /**
     * Constructor with Integer id argument for creating Passport with current id
     *
     * @param id provided id
     */
    public Passport(Integer id) {
        super(id);
    }

    /**
     * Constructor with all amount of passport properties for creating whole object
     *
     * @param id             passport's id
     * @param seriaAndNumber passport's seria and number
     * @param personalNumber person's personal number
     * @param expirationDate passport's expiration date
     */
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
