package com.shanjupay.merchant.api;

import com.shanjupay.merchant.api.dto.MerchantDTO;

/**
 * Created by Administrator.
 */
public interface MerchantService {

    //根据 id查询商户
    public MerchantDTO queryMerchantById(Long id);

    /**
     * 注册商户服务接口,接受账号,密码,手机号码,为了可扩展性使用merchantDto接受数据
     * @param
     * @return 注册成功的商户信息
     */
    MerchantDTO createMerchant(MerchantDTO merchantDTO);

}
