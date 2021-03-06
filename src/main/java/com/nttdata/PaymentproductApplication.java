package com.nttdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**Se crea la clase principal PaymentproductApplication.*/
@EnableEurekaClient
@SpringBootApplication
public class PaymentproductApplication {

  public static void main(String[] args) {
    SpringApplication.run(PaymentproductApplication.class, args);
  }

}
