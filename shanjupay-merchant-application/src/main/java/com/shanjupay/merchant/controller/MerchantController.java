package com.shanjupay.merchant.controller;

import com.shanjupay.merchant.api.MerchantService;
import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.convert.MerchantRegisterConvert;
import com.shanjupay.merchant.service.SmsService;
import com.shanjupay.merchant.vo.MerchantRegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@Api(value = "商户平台应用接口", tags = "商户平台应用接口", description = "商户平台应用接口")
public class MerchantController {

    @org.apache.dubbo.config.annotation.Reference
    private MerchantService merchantService;//注入远程调用接口


    @Autowired
    private SmsService smsService;//将本地bean接口注入

    @ApiOperation(value = "根据id查询商户信息")
    @GetMapping("/merchants/{id}")
    public MerchantDTO queryMerchantById(@PathVariable("id") Long id) {

        MerchantDTO merchantDTO = merchantService.queryMerchantById(id);
        return merchantDTO;
    }

    @ApiOperation("获取手机验证码")
    @GetMapping("/sms")
    @ApiImplicitParam(value = "手机号码", name = "phone", required = true, dataType = "String", paramType = "query")
    public String getSMSCode(@RequestParam String phone) {
        //向验证服务请求发送验证码
        return smsService.sendMsg(phone);
    }

    @ApiOperation("注册商户")
    @ApiImplicitParam(name = "merchantRegister", value = "注册信息", required = true, dataType = "MerchantRegisterVO", paramType = "body")
    @PostMapping("/merchants/register")
    public MerchantRegisterVO registerMerchant(@RequestBody MerchantRegisterVO merchantRegisterVo) {
        //校验验证码
        smsService.checkVerifiyCode(merchantRegisterVo.getVerifiykey(), merchantRegisterVo.getVerifiyCode());

        //调用dubbo服务接口
/*        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setUsername(merchantRegisterVo.getUsername());
        merchantDTO.setMobile(merchantRegisterVo.getMobile());*/
        //使用MapStruct转换对象
        MerchantDTO merchantDTO1 = MerchantRegisterConvert.INSTANSE.vo2dto(merchantRegisterVo);
        merchantService.createMerchant(merchantDTO1);
        return merchantRegisterVo;
    }

    @ApiOperation("测试")
    @GetMapping(path = "/hello")
    public String hello() {
        return "hello";
    }

    @ApiOperation("测试")
    @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "string")
    @PostMapping(value = "/hi")
    public String hi(String name) {
        return "hi," + name;
    }
}
