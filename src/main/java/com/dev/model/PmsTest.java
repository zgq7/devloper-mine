package com.dev.model;

import javax.persistence.*;

@Table(name = "pms_test")
public class PmsTest {
    @Id
    private Integer id;

    private String pms;

    public PmsTest(Integer id, String pms) {
        this.id = id;
        this.pms = pms;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return pms
     */
    public String getPms() {
        return pms;
    }

    /**
     * @param pms
     */
    public void setPms(String pms) {
        this.pms = pms;
    }
}