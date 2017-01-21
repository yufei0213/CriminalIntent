package com.bignerdanch.android.criminalintent.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by wangyufei on 16/10/16.
 */

public class Crime {

    private UUID id;
    private String title;
    private Date date;
    private boolean solved;

    public Crime() {

        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        this.id = id;
        date = new Date();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
