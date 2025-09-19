package com.job_portal.Lates_Jobs.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Salary {
    private Integer rangeMin;
    private Integer rangeMax;
    private String currency;
    private String period;

    // Getters and Setters
    public Integer getRangeMin() { return rangeMin; }
    public void setRangeMin(Integer rangeMin) { this.rangeMin = rangeMin; }
    public Integer getRangeMax() { return rangeMax; }
    public void setRangeMax(Integer rangeMax) { this.rangeMax = rangeMax; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}
