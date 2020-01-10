package com.example.demosfile.service;

import com.example.demosfile.utils.ServerResponse;

import javax.servlet.http.Part;

public interface FileService {
    public ServerResponse upload(Part file);
}
