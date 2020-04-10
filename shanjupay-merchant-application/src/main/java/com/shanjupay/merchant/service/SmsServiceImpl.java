package com.shanjupay.merchant.service;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: wangtao
 * @Date: 2020/4/8 13:22
 * @Description:
 */
@org.springframework.stereotype.Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Value("${sms.url}")
    private String smsUrl;

    @Value("${sms.effectiveTime}")
    private String effectiveTime;


    @Autowired
    private RestTemplate restTemplate;

    /**
     * 发送手机验证码
     *
     * @param phone 手机号
     * @return
     */

    @Override
    public String sendMsg(String phone) {
        //向验证码服务发送请求获取验证码
        String sms_url = smsUrl + "/generate?name=sms&effectiveTime=" + effectiveTime;
        //请求体
        Map<String, Object> body = new HashMap();
        body.put("mobile", phone);
        //请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        //指定Content-Type:application/json
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //请求信息，传入body.header
        HttpEntity httpEntity = new HttpEntity(body, httpHeaders);
        //向Url请求
        ResponseEntity<Map> exchange = null;
        Map bodyMap = null;
        try {
            exchange = restTemplate.exchange(sms_url, HttpMethod.POST, httpEntity, Map.class);
            log.info("请求验证吗服务，得到响应：{}", JSON.toJSONString(exchange));
            bodyMap = exchange.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException("发送验证码失败");
        }

        if (bodyMap == null || bodyMap.get("result") == null) {
            throw new RuntimeException("发送验证码失败");

        }
        log.info("请求验证吗服务，得到响应：{}", JSON.toJSONString(exchange));
        Map result = (Map) bodyMap.get("result");
        String key = (String) result.get("key");
        log.info("得到发送验证码对应的key:{}", key);
        return key;
    }

    @Override
    public void checkVerifiyCode(String verifiyKey, String verifiyCode) {
        //校验验证码的url
        String url = "http://localhost:56085/sailing/verify?name=sms&verificationCode=" + verifiyCode +
                "&verificationKey=" + verifiyKey;
        Map bodyMap = null;
        try {
            //使用restTemplate请求验证服务
            ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, Map.class);
            log.info("请求验证吗服务，得到响应：{}", JSON.toJSONString(exchange));
            bodyMap = exchange.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException("校验验证码失败");
        }
        if(bodyMap==null||bodyMap.get("result")==null||!(Boolean)bodyMap.get("result")){
            throw new RuntimeException("校验验证码失败");

        }

    }
}