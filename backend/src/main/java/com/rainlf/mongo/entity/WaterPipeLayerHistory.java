package com.rainlf.mongo.entity;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/24.
 */
public class WaterPipeLayerHistory extends WaterPipeLayer{

    public Date createTime;



    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
