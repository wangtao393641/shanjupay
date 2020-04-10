package com.shanjupay.merchant.service;

import com.shanjupay.merchant.api.MerchantService;
import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.convert.MerchantConvert;
import com.shanjupay.merchant.entity.Merchant;
import com.shanjupay.merchant.mapper.MerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator.
 */
@org.apache.dubbo.config.annotation.Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    MerchantMapper merchantMapper;

    @Override
    public MerchantDTO queryMerchantById(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setId(merchant.getId());
        merchantDTO.setMerchantName(merchant.getMerchantName());
        //....
        return merchantDTO;
    }

    /**
     * 注册商户服务接口,接受账号,密码,手机号码,为了可扩展性使用merchantDto接受数据
     *
     * @param
     * @return 注册成功的商户信息
     */

    @Override
    public MerchantDTO createMerchant(MerchantDTO merchantDTO) {
        //Merchant merchant = new Merchant();
        //设置手机号
        //merchant.setMobile(merchantDTO.getMobile());
        //设置联系人
        // merchant.setUsername(merchantDTO.getUsername());
        //使用MapStruct进行对象转换
        Merchant merchant = MerchantConvert.INSTANCE.dto2entity(merchantDTO);
        //设置审核状态0‐未申请,1‐已申请待审核,2‐审核通过,3‐审核拒绝
        merchant.setAuditStatus("0");
        //保存商户信息
        merchantMapper.insert(merchant);
        //将新增商户的id回写到merchantDTO
        //merchantDTO.setId(merchant.getId());
        //将entiy转换dto
        return MerchantConvert.INSTANCE.entity2dto(merchant);

    }
}
