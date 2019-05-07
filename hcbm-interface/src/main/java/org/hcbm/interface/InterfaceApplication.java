package org.hcbm.interface;

import org.hzero.autoconfigure.interface.EnableHZeroInterface;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableHZeroInterface
@EnableEurekaClient
@SpringBootApplication
public class InterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterfaceApplication.class, args);
    }
}


