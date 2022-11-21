package fr.polytech.bbr.fsj.controllers;


import fr.polytech.bbr.fsj.FileUploader;

import io.minio.StatObjectResponse;
import org.checkerframework.checker.units.qual.C;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@RestController
public class CvController {


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/cv-upload")
    public ResponseEntity<String> uploadCV(@RequestParam("file") MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        InputStream inputStream = file.getInputStream();
        FileUploader fileUploader = new FileUploader();
        Path path = Path.of(file.getOriginalFilename());
        String type = file.getContentType();
        long size = file.getSize();

        boolean status = fileUploader.uploadFile(path.toString(), type, size, inputStream);
        if(status == true) {
            return ResponseEntity.ok().body("File successfully uploaded");
        } else {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/cv-view")
    public ResponseEntity<InputStreamResource> viewCV(@RequestParam("fileName") String fileName) {
        FileUploader fileUploader = new FileUploader();
        InputStream stream = fileUploader.getFile(fileName);
        StatObjectResponse stat = fileUploader.getFileMetadata(fileName);
        InputStreamResource inputStreamResource = new InputStreamResource(stream);
        if(stream != null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(stat.size())
                    .header("Content-disposition", "attachment; filename=" + fileName)
                    .body(inputStreamResource);
        } else {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/cv-delete")
    public ResponseEntity<String> deleteCV(@RequestParam("fileName") String fileName) {
        FileUploader fileUploader = new FileUploader();
        Boolean result = fileUploader.deleteFile(fileName);
        if (result) {
            return ResponseEntity.ok().body("File successfully deleted");
        } else {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
    }

}
