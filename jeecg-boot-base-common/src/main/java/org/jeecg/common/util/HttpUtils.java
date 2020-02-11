package org.jeecg.common.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * post和get请求的工具类
 */
public class HttpUtils {
    /**
     *      * 发送POST请求
     *      * 
     *      * @param url
     *      *            目的地址
     *      * @param parameters
     *      *            请求参数，Map类型。
     *      * @return 远程响应结果
     *      
     */
    public static String sendPost(String url, Map<String, String> parameters) {
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
// 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("access_token","");
            /*httpConn.setRequestProperty("Content-type","application/json");*/
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     *      * 发送GET请求
     *      * 
     *      * @param url
     *      *            目的地址
     *      * @param parameters
     *      *            请求参数，Map类型。
     *      * @return 远程响应结果
     *      
     */
    public static String sendGet(String url, Map<String, String> parameters) {
        String result = "";
        BufferedReader in = null;// 读取响应输入流
        StringBuffer sb = new StringBuffer();// 存储参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            String full_url = url + "?" + params;
            System.out.println(full_url);
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(full_url);
            // 打开URL连接(建立了一个与服务器的tcp连接,并没有实际发送http请求！)
            URLConnection urlConnection = connURL.openConnection();
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) urlConnection;
            // 设置通用请求属性(如果已存在具有该关键字的属性，则用新值改写其值。)
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 建立实际的连接(远程对象变为可用。远程对象的头字段和内容变为可访问)
            httpConn.connect();
            // 响应头部获取
            Map<String, List<String>> headers = httpConn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : headers.keySet()) {
                System.out.println(key + "\t：\t" + headers.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;

    }

    /**
     * post请求第二种方式
     * @param url
     * @return
     * @throws IOException
     */
    public static String  doPost(String url,String param) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("access_token", "0C2B1870091E1CFA58206D6DC90A6029");
        httpPost.setEntity(new StringEntity(param, Charsets.UTF_8));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode() + "\n");
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseContent);
        response.close();
        httpClient.close();
        return  responseContent;
    }

    /**
     * Rest
     */
    public static String restAction() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://cs.nongchangyun.cn/mq_service/file/interfaceUploadFile");
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        //System.out.println( MD5Util.encodeByMD5("acsm_access_mq"));
        httpPost.addHeader("access_token",  MD5Util.encodeByMD5("acsm_access_mq"));
        FileInputStream fileInputStream  = new FileInputStream("C:\\Users\\laowang\\Desktop\\IFgo.png");
        String param = ReadIOUtil.readIoToString(fileInputStream);
    /*    httpPost.setHeader("Accept", "application/json");*/
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("b",param);
        jsonObject.put("fileType",0);
        jsonObject.put("uploadFilePath","/medicine/image/20191223092503ham04.jpg");
  /*      jsonObject.put("appKey","1");
        jsonObject.put("appSecret","1");*/
        String orgin = jsonObject.toJSONString();
        System.out.println(orgin);
        httpPost.setEntity(new StringEntity(orgin,Charsets.UTF_8));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode() + "\n");
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseContent);
        response.close();
        httpClient.close();
        return responseContent;
    }

    public static void main(String[] args) throws IOException {
        String url="http://localhost:8080/jeecg-boot/plant/addPlantInfo";
        Map<String, String> map = new HashMap<>();
        map.put("jsonStr","{\"jsonStr\":\"[{\\\"batchCode\\\":\\\"\\\",\\\"operateTime\\\":\\\"2014-10-15\\\",\\\"plantSource\\\":\\\"\\\",\\\"plantSatus\\\":\\\"种源\\\",\\\"plantId\\\":\\\"C24550\\\",\\\"csPart\\\":\\\"吉林省白山市长白朝鲜族自治县马鹿沟镇\\\",\\\"source\\\":\\\"自产\\\",\\\"fzTime\\\":\\\"2014-10-15\\\",\\\"blockMedicinalId\\\":\\\"C2100519\\\",\\\"plantType\\\":\\\"有性繁殖\\\",\\\"number\\\":\\\"15.00kg\\\",\\\"inputInto\\\":\\\"播种量\\\",\\\"responsible\\\":\\\"谢平\\\",\\\"plantMethod\\\":\\\"种源\\\",\\\"hostCode\\\":\\\"C2\\\"},{\\\"batchCode\\\":\\\"\\\",\\\"operateTime\\\":\\\"2016-10-20\\\",\\\"plantSource\\\":\\\"\\\",\\\"plantSatus\\\":\\\"移栽播种\\\",\\\"plantId\\\":\\\"C24551\\\",\\\"csPart\\\":null,\\\"source\\\":null,\\\"fzTime\\\":null,\\\"blockMedicinalId\\\":\\\"C2100519\\\",\\\"plantType\\\":\\\"条播、作床\\\",\\\"number\\\":\\\"0.00株\\\",\\\"inputInto\\\":\\\"移栽/播种量\\\",\\\"responsible\\\":\\\"谢平\\\",\\\"plantMethod\\\":\\\"移栽播种\\\",\\\"hostCode\\\":\\\"C2\\\"},{\\\"batchCode\\\":\\\"\\\",\\\"operateTime\\\":\\\"2017-07-26\\\",\\\"plantSource\\\":\\\"\\\",\\\"plantSatus\\\":\\\"草害防治\\\",\\\"plantId\\\":\\\"C24552\\\",\\\"csPart\\\":null,\\\"source\\\":null,\\\"fzTime\\\":null,\\\"blockMedicinalId\\\":\\\"C2100519\\\",\\\"plantType\\\":\\\"人工\\\",\\\"number\\\":\\\"0.00kg\\\",\\\"inputInto\\\":\\\"用药量\\\",\\\"responsible\\\":\\\"谢平\\\",\\\"plantMethod\\\":\\\"草害防治\\\",\\\"hostCode\\\":\\\"C2\\\"},{\\\"batchCode\\\":\\\"\\\",\\\"operateTime\\\":\\\"2017-07-25\\\",\\\"plantSource\\\":\\\"\\\",\\\"plantSatus\\\":\\\"肥料管理\\\",\\\"plantId\\\":\\\"C24553\\\",\\\"csPart\\\":null,\\\"source\\\":null,\\\"fzTime\\\":null,\\\"blockMedicinalId\\\":\\\"C2100519\\\",\\\"plantType\\\":\\\"叶面施肥\\\",\\\"number\\\":\\\"100.00kg\\\",\\\"inputInto\\\":\\\"施用量\\\",\\\"responsible\\\":\\\"谢平\\\",\\\"plantMethod\\\":\\\"肥料管理\\\",\\\"hostCode\\\":\\\"C2\\\"},{\\\"batchCode\\\":\\\"\\\",\\\"operateTime\\\":\\\"2017-08-10\\\",\\\"plantSource\\\":\\\"\\\",\\\"plantSatus\\\":\\\"病害防治\\\",\\\"plantId\\\":\\\"C24565\\\",\\\"csPart\\\":null,\\\"source\\\":null,\\\"fzTime\\\":null,\\\"blockMedicinalId\\\":\\\"C2100519\\\",\\\"plantType\\\":\\\"化学防治\\\",\\\"number\\\":\\\"100.00kg\\\",\\\"inputInto\\\":\\\"用药量\\\",\\\"responsible\\\":null,\\\"plantMethod\\\":\\\"病害防治\\\",\\\"hostCode\\\":\\\"C2\\\"},{\\\"batchCode\\\":\\\"\\\",\\\"operateTime\\\":\\\"2018-06-23\\\",\\\"plantSource\\\":\\\"\\\",\\\"plantSatus\\\":\\\"肥料管理\\\",\\\"plantId\\\":\\\"C24566\\\",\\\"csPart\\\":null,\\\"source\\\":null,\\\"fzTime\\\":null,\\\"blockMedicinalId\\\":\\\"C2100519\\\",\\\"plantType\\\":\\\"叶面施肥\\\",\\\"number\\\":\\\"150.00kg\\\",\\\"inputInto\\\":\\\"施用量\\\",\\\"responsible\\\":\\\"谢平\\\",\\\"plantMethod\\\":\\\"肥料管理\\\",\\\"hostCode\\\":\\\"C2\\\"},{\\\"batchCode\\\":\\\"\\\",\\\"operateTime\\\":\\\"2018-07-13\\\",\\\"plantSource\\\":\\\"\\\",\\\"plantSatus\\\":\\\"草害防治\\\",\\\"plantId\\\":\\\"C24567\\\",\\\"csPart\\\":null,\\\"source\\\":null,\\\"fzTime\\\":null,\\\"blockMedicinalId\\\":\\\"C2100519\\\",\\\"plantType\\\":\\\"人工\\\",\\\"number\\\":\\\"0.00kg\\\",\\\"inputInto\\\":\\\"用药量\\\",\\\"responsible\\\":\\\"谢平\\\",\\\"plantMethod\\\":\\\"草害防治\\\",\\\"hostCode\\\":\\\"C2\\\"},{\\\"batchCode\\\":\\\"\\\",\\\"operateTime\\\":\\\"2019-07-04\\\",\\\"plantSource\\\":\\\"\\\",\\\"plantSatus\\\":\\\"草害防治\\\",\\\"plantId\\\":\\\"C24568\\\",\\\"csPart\\\":null,\\\"source\\\":null,\\\"fzTime\\\":null,\\\"blockMedicinalId\\\":\\\"C2100519\\\",\\\"plantType\\\":\\\"人工\\\",\\\"number\\\":\\\"0.00kg\\\",\\\"inputInto\\\":\\\"用药量\\\",\\\"responsible\\\":\\\"谢平\\\",\\\"plantMethod\\\":\\\"草害防治\\\",\\\"hostCode\\\":\\\"C2\\\"},{\\\"batchCode\\\":\\\"\\\",\\\"operateTime\\\":\\\"2019-06-18\\\",\\\"plantSource\\\":\\\"\\\",\\\"plantSatus\\\":\\\"病害防治\\\",\\\"plantId\\\":\\\"C24588\\\",\\\"csPart\\\":null,\\\"source\\\":null,\\\"fzTime\\\":null,\\\"blockMedicinalId\\\":\\\"C2100519\\\",\\\"plantType\\\":\\\"化学防治\\\",\\\"number\\\":\\\"100.00kg\\\",\\\"inputInto\\\":\\\"用药量\\\",\\\"responsible\\\":null,\\\"plantMethod\\\":\\\"病害防治\\\",\\\"hostCode\\\":\\\"C2\\\"},{\\\"batchCode\\\":\\\"\\\",\\\"operateTime\\\":\\\"2019-09-26\\\",\\\"plantSource\\\":\\\"\\\",\\\"plantSatus\\\":\\\"采收\\\",\\\"plantId\\\":\\\"C24589\\\",\\\"csPart\\\":null,\\\"source\\\":null,\\\"fzTime\\\":null,\\\"blockMedicinalId\\\":\\\"C2100519\\\",\\\"plantType\\\":\\\"机械\\\",\\\"number\\\":\\\"380640.00KG\\\",\\\"inputInto\\\":\\\"采收产量\\\",\\\"responsible\\\":\\\"谢平\\\",\\\"plantMethod\\\":\\\"采收\\\",\\\"hostCode\\\":\\\"C2\\\"}]\"}");
        String s = HttpUtils.sendPost(url, map);
        System.out.println(s);
    }
}
