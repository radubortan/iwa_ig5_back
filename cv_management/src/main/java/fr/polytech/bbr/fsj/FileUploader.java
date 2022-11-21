package fr.polytech.bbr.fsj;

import io.minio.*;
import io.minio.errors.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileUploader {

    private MinioClient minioClient;

    public FileUploader() {
        minioClient = MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("fb2EfCpRCZahpGZL", "Fg5EkoVTSOqCqBHNcY9TecpIWRMWrkiW")
                .build();
    }

    public boolean uploadFile(String nameFile, String contentType, long size, InputStream inputStream) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        boolean succeed = false;
        try {
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

    public InputStream getFile(String objectName){
        InputStream stream = null;
        try {
            stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket("test-vincent")
                            .object(objectName)
                            .build());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException | InvalidResponseException | ServerException | XmlParserException e) {
            e.printStackTrace();
        }

        return stream;
    }

    public StatObjectResponse getFileMetadata(String objectName) {
        StatObjectResponse stat = null;
        try {
           stat = minioClient.statObject(
                   StatObjectArgs.builder().bucket("test-vincent").object(objectName).build()
           );
        } catch (Exception e) {
           e.printStackTrace();
        }

        return stat;
    }

    public Boolean deleteFile(String objectName) {
        Boolean delete = false;
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket("test-vincent")
                            .object(objectName)
                            .build()
            );
            delete = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delete;
    }
}
