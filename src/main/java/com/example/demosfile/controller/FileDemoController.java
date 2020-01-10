package com.example.demosfile.controller;

import com.example.demosfile.service.FileService;
import com.example.demosfile.utils.ServerResponse;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileDemoController {
    private static final Logger log= LoggerFactory.getLogger(FileDemoController.class);

    @Autowired
    private FileService fileService;

    @RequestMapping("/file")
    public String file(){
        return "file";
    }

    @RequestMapping("/file-upload")
    @ResponseBody
    public ServerResponse fileUpload(@RequestParam(value = "fileName",required = true) Part multipartFile, HttpServletRequest request)throws Exception{
      /*  System.out.println("_________________________________");
        String hostname = "192.168.159.88";
        int port = 21;
        String username = "liurenhua";
        String password = "123456";
        String workingPath = "/home/liurenhua/";
        String saveName = "tqqss.jpeg";
        FileTestController.upload( hostname, port, username, password, workingPath, multipartFile.getInputStream(), saveName);*/
        ServerResponse upload = fileService.upload(multipartFile);

        return upload;
    }




    /**
     * 上传
     *
     * @param hostname ip或域名地址
     * @param port  端口
     * @param username 用户名
     * @param password 密码
     * @param workingPath 服务器的工作目
     * @param inputStream 要上传文件的输入流
     * @param saveName    设置上传之后的文件名
     * @return
     */
    public static boolean upload(String hostname, int port, String username, String password, String workingPath, InputStream inputStream, String saveName) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        //1 测试连接
        if (connect(ftpClient, hostname, port, username, password)) {
            try {
                //2 检查工作目录是否存在
                if (ftpClient.changeWorkingDirectory(workingPath)) {
                    // 3 检查是否上传成功
                    if (storeFile(ftpClient, saveName, inputStream)) {
                        flag = true;
                        disconnect(ftpClient);
                    }
                }
            } catch (IOException e) {
                log.error("工作目录不存在");
                e.printStackTrace();
                disconnect(ftpClient);
            }
        }
        return flag;
    }
    /**
     * 断开连接
     *
     * @param ftpClient
     * @throws Exception
     */
    public static void disconnect(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
                log.error("已关闭连接");
            } catch (IOException e) {
                log.error("没有关闭连接");
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试是否能连接
     *
     * @param ftpClient
     * @param hostname  ip或域名地址
     * @param port      端口
     * @param username  用户名
     * @param password  密码
     * @return 返回真则能连接
     */
    public static boolean connect(FTPClient ftpClient, String hostname, int port, String username, String password) {
        boolean flag = false;
        try {
            //ftp初始化的一些参数
            ftpClient.connect(hostname, port);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("UTF-8");
            if (ftpClient.login(username, password)) {
                System.out.println("连接ftp成功");
                log.info("连接ftp成功");
                flag = true;
            } else {
                log.error("连接ftp失败，可能用户名或密码错误");
                try {
                    disconnect(ftpClient);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            log.error("连接失败，可能ip或端口错误");
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 上传文件
     *
     * @param ftpClient
     * @param saveName        全路径。如/home/public/a.txt
     * @param fileInputStream 要上传的文件流
     * @return
     */
    public static boolean storeFile(FTPClient ftpClient, String saveName, InputStream fileInputStream) {
        boolean flag = false;
        try {
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            if (ftpClient.storeFile(saveName, fileInputStream)) {
                flag = true;
                log.error("上传成功");
                disconnect(ftpClient);
            }
        } catch (IOException e) {
            log.error("上传失败");
            disconnect(ftpClient);
            e.printStackTrace();
        }
        return flag;
    }

    public static void main(String[] args) throws Exception{
        String hostname = "192.168.159.88";
        int port = 21;
        String username = "liurenhua";
        String password = "123456";
        String workingPath = "/home/liurenhua/";
        String str = "E:\\img\\20170813194034_UaKdW.jpeg";
        InputStream fileInputStream = new FileInputStream(new File(str));
      /*  //byte [] bytes = new byte[fileInputStream.available()];
        OutputStream os = new FileOutputStream(new File("E:\\img\\textt.png"));
        int n = 0;// 每次读取的字节长度
        byte[] bb = new byte[fileInputStream.available()];// 存储每次读取的内容
        while ((n = fileInputStream.read(bb)) != -1) {
            os.write(bb, 0, n);// 将读取的内容，写入到输出流当中
        }
        //执行完以上后，磁盘下的该文件才完整，大小是实际大小
        os.close();// 关闭输入输出流
        fileInputStream.close();*/
        String saveName = "tqq.jpeg";
        System.out.println(FileDemoController.upload( hostname, port, username, password, workingPath, fileInputStream, saveName));

    }

}
