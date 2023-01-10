package com.prueba.crud.app;

import com.prueba.crud.app.models.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Prueba1CrudApplication implements CommandLineRunner {

    @Autowired
    IUploadFileService uploadFileService;

    public static void main(String[] args) {
        SpringApplication.run(Prueba1CrudApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        uploadFileService.deleteALL();
        uploadFileService.init();
    }
}
