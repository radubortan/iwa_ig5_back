package fr.polytech.bbr.fsj;

import io.minio.*;
import io.minio.errors.MinioException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileUploader {

    public FileUploader() {
    }

    public boolean uploadFile(String nameFile, String contentType, long size, InputStream inputStream) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        boolean succeed = false;
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://127.0.0.1:9000")
                    .credentials("eYRsoaxwyKhPI6Ju", "bbz2QBog0uqo45O31taWhfnmDY3EzkSH")
                    .build();

            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("test-vincent").build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("test-vincent").build());
            } else {
                System.out.println("Bucket test-vincent already exists.");
            }

            minioClient.putObject(
                    PutObjectArgs.builder().bucket("test-vincent").object(nameFile).stream(
                                    inputStream, size, -1)
                            .contentType(contentType)
                            .build());


            System.out.println("Upload OK !");
            succeed = true;
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
            succeed = false;
        }
        return succeed;
    }
}
