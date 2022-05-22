package com.tmsvr.partnera;

import com.tmsvr.common.Result;
import com.tmsvr.common.ResultProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PartnerAResultProvider implements ResultProvider {
    @Override
    public Result getResult() {
        log.info("Partner A implementation working.");
        return new Result(1, "From partner A");
    }
}
