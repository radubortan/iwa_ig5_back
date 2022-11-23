package fr.polytech.bbr.fsj.controllers;


import fr.polytech.bbr.fsj.FileUploader;

import fr.polytech.bbr.fsj.model.Candidate;
import fr.polytech.bbr.fsj.repository.CandidateRepo;
import io.minio.StatObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;


@RestController
public class CvController {

    @Autowired
    CandidateRepo candidateRepo;


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/cv-upload/{id}")
    public ResponseEntity<Candidate> uploadCV(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        Optional<Candidate> candidateData = candidateRepo.findById(id);
        InputStream inputStream = file.getInputStream();
        FileUploader fileUploader = new FileUploader();
        Path path = Path.of(file.getOriginalFilename());
        String type = file.getContentType();
        long size = file.getSize();

        boolean status = fileUploader.uploadFile(path.toString(), type, size, inputStream);
        if(status == true) {
            if(candidateData.isPresent()) {
                Candidate _candidate = candidateData.get();
                _candidate.setLinkCv(file.getOriginalFilename());
                return new ResponseEntity<>(candidateRepo.save(_candidate), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
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
    @GetMapping("/cv-delete/{id}")
    public ResponseEntity<String> deleteCV(@PathVariable("id") String id, @RequestParam("fileName") String fileName) {
        Optional<Candidate> candidateData = candidateRepo.findById(id);
        FileUploader fileUploader = new FileUploader();
        Boolean result = fileUploader.deleteFile(fileName);
        if (result) {
            if(candidateData.isPresent()) {
                Candidate _candidate = candidateData.get();
                _candidate.setLinkCv(null);
                candidateRepo.save(_candidate);
            }
            return ResponseEntity.ok().body("File successfully deleted");
        } else {
            return ResponseEntity.status(BAD_REQUEST).body(null);
        }
    }

}
