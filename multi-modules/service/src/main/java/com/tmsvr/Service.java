package com.tmsvr;

import com.tmsvr.common.ResultProvider;
import com.tmsvr.partnera.PartnerAResultProvider;
import com.tmsvr.partnerb.PartnerBResultProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Service implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Service.class, args);
    }

    @Override
    public void run(String... args) {
        ResultProvider partnerA = new PartnerAResultProvider();
        ResultProvider partnerB = new PartnerBResultProvider();

        log.info(partnerA.getResult().code());
        log.info(partnerB.getResult().code());
    }
}
