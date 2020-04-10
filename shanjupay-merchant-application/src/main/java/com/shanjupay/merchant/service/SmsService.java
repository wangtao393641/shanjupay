package com.shanjupay.merchant.service;

/**
 * @Auther: wangtao
 * @Date: 2020/4/8 13:18
 * @Description:
 */
public interface SmsService {

    /**
     *发送手机验证码
     * @param phone 手机号
     * @return 验证码对应key
     */
    String sendMsg(String phone);
    /**
     * 校验手机验证码
     * @param verifiykey验证码key
     * @param verifiyCode验证码
     */
    void checkVerifiyCode(String verifiyKey,String verifiyCode);

}
