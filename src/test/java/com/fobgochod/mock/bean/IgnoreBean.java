package com.fobgochod.mock.bean;

import com.fobgochod.mock.annotation.MockIgnore;

public class IgnoreBean {

    @MockIgnore
    private String ignore;
    private int notIgnore;

    public String getIgnore() {
        return ignore;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }

    public int getNotIgnore() {
        return notIgnore;
    }

    public void setNotIgnore(int notIgnore) {
        this.notIgnore = notIgnore;
    }
}
