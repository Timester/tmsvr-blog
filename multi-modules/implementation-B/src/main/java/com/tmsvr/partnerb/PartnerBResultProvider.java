package com.tmsvr.partnerb;

import com.tmsvr.common.Result;
import com.tmsvr.common.ResultProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PartnerBResultProvider implements ResultProvider {
    @Override
    public Result getResult() {
        log.info("Partner B implementation working.");
        return new Result(2, "From partner B");
    }
}
