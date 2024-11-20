package com.electronicBE.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public interface FileSerivce {

    public String uploadImage(MultipartFile file,String path) throws IOException;

    public InputStream getResource(String path,String name) throws FileNotFoundException;

    
}
