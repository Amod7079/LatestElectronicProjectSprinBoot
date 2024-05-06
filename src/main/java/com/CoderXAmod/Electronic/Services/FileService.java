package com.CoderXAmod.Electronic.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    String UploadFile(MultipartFile file, String Path) throws IOException;
       InputStream getResourse(String path, String name) throws FileNotFoundException;
}
