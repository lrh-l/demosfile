package com.example.demosfile.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demosfile.service.FIleDownloadService;
import com.example.demosfile.service.FileDownloacServiceImpl;
import com.example.demosfile.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileDownloadController {
    @Autowired
    private FIleDownloadService fileDownloacService;
    @RequestMapping(value = "/file-download")
    public ModelAndView fileDownload(){
        System.out.println("-----");
        ServerResponse imgList = fileDownloacService.getImgList(null);
        ModelAndView modelAndView = new ModelAndView("fileDownload");
        modelAndView.addObject("data",imgList.getData());
        return modelAndView;
    }
}
