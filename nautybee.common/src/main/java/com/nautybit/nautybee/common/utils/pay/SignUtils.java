package com.nautybit.nautybee.common.utils.pay;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 签名工具
 *
 * Created by Minutch on 15/7/21.
 */
public class SignUtils {
    /**
     * 签名参数KEY
     */
    private static final String SIGN = "sign";
    /**
     * 签名类型参数KEY
     */
    private static final String SIGN_TYPE = "sign_type";
    /**
     * 除去参数中的空值和签名参数
     * @param paramMap
     * @return
     */
    public static Map<String,String> paramFilter(Map<String,String> paramMap) {
        Map<String, String> result = new HashMap<String, String>();
        if (paramMap == null || paramMap.size() == 0) {
            return result;
        }

        for (String key : paramMap.keySet()) {
            String value = paramMap.get(key);
            if (StringUtils.isBlank(value) || SIGN.equalsIgnoreCase(key)
                    || SIGN_TYPE.equalsIgnoreCase(key)) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
    
    public static String createLinkStringWithEscape(Map<String, String> params){
        return createLinkStringWithEscape(params,false,false);
    }
    
    

    /**
     * 把数组所有元素排序，并按照“参数="参数值"”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkStringWithEscape(Map<String, String> params,boolean isIncludeQuotes,boolean isEncode) {

        Map<String,String> signMap = paramFilter(params);
        List<String> keys = new ArrayList<String>(signMap.keySet());
        Collections.sort(keys);
        StringBuffer preStr = new StringBuffer();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if(isEncode){
                value = urlEncode(value);
            }
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                if(isIncludeQuotes){
                    preStr.append(key + "=\"" + value + "\"");
                }else{
                    preStr.append(key + "=" + value + "");
                }
            } else {
                if(isIncludeQuotes){
                    preStr.append(key + "=\"" + value + "\"&");
                }else{
                    preStr.append(key + "=" + value + "&");
                }
            }
        }

        return preStr.toString();
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        Map<String,String> signMap = paramFilter(params);
        List<String> keys = new ArrayList<String>(signMap.keySet());
        Collections.sort(keys);
        StringBuffer preStr = new StringBuffer();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                preStr.append(key + "=" + value + "");
            } else {
                preStr.append(key + "=" + value + "&");
            }
        }

        return preStr.toString();
    }

    /**
     * 生成文件摘要
     * @param content 内容
     * @param file_digest_type 摘要算法
     * @return 文件摘要结果
     */
    public static String getAbstract(String content, String file_digest_type){
        if(file_digest_type.equals("MD5")){
            return DigestUtils.md5Hex(content);
        }
        else if(file_digest_type.equals("SHA")) {
            return DigestUtils.shaHex(content);
        }
        else {
            return "";
        }
    }
    
    private static String urlEncode(String str){
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
