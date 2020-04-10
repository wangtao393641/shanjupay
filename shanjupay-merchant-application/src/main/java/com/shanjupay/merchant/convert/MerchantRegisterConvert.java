package com.shanjupay.merchant.convert;


import com.shanjupay.merchant.api.dto.MerchantDTO;
import com.shanjupay.merchant.vo.MerchantRegisterVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Auther: wangtao
 * @Date: 2020/4/10 10:50
 * @Description:
 */
@Mapper
public interface MerchantRegisterConvert {
    MerchantRegisterConvert INSTANSE = Mappers.getMapper(MerchantRegisterConvert.class);

    //将dto转换vo
    MerchantDTO vo2dto(MerchantRegisterVO merchantRegisterVO);

    //将vo转换dto
    MerchantRegisterVO dto2vo(MerchantDTO merchantDTO);


}
