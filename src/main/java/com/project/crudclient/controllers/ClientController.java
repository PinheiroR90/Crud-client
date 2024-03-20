package com.project.crudclient.controllers;

import com.project.crudclient.dto.ClientDTO;

import com.project.crudclient.dto.CustomError;
import com.project.crudclient.services.ClientService;
import com.project.crudclient.services.exceptions.NotFoundClientException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<ClientDTO> getAllCli(){
        return clientService.findAllClient();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> getCliById(@PathVariable Long id){
        ClientDTO dto =  clientService.findByIdClient(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createCli(@Valid @RequestBody ClientDTO dto){
        dto = clientService.createClient(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> updateCli(@PathVariable Long id,@Valid @RequestBody ClientDTO dto){
        dto = clientService.updateClient(id,dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCli(@PathVariable Long id){
             clientService.deleteClient(id);
             return ResponseEntity.noContent().build();
    }

}
