package com.minstone.mobile.mp.common;

import com.minstone.mobile.mp.common.constants.CommonResultEnum;
import com.minstone.mobile.mp.wechat.publics.domain.WxPublic;
import com.minstone.mobile.mp.wechat.publics.service.IWxPublicService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by huangyg on 2017/8/10.
 */
@Aspect
@Component
public class CommonAspect {


    @Autowired
    private WxMpService wxService;

    @Autowired
    private IWxPublicService publicService;

    private static Logger logger = LoggerFactory.getLogger(CommonAspect.class);

    @Pointcut("execution(public * com.minstone.mobile.mp.wechat..*.controller..*(..))")
    public void pointCut(){}


    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) throws WxErrorException, IOException {


        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("============================ WeiXin ============================");
        logger.info("URL           :{}   {}",request.getMethod(),request.getRequestURI());
        logger.info("Method        :{}",joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("Address       :{}",request.getRemoteHost(),request.getRemoteAddr(),request.getRequestURL());
        Class[] parameterTypes = ( (MethodSignature)joinPoint.getSignature() ).getMethod().getParameterTypes() ;


        switch( request.getMethod() ){
            case "GET" :{
                logger.info("Parameter     :{}",request.getQueryString() ) ;
                break ;
            }
            case "POST" :{
                Object reqDto = joinPoint.getArgs() ;
                if( reqDto != null ){

                    logger.info("Parameter     :{}",reqDto.toString()) ;
                }
                break ;
            }
            default : break ;
        }

        // 每次都拦截参数 publicCode
        String publicCode = request.getParameter("publicCode");
        if (publicCode == null){
            throw  new CommonException(CommonResultEnum.PARAMS_PUBLICCODE_MISSING);
        }
        WxPublic checkPublic = publicService.get(publicCode);
        if (checkPublic == null) {
            throw new CommonException(CommonResultEnum.PUBLIC_NOTFOUND);
        }
        // 判断是否需要切换公众号
        if (!checkPublic.getAppSecret().equals(new WxMpInMemoryConfigStorage().getSecret())) {
            WxMpInMemoryConfigStorage config = publicService.switchPublic(checkPublic);
            wxService.setWxMpConfigStorage(config);
        }

    }

//    @Around("pointCut()")
//    public void storageConfig(JoinPoint joinPoint) throws WxErrorException, IOException {
//
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() ;
//        Object returnObject = null ;
//
//        Object target = joinPoint.getTarget() ;
//        String methodName = joinPoint.getSignature().getName() ;
//        Class[] parameterTypes = ( (MethodSignature)joinPoint.getSignature() ).getMethod().getParameterTypes() ;
//        Method method = null ;
//        try {
//            method = target.getClass().getMethod( methodName , parameterTypes ) ;
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        // 判断 method 是否包含 StorageAnnotation 注解
//        if (method != null && method.isAnnotationPresent(StorageAnnotation.class)){
//            WxPublic checkPublic = publicService.get(req.getParameter("publicCode"));
//            if (checkPublic == null) {
//                throw new CommonException(CommonResultEnum.PUBLIC_NOTFOUND);
//            }
//            // 判断是否需要切换公众号
//            if (!checkPublic.getAppSerct().equals(new WxMpInMemoryConfigStorage().getSecret())) {
//                WxMpInMemoryConfigStorage config = publicService.switchPublic(checkPublic);
//                service.setWxMpConfigStorage(config);
//            }
//        }
//
//    }
}
