package com.example.demosfile.dao;

import com.example.demosfile.pojo.Img;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Mapper
public interface imgDao {
    public List<Img> getImgList(Map map);

    public Integer saveImg(Img img);
}
