package com.depinx.data.service;

import com.alibaba.fastjson2.JSONObject;
import com.depinx.data.constants.CommonConstants;
import com.depinx.data.entity.DataEntity;
import com.lark.oapi.Client;
import com.lark.oapi.service.bitable.v1.model.AppTableRecord;
import com.lark.oapi.service.bitable.v1.model.ListAppTableRecordReq;
import com.lark.oapi.service.bitable.v1.model.ListAppTableRecordResp;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class LarkService {
    @Resource
    private RedisTemplate<String, List<DataEntity>> redisTemplate;

    public void fetchLark() {
        try {
            ListAppTableRecordReq listAppTableRecordReq = ListAppTableRecordReq.newBuilder()
                    .appToken(CommonConstants.LARK_APP_TOKEN)
                    .tableId(CommonConstants.SOURCE_TABLE_ID)
                    .viewId(CommonConstants.SOURCE_VIEW_ID)
                    .pageSize(200)
                    .build();

            Client client = Client.newBuilder(CommonConstants.LARK_APP_ID, CommonConstants.LARK_APP_SECRET).build();
            ListAppTableRecordResp response = client.bitable().v1().appTableRecord().list(listAppTableRecordReq);
            Set<String> check = new HashSet<>();
            List<DataEntity> list = new ArrayList<>();
            if (response.success()) {
                for(AppTableRecord record: response.getData().getItems()){
                    String projectName = record.getFields().get("项目名称").toString();
                    if (check.contains(projectName)) {
                        continue;
                    }
                    check.add(projectName);
                    String coinName = record.getFields().get("代币名称").toString();
                    String price = record.getFields().get("当前币价（实时更新").toString();
                    log.info("record: {}, {}, {}", projectName, coinName, price);
                    DataEntity dataEntity = new DataEntity();
                    dataEntity.setProjectName(projectName);
                    dataEntity.setCoinName(coinName);
                    dataEntity.setPrice(price);
                    list.add(dataEntity);
                }
            }
            redisTemplate.opsForValue().set(CommonConstants.LARK_TABLE_CACHE_KEY, list);
        }catch (Exception e) {
            log.error("fetchLark failed: {}", e.getMessage());
        }
    }

    public List<String> getCoinList() {
        List<DataEntity> dataEntities = redisTemplate.opsForValue().get(CommonConstants.LARK_TABLE_CACHE_KEY);
        List<String> list = new ArrayList<>();
        if (dataEntities != null) {
            for (DataEntity dataEntity : dataEntities) {
                list.add(dataEntity.getCoinName());
            }
        }
        return list;
    }
}

