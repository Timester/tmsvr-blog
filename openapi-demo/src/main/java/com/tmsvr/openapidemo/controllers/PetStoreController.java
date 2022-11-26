package com.tmsvr.openapidemo.controllers;

import com.tmsvr.openapidemo.petstore.model.Pet;
import com.tmsvr.openapidemo.petstore.api.PetApi;
import com.tmsvr.openapidemo.service.UuidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PetStoreController implements PetApi {

    private final UuidService uuidService;

    @Override
    public ResponseEntity<Pet> getPetById(Long petId) {
        log.info("FOUND");

        List<String> uuids = uuidService.getUuids();
        for (String uuid : uuids) {
            log.info("UUID: {}", uuid);
        }

        var foundPet = new Pet();
        foundPet.setId(1L);
        foundPet.setName("Beethoven - " + uuids.get(0));

        return ResponseEntity.ok(foundPet);
    }

    @Override
    public ResponseEntity<Pet> addPet(Pet pet) {
        log.info("CREATED");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletePet(Long petId, String apiKey) {
        log.info("DELETED");
        return ResponseEntity.ok().build();
    }
}
