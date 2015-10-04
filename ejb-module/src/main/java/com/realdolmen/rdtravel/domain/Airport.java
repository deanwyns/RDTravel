package com.realdolmen.rdtravel.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by JSTAX29 on 2/10/2015.
 * An airport where flights me depart from or arrive in.
 */
@Entity
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String country;
    private String iataFaa;
    private String icao;
    private double latitude;
    private double longitude;
    private double altitude;
    private double timezone;
    private char daylightSavingsTime;
    private String timezoneTzFormat;

    protected Airport(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIataFaa() {
        return iataFaa;
    }

    public void setIataFaa(String iataFaa) {
        this.iataFaa = iataFaa;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getTimezone() {
        return timezone;
    }

    public void setTimezone(double timezone) {
        this.timezone = timezone;
    }

    public char getDaylightSavingsTime() {
        return daylightSavingsTime;
    }

    public void setDaylightSavingsTime(char daylightSavingsTime) {
        this.daylightSavingsTime = daylightSavingsTime;
    }

    public String getTimezoneTzFormat() {
        return timezoneTzFormat;
    }

    public void setTimezoneTzFormat(String timezoneTzFormat) {
        this.timezoneTzFormat = timezoneTzFormat;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Airport{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", iataFaa='").append(iataFaa).append('\'');
        sb.append(", icao='").append(icao).append('\'');
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", altitude=").append(altitude);
        sb.append(", timezone=").append(timezone);
        sb.append(", daylightSavingsTime=").append(daylightSavingsTime);
        sb.append(", timezoneTzFormat='").append(timezoneTzFormat).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
