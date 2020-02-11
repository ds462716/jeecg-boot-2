package org.jeecg.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 读取文件流，字符串和文件流互转
 */
public class ReadIOUtil {
    /**
     * 把文件流转成字符串
     * @param is
     * @return
     */
    public static String readIoToString(InputStream is) {
        String result = null;
        byte[] data = null;
        try {
            data = new byte[is.available()];
            is.read(data);
            BASE64Encoder encoder = new BASE64Encoder();
            result =  encoder.encode(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(data != null) {
                data = null;
            }
        }
        return result;
    }

    /**
     * 把IO字符串输出到文件
     * @param ioString
     * @param filePath
     */
    public static void readIoStringToFile(String ioString, String filePath) {
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(ioString);

            fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
