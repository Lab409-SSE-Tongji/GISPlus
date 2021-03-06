package com.rainlf.service;

import com.rainlf.mongo.entity.Recycle;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
public interface RecycleService {

    List<Recycle> getUserRecycles(String userId);

    List<Recycle> getOrganRecycles(String organId);

    List<Recycle> getAllRecycles();

    List<Recycle> getDeliverRecycle(String userId);

    String restoreRecycle(String recycleId);

    String deleteRecycle(String recycleId);

    String deleteRecycles(String userId);
}
