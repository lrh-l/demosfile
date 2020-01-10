package com.example.demosfile.utils;

import org.apache.commons.net.ftp.FTPClient;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FtpFileUtis {
    private final static Logger logger = LoggerFactory.getLogger(FtpFileUtis.class);
     // 登陆IP
    public static final String FTP_IP="192.168.159.88";
    public static boolean uploadFile(String remotePath, String fileName, InputStream inputStream) {
        boolean result = false;
        FTPClient ftpClient = new FTPClient();
        try {
            //连接登陆
            ftpClient.connect(FTP_IP);
            boolean isSeccess = ftpClient.login("liurenhua", "123456");
            logger.info("ftp服务器:{},登陆--{}", FTP_IP, isSeccess);
             // ftp 加载方式
            ftpClient.enterLocalPassiveMode();
            //如果登陆成功...
            if (isSeccess) {
                //切换到指定的ftp服务器文件夹目录
                boolean isChangeSuccess = ftpClient.changeWorkingDirectory(remotePath);
                //如果切换失败则创建改目录再切换
                if (!isChangeSuccess) {
                    ftpClient.makeDirectory("/home/liurenhua/");
                    ftpClient.changeWorkingDirectory(remotePath);
                }
                //设置缓冲大小，编码格式，文件类型
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                // 以二进制形式传输
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                //开始上传
                logger.info("开始上传文件：{}", fileName);
                result = ftpClient.storeFile(fileName, inputStream);
                logger.info("文件：{},上传:{}", fileName, result);
                return result;
            }
        } catch (IOException e) {
            logger.error("文件上传失败", e);
        } finally {
            try {
                //关闭流
                inputStream.close();
                ftpClient.logout();
            } catch (IOException e) {
                logger.error("输入流关闭失败", e);
            }
        }
        return result;
    }

}
