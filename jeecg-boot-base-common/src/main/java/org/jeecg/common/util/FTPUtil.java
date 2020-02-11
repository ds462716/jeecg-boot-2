package org.jeecg.common.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

public class FTPUtil {
    public static final String HOST_NAME="180.168.130.217";
    public static final int PORT=9011;
    public static final String USERNAME="admin";
    public static final String PASSWORD="adminadmin";
    public static final String PREFIX_URL="/app/files/";
    /**
     * 上传文件
     * 
     * @param hostname
     *            FTP服务器地址
     * @param port
     *            FTP服务器端口号
     * @param username
     *            FTP登录帐号
     * @param password
     *            FTP登录密码
     * @param pathname
     *            FTP服务器保存目录
     * @param fileName
     *            上传到FTP服务器后的文件名称
     * @param inputStream
     *            输入文件流
     * @return
     */
    public synchronized static boolean uploadFile(String hostname, int port, String username, String password, String pathname,
                                                  String fileName, InputStream inputStream)  {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.enterLocalPassiveMode();
        try {
// 连接FTP服务器
            if(!ftpClient.isConnected()){
                ftpClient.connect(hostname, port);
            }
// 登录FTP服务器
            System.out.println("连接成功！");
            boolean login = ftpClient.login(username, password);
// 是否成功登录FTP服务器
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return flag;
            }
            ftpClient.enterLocalPassiveMode();
            // ftpClient.setActivePortRange(40100,40200);主动模式端口范围
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            /*ftpClient.makeDirectory(pathname);*/
            ftpClient.changeWorkingDirectory(pathname);
            boolean result = ftpClient.storeFile(fileName, inputStream);//是否成功
            if (!result){
                return flag;
            }
            inputStream.close();
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**

     * Description: 从FTP服务器下载文件

     * @param url FTP服务器hostname

     * @param port FTP服务器端口

     * @param username FTP登录账号

     * @param password FTP登录密码

     * @param remotePath FTP服务器上的相对路径

     * @param fileName 要下载的文件名

     * @param localPath 下载后保存到本地的路径

     * @return

     */

    public synchronized static boolean downFile(String url,int port,String username, String password, String remotePath,String fileName,String localPath) {

        boolean success =false;

        FTPClient ftpClient =new FTPClient();

        try{
            int reply;
            ftpClient.connect(url, port);
//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpClient.login(username, password);//登录
            reply = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
             ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
            FTPFile[] fs = ftpClient.listFiles();
            for(FTPFile ff:fs){
                if(ff.getName().equals(fileName)){
                    File localFile =new File(localPath+"/"+ff.getName());
                    OutputStream is =new FileOutputStream(localFile);
                    ftpClient.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
            ftpClient.logout();
            success =true;
        }catch(IOException e) {
            e.printStackTrace();
        }finally{
            if(ftpClient.isConnected()) {
                try{
                    ftpClient.disconnect();
                }catch(IOException ioe) {
                }
            }
        }
        return success;
    }

    /**
     * 测试
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test.jpg.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


/**
 * 下载
 */
     /*   FTPUtil.downFile("180.168.130.217",
                9011,"ypBusiness",
                "ypBusiness",
                "/app/files/yp/image",
                "xxx.jpg","C:\\Users\\laowang\\Desktop\\");*/
        /**
         *
         */
        boolean sphadmin = FTPUtil.uploadFile("180.168.130.217",
                9011, "admin",
                "adminadmin",
                "/app/files/yp/image/202002",
                "laowanglaowangTest.jpg", fileInputStream);
        System.out.println(sphadmin);
    /*  FTPUtil.uploadFile("49.235.29.30",
                9011,"laowang",
                "laowang",
                "/data/ftp/pub",
                "537989dsadsadasd8989535.text",fileInputStream);*/
    }

}
