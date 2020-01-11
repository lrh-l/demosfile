package com.example.demosfile.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demosfile.dao.imgDao;
import com.example.demosfile.utils.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FileDownloacServiceImpl implements FIleDownloadService{
    private static  final Logger logger = LoggerFactory.getLogger(FileDownloacServiceImpl.class);
    @Autowired()
   private imgDao imgDao;
    @Override
   public ServerResponse getImgList(Map map){
       logger.info(JSONObject.toJSON(imgDao.getImgList(null)).toString());
        return ServerResponse.createBySucess(imgDao.getImgList(null));
    }
}
