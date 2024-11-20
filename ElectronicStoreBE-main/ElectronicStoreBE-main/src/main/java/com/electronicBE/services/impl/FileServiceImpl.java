package com.electronicBE.services.impl;

import com.electronicBE.exceptions.BadApiException;
import com.electronicBE.services.FileSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileSerivce {


    private Logger logger=LoggerFactory.getLogger(FileServiceImpl.class);


    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {


        //abc.png
        String originalFilename = file.getOriginalFilename();
        logger.info("Filename : {}", originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullPathWithFileName = path  + fileNameWithExtension;

        logger.info("full image path: {} ", fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            //file save
            logger.info("file extension is {} ", extension);
            File folder = new File(path);
            if (!folder.exists()) {
                //create the folder
                folder.mkdirs();

            }

            //upload
        Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;

        } else {
            throw new BadApiException("File with this " + extension + " not allowed !!");
        }



    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String filePath=path+name;

        InputStream inputStream= new FileInputStream(filePath);

        return inputStream;
    }
}
