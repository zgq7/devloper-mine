//package com.loper.mine.utils;
//
//import java.io.IOException;
//import java.security.MessageDigest;
//import java.util.*;
//import java.util.Map.Entry;
//import java.util.stream.Collectors;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Lists;
//import com.guosen.act.boot.base.req.BaseReqParam;
//import com.guosen.act.boot.cms.req.AwardReq;
//import com.guosen.act.boot.cms.req.ProductsReq;
//import com.guosen.rest.base.exception.BusinessException;
//import com.guosen.rest.base.http.util.HttpTemplateUtil;
//import com.guosen.rest.base.response.code.ResponseCode;
//import com.guosen.rest.base.util.Asserts;
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.collections4.MapUtils;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cglib.beans.BeanMap;
//
//
//public class SignUtils {
//    protected static final Logger logger = LoggerFactory.getLogger(SignUtils.class);
//
//    public SignUtils() {
//    }
//
//    public static String getTimeStamp() {//13位
//        return String.valueOf(System.currentTimeMillis());
//    }
//
//    public static String createSHA1Sign(
//            SortedMap<String, String> signParams) {
//        StringBuffer sb = new StringBuffer();
//        Set es = signParams.entrySet();
//        Iterator it = es.iterator();
//        while (it.hasNext()) {
//            Entry params = (Entry) it.next();
//            String k = (String) params.getKey();
//            String v = (String) params.getValue();
//            if (v == null) {
//                continue;
//            }
//            sb.append(k + "=" + v + "&");
//        }
//        String params = sb.substring(0, sb.lastIndexOf("&"));
//        logger.info(params);
//        return getSha1(params);
//    }
//
//    public static String createSHA1Sign(
//            Map<String, Object> signParams) {
//        StringBuffer sb = new StringBuffer();
//        Set es = signParams.entrySet();
//        Iterator it = es.iterator();
//        while (it.hasNext()) {
//            Entry params = (Entry) it.next();
//            String k = (String) params.getKey();
//            Object v = params.getValue();
//            if (v == null ) {
//                continue;
//            }
//            if(v instanceof  Collection || v instanceof Map){
//                v = JSON.toJSONString(v) ;
//            }
//            sb.append(k + "=" + v + "&");
//        }
//        String params = sb.substring(0, sb.lastIndexOf("&"));
//        logger.info(params);
//        return getSha1(params);
//    }
//
//
//    public static SortedMap<String, String> buildSHA1SignMap(
//            SortedMap<String, String> params) {
//        if (StringUtils.isBlank(params.get("timestamp"))) {
//            params.put("timestamp", getTimeStamp());
//        }
//        if (StringUtils.isBlank(params.get("nonce"))) {
//            params.put("nonce", RandomStringUtils.randomAlphanumeric(6));
//        }
//        params.put("signature", createSHA1Sign(params));
//        params.remove("secret");
//        return params;
//    }
//
//    public static boolean checkSHA1Sign(
//            SortedMap<String, String> params, String secret) {
//        if (StringUtils.isBlank(params.get("timestamp"))) {
//            params.put("timestamp", getTimeStamp());
//        }
//        if (StringUtils.isBlank(params.get("nonce"))) {
//            params.put("nonce", RandomStringUtils.randomAlphanumeric(6));
//        }
//        String signature = MapUtils.getString(params, "signature");
//        params.remove("signature");
//        params.put("secret", secret);
//        String signatureGen = createSHA1Sign(params);
//        return signatureGen.equalsIgnoreCase(signature);
//    }
//
//
//    public static Map<String, Object> buildSHA1SignMap(
//            Map<String, Object> params) {
//        if (StringUtils.isBlank(MapUtils.getString(params, "timestamp"))) {
//            params.put("timestamp", getTimeStamp());
//        }
//        if (StringUtils.isBlank(MapUtils.getString(params, "nonce"))) {
//            params.put("nonce", RandomStringUtils.randomAlphanumeric(6));
//        }
//        params.put("signature", createSHA1Sign(tranSortedMap(params)));
//        params.remove("secret");
//        return params;
//    }
//
//    public static boolean checkSHA1Sign(
//            Map<String, Object> params,
//            String secret) {
//        String signature = MapUtils.getString(params, "signature");
//        params.remove("signature");
//        params.put("secret", secret);
//        String signatureGen = createSHA1Sign(tranSortedMap(params));
//        logger.info("signatureSource:{} ,  signatureGen:{} " , signature , signatureGen);
//        return signatureGen.equalsIgnoreCase(signature);
//    }
//
//    public static boolean checkSHA1SignSorted(
//            Map<String, Object> params) {
//        String signature = MapUtils.getString(params, "signature");
//        params.remove("signature");
//        String signatureGen = createSHA1Sign(params);
//        return signatureGen.equalsIgnoreCase(signature);
//    }
//
//    public static Map<String, Object> tranSortedMap(Map<String, Object> params) {
//        return params.entrySet().stream()
//                .filter(l -> null != l.getValue())
//                .sorted(Entry.comparingByKey())
//                .collect(Collectors.toMap(
//                        Entry::getKey,
//                        Entry::getValue,
//                        (k, v) -> k, LinkedHashMap::new));
//    }
//
//    public static void checkBeanSHA1Sign(
//            Object object,
//            String secret) {
//        try {
//            Map<String, Object> params = BeanUtils.describe(object);
//            params.remove("class");
//            //logger.info(JSON.toJSONString(params));
//            boolean signFlag = checkSHA1Sign(params, secret);
//            Asserts.isTrue(signFlag, ResponseCode.SIGNATURE_PARAMTERS_FAIL_691, "签名校验失败");
//        } catch (BusinessException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            logger.error(ex.getMessage(), ex);
//            throw new BusinessException(ResponseCode.SIGNATURE_PARAMTERS_ILLEGAL_690,
//                    "签名参数存在异常");
//        }
//    }
//
//    public static void checkBeanSHA1Sign(
//            Object object) {
//        try {
//            BeanMap beanMap = BeanMap.create(object);
//            boolean signFlag = checkSHA1SignSorted(tranSortedMap(beanMap));
//            Asserts.isTrue(signFlag, ResponseCode.SIGNATURE_PARAMTERS_FAIL_691, "签名校验失败");
//        } catch (BusinessException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            logger.error(ex.getMessage(), ex);
//            throw new BusinessException(ResponseCode.SIGNATURE_PARAMTERS_ILLEGAL_690,
//                    "签名参数存在异常");
//        }
//    }
//
//
//    public static String buildBeanSHA1Sign(
//            Object object,
//            String secret) {
//        try {
//            BeanMap beanMap = BeanMap.create(object);
//            beanMap.put("secret" , secret) ;
//            Map<String , Object> params = buildSHA1SignMap(tranSortedMap(beanMap)) ;
//            String  signature = MapUtils.getString(params , "signature") ;
//            return secret ;
//        } catch (BusinessException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            logger.error(ex.getMessage(), ex);
//            throw new BusinessException(ResponseCode.SIGNATURE_PARAMTERS_ILLEGAL_690,
//                    "签名参数存在异常");
//        }
//    }
//
//
//    public static BaseReqParam buildBeanSHA1Sign(
//            BaseReqParam object,
//            String secret) {
//        try {
//            BeanMap beanMap = BeanMap.create(object);
//            if(StringUtils.isNotBlank(secret)){
//                object.setSecret(secret);
//            }
//            object.setTimestamp(getTimeStamp());
//            object.setNonce(RandomStringUtils.randomAlphanumeric(6));
//            Map<String , Object> params = buildSHA1SignMap(tranSortedMap(beanMap)) ;
//            String  signature = MapUtils.getString(params , "signature") ;
//            object.setSignature(signature);
//            object.setSecret(null);
//            return object ;
//        } catch (BusinessException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            logger.error(ex.getMessage(), ex);
//            throw new BusinessException(ResponseCode.SIGNATURE_PARAMTERS_ILLEGAL_690,
//                    "签名参数存在异常");
//        }
//    }
//
//    public static void checkBeanSign(String algorithm, Object object) {
//        if ("SHA1".equalsIgnoreCase(algorithm)) {
//            checkBeanSHA1Sign(object);
//        }
//    }
//
//    public static String getSha1(String str) {
//        if (StringUtils.isNotBlank(str)) {
//            char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
//            try {
//                MessageDigest e = MessageDigest.getInstance("SHA1");
//                e.update(str.getBytes("UTF-8"));
//                byte[] md = e.digest();
//                int j = md.length;
//                char[] buf = new char[j * 2];
//                int k = 0;
//                for (int i = 0; i < j; ++i) {
//                    byte byte0 = md[i];
//                    buf[k++] = hexDigits[byte0 >>> 4 & 15];
//                    buf[k++] = hexDigits[byte0 & 15];
//                }
//                return new String(buf);
//            } catch (Exception ex) {
//                logger.error(ex.getMessage(), ex);
//                throw new RuntimeException(ex.getMessage(), ex);
//            }
//        } else {
//            return null;
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//
//        /*System.out.println(getTimeStamp());
//        System.out.println(getTimeStamp().length());
//        String secret = "559d76fe8a6629ab";
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("opfid", "65499870709224111111");
//        map.put("nonce", "123456");
//        map.put("timestamp", "1625194549051");
//        map.put("secret", secret);
//        map.put("uid", "1000010001");
//        Map<String, Object> map2 = SHA1Util.buildSHA1SignMap(map);*/
//        //cms.api.mpfid.base = mpf_gxpt
//        ///cms.api.secret.base = 625f66b6865943dfa89b493df5ef5631
//        AwardReq req2 = new AwardReq() ;
//        req2.setPageNo(1);
//        req2.setPageSize(10);
//        req2.setOrderIds(Lists.newArrayList("1" , "2" , "9"));
//        req2.setMpfid("mpf_gxpt");
//        //req2.setSignature(buildBeanSHA1Sign(req2 , "625f66b6865943dfa89b493df5ef5631" )) ;
//        req2.setSecret(null);
//        req2.setNonce(RandomStringUtils.randomAlphanumeric(6));
//        req2.setTimestamp( getTimeStamp());
//
//        JSONObject object  = HttpTemplateUtil.postForObject("http://innerapi.guosen.com.cn/api/middleware/thirdpart/award/orderListQuery"
//        , JSON.toJSONString(buildBeanSHA1Sign(req2 , "625f66b6865943dfa89b493df5ef5631")) , JSONObject.class) ;
//        logger.info("{}" , object);
//        logger.info("-----------------------------------------------");
//
//
//        String json = "{\"mpfid\":\"mpf_gxpt\",\"nonce\":\"yvwrvj\",\"pageNo\":1,\"pageSize\":10,\"signature\":\"8186ec3a0f312b11ed8b193e15910e15bc20ea27\",\"timestamp\":\"1656317602060\"}";
//        ProductsReq req = JSON.parseObject(json, ProductsReq.class);
//        Long current = System.currentTimeMillis();
//        checkBeanSHA1Sign(req, "625f66b6865943dfa89b493df5ef5631");
//        Long current2 = System.currentTimeMillis();
//        req.setSecret("625f66b6865943dfa89b493df5ef5631");
//
//        checkBeanSHA1Sign(req);
//        Long current3 = System.currentTimeMillis();
//        logger.info(" {} , {} ", current2 - current, current3 - current2);
//
//
//    }
//
//}