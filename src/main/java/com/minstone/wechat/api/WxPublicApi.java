package com.minstone.wechat.api;

import com.minstone.wechat.model.Result;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by huangyg on 2017/9/20.
 *
 * 对外提供的公众号管理的接口
 */
public interface WxPublicApi {
    /**
     *
     * @param reqMap 公众号信息
     * @param publicHeadImg 公众号头像
     * @param publicQrcode  公众号二维码
     * @return
     * @throws WxErrorException
     * @throws IOException
     *
     * 添加公众号信息
     */
    Result addPublicAccount(Map<String, Object> reqMap, MultipartFile publicHeadImg, MultipartFile publicQrcode) throws WxErrorException, IOException;


    /**
     *
     * @param publicCode 公众号主键
     * @return
     * @throws WxErrorException
     * @throws IOException
     *
     * 获取某个公众号信息
     */
    Result getPublicAccount(String publicCode) throws WxErrorException, IOException;

    /**
     *
     * @return
     * @throws WxErrorException
     * @throws IOException
     *
     * 获取所有公众号信息
     */
    Result getAllPublicAccount() throws WxErrorException, IOException;

    /**
     *
     * @param publicCode 公众号主键
     * @param reqMap 公众号信息
     * @param publicHeadImg 公众号头像
     * @param publicQrcode 公众号二维码
     * @return
     * @throws WxErrorException
     * @throws IOException
     *
     * 更新公众号信息
     */
    Result updatePublicAccount(String publicCode, Map<String,Object>reqMap, MultipartFile publicHeadImg, MultipartFile publicQrcode) throws WxErrorException, IOException;

    /**
     *
     * @param publicCode 公众号主键
     * @return
     * @throws WxErrorException
     * @throws IOException
     *
     * 删除某个公众号
     */
    Result deletePublicAccount(String publicCode) throws WxErrorException, IOException;


    void downloadIcon(String imgCode, Integer imgType , HttpServletResponse response) throws WxErrorException, IOException;

    void showIcon(String imgCode, Integer imgType) throws WxErrorException, IOException;
}