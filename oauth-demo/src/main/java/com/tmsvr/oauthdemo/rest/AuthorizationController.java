package com.tmsvr.oauthdemo.rest;

import com.tmsvr.oauthdemo.service.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/authorization")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @GetMapping("/start-auth")
    public ResponseEntity<String> getRedirectUrl() {
        return ResponseEntity.ok(authorizationService.getAuthorizationUrl());
    }

    @PostMapping("/finish-auth")
    public ResponseEntity<Void> finishAuth(@RequestBody FinishAuthRequest finishAuthRequest) {
        authorizationService.finishAuth(finishAuthRequest.code(), finishAuthRequest.state());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
