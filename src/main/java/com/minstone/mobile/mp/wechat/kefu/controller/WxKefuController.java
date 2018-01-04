package com.minstone.mobile.mp.wechat.kefu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minstone.mobile.mp.common.CommonResult;
import com.minstone.mobile.mp.common.constants.CommonResultEnum;
import com.minstone.mobile.mp.utils.DateUtil;
import com.minstone.mobile.mp.utils.FileUtil;
import com.minstone.mobile.mp.utils.ResultUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.request.WxMpKfAccountRequest;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfInfo;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfList;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfMsgList;
import me.chanjar.weixin.mp.bean.kefu.result.WxMpKfMsgRecord;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;
import sun.tools.jconsole.Worker;

import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by huangyg on 2017/8/7.
 */
@RestController
@RequestMapping("/kefu")
public class WxKefuController {

    @Autowired
    private WxMpService service;

    // 获取客服列表
    @GetMapping("/getPage")
    public CommonResult getPage(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) throws WxErrorException {

        // pageHelper 只能用于有数据库的查询列表，由于该接口直接从微信获取数据，所以这里手动构造分页数据。
        WxMpKfList list = this.service.getKefuService().kfList();
        PageInfo<WxMpKfInfo> page = new PageInfo<>(list.getKfList());
        int totalPages = (int) Math.ceil((double) page.getSize() / pageSize);
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        if (pageSize > page.getSize()) {
            currentPage = 1;
            pageSize = page.getSize();
        }

        page.setPageNum(currentPage);
        page.setPages(totalPages);// 向上取整
        int endIndex = (currentPage * pageSize) < page.getSize() ? (currentPage * pageSize) : page.getSize();

        int currentPageSize = (endIndex == pageSize) ? pageSize : (page.getSize() - (currentPage - 1) * pageSize);
        page.setPageSize(currentPageSize);

        page.setList(list.getKfList().subList((currentPage - 1) * pageSize, endIndex));
        return ResultUtil.pageFormat(page);
    }

    // 获取在线客服列表
    @GetMapping("/onlineList")
    public CommonResult kfOnlineList() throws WxErrorException {
        return ResultUtil.success(this.service.getKefuService().kfOnlineList());

    }

    // 创建客服
    @PostMapping("/new")
    public CommonResult kfAccountNew(WxMpKfAccountRequest account) throws WxErrorException {
        return ResultUtil.success(this.service.getKefuService().kfAccountAdd(account));
    }

    // 上传客服头像
    @PostMapping("/uploadImg")
    public CommonResult kfAccountUploadHeadImg(String account, File imgFile) throws WxErrorException {
        return ResultUtil.success();
    }

    // 邀请客服
    @PostMapping("/invite")
    public CommonResult kfAccountInvite(@ModelAttribute WxMpKfAccountRequest account) throws WxErrorException {
        return ResultUtil.success(this.service.getKefuService().kfAccountInviteWorker(account));
    }

    // 添加、上传头像并邀请客服
    @PostMapping("/add")
    public CommonResult kfAccountAdd(WxMpKfAccountRequest account, @RequestParam(value = "headImg", required = false) MultipartFile multfile) throws WxErrorException, IOException {
        if (account.getKfAccount() == null) {
            return ResultUtil.failure(CommonResultEnum.KEFU_ACCOUNT_ERROR);
        }
        if (account.getInviteWx() == null) {
            return ResultUtil.failure(CommonResultEnum.KEFU_INVITE_ERROR);
        }
        if (account.getNickName() == null) {
            return ResultUtil.failure(CommonResultEnum.KEFU_INVITE_ERROR);
        }
        // 1. 创建客服账号
        // 完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线，后缀为公众号微信号，长度不超过30个字符
        this.service.getKefuService().kfAccountAdd(account);
        // 2. 上传头像
        if (multfile != null) {
            File file = FileUtil.convert(multfile);
            // todo - 判断图片格式
            if (!this.service.getKefuService().kfAccountUploadHeadImg(account.getKfAccount(), file)) {
                // 上传失败后应该删除客服账号
                this.service.getKefuService().kfAccountDel(account.getKfAccount());
                return ResultUtil.failure(CommonResultEnum.KEFU_HEADIMG_ERROR);
            } else {

            }
            file.delete();
        }
        // 3. 邀请微信用户
        return ResultUtil.success(this.service.getKefuService().kfAccountInviteWorker(account));
    }

    // 删除客服
    @GetMapping("/delete")
    public CommonResult kfAccountDelete(@RequestParam String account) throws WxErrorException {
        return ResultUtil.success(this.service.getKefuService().kfAccountDel(account));

    }
//    // 更新客服信息
//    @PostMapping("/update")
//    public CommonResult kfAccountUpdate(WxMpKfAccountRequest account,@RequestParam("file") MultipartFile multfile) throws WxErrorException{
//        return ResultUtil.success(this.service.getKefuService().kfAccountUpdate(account));
//    }
    // 更新客服信息
    @PostMapping("/update")
    public CommonResult test(WxMpKfAccountRequest account, @RequestParam(value = "headImg", required = false) MultipartFile multfile) throws WxErrorException, IOException {
        if (account.getKfAccount() == null) {
            return ResultUtil.failure(CommonResultEnum.KEFU_ACCOUNT_ERROR);
        }
        if (multfile != null) {
            File file = FileUtil.convert(multfile);
            if (!this.service.getKefuService().kfAccountUploadHeadImg(account.getKfAccount(), file)) {
                return ResultUtil.failure(CommonResultEnum.KEFU_HEADIMG_ERROR);
            }
            file.delete();
        }
        return ResultUtil.success(this.service.getKefuService().kfAccountUpdate(account));
    }

    // 创建会话
    @PostMapping("/sessionCreate")
    public CommonResult kfSessionCreate(String openid, String account) throws WxErrorException {
        return ResultUtil.success(this.service.getKefuService().kfSessionCreate(openid, account));

    }

    // 关闭会话
    @PostMapping("/sessionClose")
    public CommonResult kfSessionClose(String openid, String account) throws WxErrorException {
        return ResultUtil.success(this.service.getKefuService().kfSessionClose(openid, account));

    }

    // 获取客户会话状态
    @GetMapping("/sessionGet")
    public CommonResult kfSessionGet(@RequestParam String openid) throws WxErrorException {
        return ResultUtil.success(this.service.getKefuService().kfSessionGet(openid));

    }

    // 获取客服的会话列表
    @GetMapping("/sessionList")
    public CommonResult kfSessionList(@RequestParam String account) throws WxErrorException {
        return ResultUtil.success(this.service.getKefuService().kfSessionList(account));

    }

    // 获取未接入会话列表
    @GetMapping("/sessionWaitGet")
    public CommonResult kfSessionGetWaitCase() throws WxErrorException {
        return ResultUtil.success(this.service.getKefuService().kfSessionGetWaitCase());

    }


    // 获取某个时间取内的客服服务用户
    @GetMapping("/chatUser")
    public CommonResult chatRecord(String startTime,
                                   @RequestParam(value = "endTime", required = false) String endTime,
                                   @RequestParam(value = "account", required = false) String account,
                                   @RequestParam(value = "msgid", required = false, defaultValue = "1") Long msgid,
                                   @RequestParam(value = "number", required = false, defaultValue = "10000") Integer number) throws WxErrorException {
        Date start = DateUtil.dateToUnixTimestamp(startTime);
        Date end = null;
        if (endTime == null) {
            end = new Date(start.getTime() + (1000 * 60 * 60 * 24));
        } else {
            end = DateUtil.dateToUnixTimestamp(endTime);
        }
        // 修改微信接口返回的数据
        WxMpKfMsgList msgList = this.service.getKefuService().kfMsgList(start, end, msgid, number);
        List<WxMpKfMsgRecord> recordList = msgList.getRecords();

        if (recordList.size() == 0) {
            return ResultUtil.success();
        }

        // 将 openid 转化成用户名称
        List<String> openidList = new ArrayList<>();
        for (WxMpKfMsgRecord record : recordList) {
            if (!openidList.contains(record.getOpenid())) {
                openidList.add(record.getOpenid());
            }
        }
        // 获取用户信息，这里需要根据 openid 获取昵称
        List<WxMpUser> userList = this.service.getUserService().userInfoList(openidList);

        List<String> users = new ArrayList<>();

        for (WxMpKfMsgRecord record : recordList) {
            String nickName = record.getOpenid();
            for (WxMpUser user : userList) {
                if (record.getOpenid().equals(user.getOpenId())) {
                    nickName = user.getNickname();
                }
            }
            if (!users.contains(nickName)) {
                users.add(nickName);
            }
        }
        return ResultUtil.success(users);

    }

    // 获取某个时间区间内聊天记录，两者不能超过24小时
    @GetMapping("/chatRecord")
    public CommonResult chatRecord(String startTime,
                                   @RequestParam(value = "endTime", required = false) String endTime,
                                   @RequestParam(value = "account", required = false) String account,
                                   @RequestParam(value = "userName", required = false) String userName,
                                   @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                   @RequestParam(value = "msgid", required = false, defaultValue = "1") Long msgid,
                                   @RequestParam(value = "number", required = false, defaultValue = "10000") Integer number) throws WxErrorException {

        Date start = DateUtil.dateToUnixTimestamp(startTime);
        Date end = null;
        if (endTime == null) {
            end = new Date(start.getTime() + (1000 * 60 * 60 * 24));
        } else {
            end = DateUtil.dateToUnixTimestamp(endTime);
        }
        // 修改微信接口返回的数据
        WxMpKfMsgList msgList = this.service.getKefuService().kfMsgList(start, end, msgid, number);
        List<WxMpKfMsgRecord> recordList = msgList.getRecords();

        if (recordList.size() == 0) {
            return ResultUtil.success();
        }

        // 将 openid 转化成用户名称
        List<String> openidList = new ArrayList<>();
        for (WxMpKfMsgRecord record : recordList) {
            if (!openidList.contains(record.getOpenid())) {
                openidList.add(record.getOpenid());
            }
        }

        // 获取用户信息，这里需要根据 openid 获取昵称
        List<WxMpUser> userList = this.service.getUserService().userInfoList(openidList);

        List<WxMpKfMsgRecord> result = new ArrayList<>();
        List<WxMpKfMsgRecord> result0 = new ArrayList<>();
        List<WxMpKfMsgRecord> result1 = new ArrayList<>();
        List<WxMpKfMsgRecord> result2 = new ArrayList<>();

        for (WxMpKfMsgRecord record : recordList) {
            // 修改微信接口返回的数据，将 openid -> nickname
            String nickName = record.getOpenid();
            for (WxMpUser user : userList) {
                if (record.getOpenid().equals(user.getOpenId())) {
                    nickName = user.getNickname();
                }
            }
            record.setOpenid(nickName);
            // 记录某个客服的聊天记录
            if (account != null && record.getWorker().equals(account)) {
                // 记录某个客服和用户的聊天记录
                result1.add(record);
                if (userName != null && record.getOpenid().equals(userName)) {
                    result2.add(record);
                }
            }
            result0.add(record);
        }

        result = result1.size() > 0 ? (result2.size() > 0 ? result2 : result1) : result0;

        // 分页处理
        PageInfo<WxMpKfMsgRecord> page = new PageInfo<>(result);
        int totalPages = (int) Math.ceil((double) page.getSize() / pageSize);
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        if (pageSize > page.getSize()) {
            currentPage = 1;
            pageSize = page.getSize();
        }

        page.setPageNum(currentPage);
        page.setPages(totalPages);// 向上取整
        int endIndex = (currentPage * pageSize) < page.getSize() ? (currentPage * pageSize) : page.getSize();

        int currentPageSize = (endIndex == pageSize) ? pageSize : (page.getSize() - (currentPage - 1) * pageSize);
        page.setPageSize(currentPageSize);

        page.setList(result.subList((currentPage - 1) * pageSize, endIndex));
        return ResultUtil.pageFormat(page);
    }
}
