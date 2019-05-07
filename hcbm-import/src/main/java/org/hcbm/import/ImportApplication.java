package org.hcbm.import;

import org.hzero.autoconfigure.import.EnableHZeroImport;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableHZeroImport
@EnableEurekaClient
@SpringBootApplication
public class ImportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImportApplication.class, args);
    }
}


