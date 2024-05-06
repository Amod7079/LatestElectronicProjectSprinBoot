package com.CoderXAmod.Electronic.Services.Impl;

import com.CoderXAmod.Electronic.Exception.BadApiRequest;
import com.CoderXAmod.Electronic.Services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    Logger logger = LoggerFactory.getLogger(FileService.class);

    @Override
    public String UploadFile(MultipartFile file, String Path) throws IOException {

        // return null;

        String origibnalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString();
        String extension = origibnalFileName.substring(origibnalFileName.lastIndexOf("."));
        String FileNameWithExtension = fileName + extension;
        String FullPathWithFileName = Path + FileNameWithExtension;

        logger.info("Full Path Of Image is Look Like this  :{}" + FullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg") || extension.equalsIgnoreCase(".pdf")) {
            //file ko save krni hai hme

            File folder = new File(Path);
            if (!folder.exists()) {
                // create the folder
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(), Paths.get(FullPathWithFileName));
            return FileNameWithExtension;
        } else {
            throw new BadApiRequest("File With This " + extension + "Not Allowed Here 🚫");
        }

    }


    @Override
    public InputStream getResourse(String path, String name) throws FileNotFoundException {

        String fullPath = path + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
