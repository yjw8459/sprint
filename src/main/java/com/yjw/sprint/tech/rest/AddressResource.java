package com.yjw.sprint.tech.rest;

import com.yjw.sprint.tech.dto.AddressDTO;
import com.yjw.sprint.tech.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressResource {

    private AddressService addressService;

    public AddressResource(AddressService addressService) { this.addressService = addressService; }

    @GetMapping("/address")
    public ResponseEntity<List<AddressDTO>> getAll(){
        return ResponseEntity.ok(addressService.findAll());
    }
}
