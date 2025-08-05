package com.depinx.data.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.depinx.data.constants.CommonConstants;
import com.depinx.data.entity.DataEntity;
import com.depinx.data.entity.QueryRequest;
import com.depinx.data.entity.ResponseParam;
import com.depinx.data.utils.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CexService {
    @Resource
    private LarkService larkService;

    public List<DataEntity> getDataEntity(QueryRequest queryRequest) {
        List<DataEntity> dataEntities = new ArrayList<>();
        List<String> coinNames = new ArrayList<>();
        if (queryRequest.getCoinName() == null || queryRequest.getCoinName().isEmpty()) {
            coinNames.addAll(larkService.getCoinList());
        }else {
            coinNames.add(queryRequest.getCoinName());
        }
        for (String coinName : coinNames) {
            DataEntity response = new DataEntity();
            response.setCoinName(coinName);
            BigDecimal price = price(coinName);
            if (price != null) {
                response.setPrice(price.toString());
            }

            if (queryRequest.getStartTime() == null || queryRequest.getEndTime() == null) {
                StringBuilder okxTwapData = new StringBuilder();
                StringBuilder okxMaDate = new StringBuilder();
                StringBuilder bnTwapData = new StringBuilder();
                StringBuilder bnMaDate = new StringBuilder();
                for (Map.Entry<String, Integer> entry: CommonConstants.DAY_INTERVALS.entrySet()) {
                    log.info("key:{}, value: {}", entry.getKey(), entry.getValue());
                    long endTime = System.currentTimeMillis();
                    long startTime = endTime - TimeUnit.DAYS.toMillis(entry.getValue());
                    ResponseParam params = queryOkx(coinName, startTime, endTime);
                    if (params != null) {
                        okxTwapData.append(params.getTwap()).append("/");
                        okxMaDate.append(params.getMa()).append("/");
                    }
                    params = queryBn(coinName, startTime, endTime);
                    if (params != null) {
                        bnTwapData.append(params.getTwap()).append("/");
                        bnMaDate.append(params.getMa()).append("/");
                    }
                }
                response.setOkxTwap(StringUtils.removeTrailingSlash(bnTwapData.toString()));
                response.setOkxMa(StringUtils.removeTrailingSlash(bnMaDate.toString()));
                response.setBnMa(StringUtils.removeTrailingSlash(bnMaDate.toString()));
                response.setBnTwap(StringUtils.removeTrailingSlash(bnTwapData.toString()));
            }else {

                ResponseParam params = queryOkx(coinName, queryRequest.getStartTime().getTime(), queryRequest.getEndTime().getTime());
                if (params != null) {
                    response.setOkxTwap(StringUtils.removeTrailingSlash(params.getTwap()));
                    response.setOkxMa(StringUtils.removeTrailingSlash(params.getMa()));
                }
                params = queryBn(coinName, queryRequest.getStartTime().getTime(), queryRequest.getEndTime().getTime());
                if (params != null) {
                    response.setBnTwap(StringUtils.removeTrailingSlash(params.getTwap()));
                    response.setBnMa(StringUtils.removeTrailingSlash(params.getMa()));
                }
            }
            dataEntities.add(response);
        }
        return dataEntities;
    }

    public ResponseParam queryBn(String coinName, long startTime, long endTime) {
        try{
            String symbol = coinName + "USDT";
            String url = CommonConstants.BINANCE_BASE_URL +
                    "/api/v3/klines" +
                    "?symbol=" + symbol + "&interval=1h" +
                    "&startTime=" + startTime + "&endTime=" + endTime +
                    "&limit=1000";
            log.info("query bn url:{}", url);
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            log.info("query bn response: {}", response);
            JSONArray arr = JSONArray.parseArray(response);
            List<Double> closes = new ArrayList<>();
            double total = 0;
            double volume = 0;
            for (Object o : arr) {
                JSONArray line = (JSONArray) o;
                double close = Double.parseDouble(line.getString(4));
                double vol = Double.parseDouble(line.getString(5));
                closes.add(close); // close price
                volume += vol;
                total += close * vol;
            }

            double twap = total / volume;
            double ma = closes.stream().mapToDouble(d -> d).average().orElse(0.0);

            ResponseParam responseParam = new ResponseParam();
            responseParam.setMa(String.format("%.6f", ma));
            responseParam.setTwap(String.format("%.6f", twap));
            return responseParam;

        }catch (Exception e) {
            log.error("query bn error: {}", e.getMessage());
        }
        return null;
    }

    public ResponseParam queryOkx(String coinName, long startTime, long endTime) {
        try {
            String okxSymbol = coinName + "-USDT";
            String url = CommonConstants.OKX_BASE_URL +
                    "/api/v5/market/history-candles" +
                    "?instId=" + okxSymbol + "&bar=1H" +
                    "&after=" + endTime + "&before=" + startTime +
                    "&limit=1000";
            log.info("queryOkx url:{}", url);
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            log.info("queryOkx response: {}", response);
            JSONObject result = JSONObject.parseObject(response);
            if (!"0".equals(result.getString("code"))) {
                log.warn("OKX Error: {}", result.getString("msg"));
                return null;
            }else {
                JSONArray dataArr = result.getJSONArray("data");
                log.info("dataArr size:{}", dataArr.size());
                List<Double> closes = new ArrayList<>();
                double total = 0;
                double volume = 0;
                for (Object o : dataArr) {
                    JSONArray kline = (JSONArray) o;
                    double close = Double.parseDouble(kline.getString(4));
                    double vol = Double.parseDouble(kline.getString(5));
                    closes.add(close); // close price
                    total += close * vol;
                    volume += vol;
                }

                double twap = total / volume;
                double ma = closes.stream().mapToDouble(d -> d).average().orElse(0.0);

                ResponseParam responseParam = new ResponseParam();
                responseParam.setMa(String.format("%.6f", ma));
                responseParam.setTwap(String.format("%.6f", twap));
                return responseParam;
            }
        }catch (Exception e) {
            log.error("query okx error: {}", e.getMessage());
            return null;
        }
    }

    public BigDecimal price(String coinName) {
        try{
            RestTemplate restTemplate = new RestTemplate();

            String url = String.format("https://www.okx.com/api/v5/market/ticker?instId=%s-USDT", coinName);
            String uri = UriComponentsBuilder
                    .fromUriString(url)
                    .toUriString();

            String responseStr = restTemplate.getForObject(uri, String.class);
            log.info("price responseStr:{}", responseStr);
            JSONObject json = JSONObject.parseObject(responseStr);

            if ("0".equals(json.getString("code"))) {
                JSONArray dataArray = json.getJSONArray("data");
                if (dataArray != null && !dataArray.isEmpty()) {
                    JSONObject ticker = dataArray.getJSONObject(0);
                    return new BigDecimal(ticker.getString("last"));  // 获取最新成交价
                }
            }
            return null;
        }catch (Exception e) {
            log.error("price error: {}", e.getMessage());
            return null;
        }
    }
}
