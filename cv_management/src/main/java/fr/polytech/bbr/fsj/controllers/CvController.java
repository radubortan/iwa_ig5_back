package fr.polytech.bbr.fsj.controllers;


import fr.polytech.bbr.fsj.FileUploader;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RestController
public class CvController {


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/cv-upload")
    public void uploadCV(@RequestParam("file") MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        InputStream inputStream = file.getInputStream();
        FileUploader fileUploader = new FileUploader();
        Path path = Path.of(file.getOriginalFilename());
        String type = file.getContentType();
        long size = file.getSize();

        fileUploader.uploadFile(path.toString(), type, size, inputStream);
    }
}
