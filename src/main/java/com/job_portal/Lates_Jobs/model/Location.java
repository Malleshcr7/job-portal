package com.job_portal.Lates_Jobs.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Location {
    private String city;
    private String state;
    private String country;
    private boolean isRemote;
    private boolean isHybrid;
    private boolean isOnsite;

    // Getters and Setters
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public boolean isRemote() { return isRemote; }
    public void setRemote(boolean remote) { isRemote = remote; }
    public boolean isHybrid() { return isHybrid; }
    public void setHybrid(boolean hybrid) { isHybrid = hybrid; }
    public boolean isOnsite() { return isOnsite; }
    public void setOnsite(boolean onsite) { isOnsite = onsite; }
}
