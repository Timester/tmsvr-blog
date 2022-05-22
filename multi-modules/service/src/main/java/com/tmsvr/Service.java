package com.tmsvr;

import com.tmsvr.common.ResultProvider;
import com.tmsvr.partnera.PartnerAResultProvider;
import com.tmsvr.partnerb.PartnerBResultProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Service {

    public static void main(String[] args) {
        Service service = new Service();
        service.start();
    }

    private void start() {
        ResultProvider partnerA = new PartnerAResultProvider();
        ResultProvider partnerB = new PartnerBResultProvider();

        log.info(partnerA.getResult().code());
        log.info(partnerB.getResult().code());
    }
}
