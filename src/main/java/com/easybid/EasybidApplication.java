package com.easybid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.easybid")
@EnableScheduling
public class EasybidApplication {

  public static void main(String[] args) {
    SpringApplication.run(EasybidApplication.class, args);
  }

}
