package com.exadel.borsch.domain;

import java.util.Date;
import java.util.Map;

/**
 * Created by srw on 8/1/13.
 */
public class Menu {
    private Long id;
    private Date first_day_of_week;
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFirst_day_of_week() {
        return first_day_of_week;
    }

    public void setFirst_day_of_week(Date first_day_of_week) {
        this.first_day_of_week = first_day_of_week;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
