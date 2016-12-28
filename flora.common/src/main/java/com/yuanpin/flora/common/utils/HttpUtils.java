package com.yuanpin.flora.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuanpin.flora.common.result.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Minutch on 15/6/26.
 */
public class HttpUtils {
    static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url         发送请求的URL
     * @param param       请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param connTimeOut 连接超时时间
     * @param readTimeOut 获取结果超时时间
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, int connTimeOut, int readTimeOut) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            if (param != null && !"".equals(param)) {
                urlNameString = urlNameString + "?" + param;
            }
            logger.info(url+"?"+param);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(connTimeOut);
            connection.setReadTimeout(readTimeOut);
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            // Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            // for (String key : map.keySet()) {
            // System.out.println(key + "--->" + map.get(key));
            // }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送GET请求出现异常", e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            }
        }
        return result;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        return sendGet(url, param, 1000, 3000);
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        return sendPost(url, param, 1000, 3000);
    }

    public static String sendPost(String url, String param, int connTimeOut, int readTimeOut){
        return sendPost(url, param, connTimeOut, readTimeOut, "application/json");
    }
    
    public static Result<String> sendPostResult(String url, String param) {
        return sendPostResult(url, param, 1000, 3000);
    }
    
    
    public static Result<String> sendPostResult(String url, String param, int connTimeOut, int readTimeOut){
        return sendPostResult(url, param, connTimeOut, readTimeOut, "application/json");
    }
    
    
    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url         发送请求的 URL
     * @param param       请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param connTimeOut 连接超时时间
     * @param readTimeOut 获取结果超时时间
     * @return 所代表远程资源的响应结果
     */
    public static Result<String> sendPostResult(String url, String param, int connTimeOut, int readTimeOut, String contentType) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            logger.info(url+"?"+param);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "application/json");
            if(contentType!=null) {
                conn.setRequestProperty("content-type", contentType);
            }
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setConnectTimeout(connTimeOut);
            conn.setReadTimeout(readTimeOut);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常", e);
            return Result.wrapErrorResult(null, e.getMessage());
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                return Result.wrapErrorResult(null, ex.getMessage());
            }
        }
        return Result.wrapSuccessfulResult(result);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url         发送请求的 URL
     * @param param       请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param connTimeOut 连接超时时间
     * @param readTimeOut 获取结果超时时间
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param, int connTimeOut, int readTimeOut, String contentType) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            logger.info(url+"   param:"+param);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "application/json");
            if(contentType!=null) {
                conn.setRequestProperty("content-type", contentType);
            }
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setConnectTimeout(connTimeOut);
            conn.setReadTimeout(readTimeOut);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
        return result;
    }
    public static String doDelete(String url,String param)  {

        BufferedReader in = null;
        String result = "";

        try {
            URL localURL = new URL(url + "?"+param);
            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection)connection;

            httpURLConnection.setDoOutput(true);
            //httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpURLConnection.setRequestProperty("accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(param.length()));
            httpURLConnection.setConnectTimeout(1000);
            httpURLConnection.setReadTimeout(3000);

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        }catch (Exception e) {
            logger.error("发送 DELETE 请求出现异常", e);
        } finally {
            try {

                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
        return result.toString();
    }


}
