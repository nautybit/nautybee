package com.nautybit.nautybee.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.Stack;

/**
 *
 * Created by Minutch on 15/6/28.
 */
public class GenerationUtils {

    private static final Logger logger = LoggerFactory.getLogger(GenerationUtils.class);

    /**
     * 随机数生成工具
     * @return
     */
    public static String generateRandomCode(int length){

        if (length <= 0) {
            throw new RuntimeException("error: random code length must large than 0!");
        }
        Random random = new Random();
        StringBuffer verifyCodeBuffer = new StringBuffer();
        for (int i=0;i<length;i++) {
            verifyCodeBuffer.append(random.nextInt(10));
        }
        return verifyCodeBuffer.toString();
    }
    /**
     * 短信验证码生成工具(生成4位数的短信验证码)
     * @return
     */
    public static String generateMsgVerifyCode(){

        return generateRandomCode(4);
    }

    /**
     * 生成固定长度的数字
     * @param length
     * @return
     */
    public static String generateFixedLengthDigital(long digital,int length){

        if (length < 1) {
            logger.error("generateFixedLengthDigital:digital length could not less than 1!");
            throw new RuntimeException("digital length could not less than 1!");
        }

        String digitalStr = String.valueOf(digital);
        if (digitalStr.length() >= length) {
            return digitalStr;
        }

        int appendLength = length - digitalStr.length();
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<appendLength;i++) {
            sb.append("0");
        }
        sb.append(digital);

        return sb.toString();
    }





    private static final String digths = "23456789ABCEFGHJKMNPQRSTUVWXYZ";
    /**
     * 将数转为任意31进制
     * @param num
     * @return
     */
    public static String transHex(long num){

        StringBuffer str = new StringBuffer("");

        Stack<Character> s = new Stack<Character>();
        while(num != 0){
            s.push(digths.charAt((int)num%31));
            num/=31;
        }
        while(!s.isEmpty()){
            str.append(s.pop());
        }
        return str.toString();
    }





    /**
     * 31转成10进制
     * @param num
     * @return
     */
    public static Long toTenHex(String num){

        if (StringUtils.isBlank(num)) {
            return null;
        }
        //把D全部替换成空字符串
        num = num.replace("D","").trim();
        if (StringUtils.isBlank(num)) {
            return null;
        }

        int n = num.trim().length();
        long result = 0;
        for (int i=0;i<n;i++) {
            String letter = String.valueOf(num.charAt(i));
            int highDig = n-i-1;
            int pos = digths.indexOf(letter);
            result += pos*Math.pow(31,highDig);
        }
        return result;
    }



    public static void main(String[] args){
//        String a = getStoreInvacationCode(1000001L);
//       System.out.println(a);
//       System.out.println(toTenHex(a));
    }
}
