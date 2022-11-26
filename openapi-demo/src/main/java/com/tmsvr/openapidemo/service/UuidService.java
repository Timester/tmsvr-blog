package com.tmsvr.openapidemo.service;

import com.tmsvr.openapidemo.uuid.api.UuidApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UuidService {

    private final UuidApi uuidApi;

    public List<String> getUuids() {
        try {
            return uuidApi.generateUuid(4);
        } catch (RestClientException restClientException) {
            // TODO handle exceptions here
            log.warn(restClientException.getMessage(), restClientException);
            return List.of("not-so-unique");
        }
    }
}
