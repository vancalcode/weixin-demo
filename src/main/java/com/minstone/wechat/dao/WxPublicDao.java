package com.minstone.wechat.dao;

import com.minstone.wechat.domain.WxPublic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WxPublicDao {

    int deleteByPrimaryKey(String publicCode);

    int insert(WxPublic record);

    int insertSelective(WxPublic record);

    String selectImgCodeByPrimaryKey(String publicCode);

    WxPublic selectByPrimaryKey(String publicCode);

    int updateByPrimaryKeySelective(WxPublic record);

    List<WxPublic> selectAll();

    int updateByPrimaryKey(WxPublic record);
}