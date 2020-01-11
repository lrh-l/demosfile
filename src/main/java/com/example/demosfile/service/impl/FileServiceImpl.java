package com.example.demosfile.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demosfile.dao.ImgSaveDao;
import com.example.demosfile.dao.imgDao;
import com.example.demosfile.pojo.Img;
import com.example.demosfile.service.FileService;
import com.example.demosfile.utils.FtpFileUtis;
import com.example.demosfile.utils.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private final static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private imgDao imgDao;

    @Override
    public ServerResponse upload(Part file) {
        Map<String, String> resultMap = new HashMap();
        Img img = new Img();
        Date date = new Date();
        String oldName = file.getSubmittedFileName();
        String newName = UUID.randomUUID().toString();
        //给文件重命名
        newName = newName + oldName.substring(oldName.lastIndexOf("."));
        img.setImgName(newName);
        img.setImgNativeName(file.getSubmittedFileName());
        img.setShengXiao(1);
        img.setCreateDate(date);
        img.setUpdateDate(date);
        try {
            //利用FtpUtil中到方法进行上传文件
            boolean result = FtpFileUtis.uploadFile("/home/liurenhua", newName, file.getInputStream());
            if (result) {
                //如果上传成功，则返回前端新的文件名和路径
                resultMap.put("uri", newName);
                //根据nginx配置的ftp文件夹和域名的对应关系组装文件在ftp服务器中的位置
                resultMap.put("url", "http://" + FtpFileUtis.FTP_IP+":8080"+"/img/" + newName);
                logger.info("图片路径为"+ JSONObject.toJSON(resultMap.get("url")).toString());
                logger.info("响应为"+ JSONObject.toJSON(ServerResponse.createBySucess("上传成功！",resultMap)).toString());
                 ///JSONObject.parseObject(JSONObject.toJSONString(resultMap), Img.class)
                 img.setImgUrl(resultMap.get("url"));
                 imgDao.saveImg(img);
                return ServerResponse.createBySucess("上传成功！",resultMap);
            } else {
                return ServerResponse.createByErrorMessage("上传失败");
            }
        } catch (IOException e) {
            e.printStackTrace();

        }catch (Exception e){
            logger.info(e.getMessage()+"上传失败");
        }
        return ServerResponse.createByErrorMessage("上传失败");
    }
}
