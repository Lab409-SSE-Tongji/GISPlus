package com.rainlf.mongo.domain;

/**
 * Created by Administrator on 2017/7/6.
 */
public class WellDomain {

    private String x;
    private String y;
    private String z;
    private String status;

    public WellDomain() {
    }

    public WellDomain(String x, String y, String z, String status) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.status = status;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
