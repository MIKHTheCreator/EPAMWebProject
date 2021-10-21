package com.epam.jwd.dao.entity;

import java.time.LocalDate;
import java.util.Objects;

public class PassportData extends AbstractEntity<Integer> {

    private String seriesAndNumber;
    private String personalNumber;
    private LocalDate expirationTime;

    public PassportData() {
    }

    public PassportData(Integer id) {
        super(id);
    }

    public PassportData(Integer id, String seriesAndNumber, String personalNumber, LocalDate expirationTime) {
        super(id);
        this.seriesAndNumber = seriesAndNumber;
        this.personalNumber = personalNumber;
        this.expirationTime = expirationTime;
    }

    public String getSeriesAndNumber() {
        return seriesAndNumber;
    }

    public void setSeriesAndNumber(String seriesAndNumber) {
        this.seriesAndNumber = seriesAndNumber;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public LocalDate getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDate expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassportData that = (PassportData) o;
        return seriesAndNumber.equals(that.seriesAndNumber)
                && personalNumber.equals(that.personalNumber)
                && expirationTime.equals(that.expirationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seriesAndNumber, personalNumber, expirationTime);
    }

    @Override
    public String toString() {
        return "PassportData{" +
                "seriesAndNumber='" + seriesAndNumber + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
