package com.project.crudclient.services;

import com.project.crudclient.dto.ClientDTO;
import com.project.crudclient.entities.Client;
import com.project.crudclient.repositories.ClientRepository;
import com.project.crudclient.services.exceptions.NotFoundClientException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<ClientDTO> findAllClient(){
        List<Client> client = clientRepository.findAll();
        return client.stream().map(ClientDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public  ClientDTO findByIdClient(Long id){
        Client client = clientRepository.findById(id).orElseThrow(() ->
                new NotFoundClientException("Cliente "+ id +" inexistente"));
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO createClient(ClientDTO dto){
        Client client = new Client();
        copyDTOForClient(dto,client);
        client = clientRepository.save(client);
        return new ClientDTO(client);
    }
    @Transactional
    public ClientDTO updateClient(Long id,ClientDTO dto){
       try {
           Client client = clientRepository.getReferenceById(id);
           copyDTOForClient(dto,client);
           client = clientRepository.save(client);
           return  new ClientDTO(client);
       }
       catch (EntityNotFoundException e){
           throw new NotFoundClientException("Cliente "+ id+ " inexistente");
       }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteClient(Long id) {
      try {
          clientRepository.deleteById(id);
      }
      catch (EntityNotFoundException  | EmptyResultDataAccessException  e){
          throw new NotFoundClientException("Cliente "+ id+ " inexistente");
      }
      catch (DataIntegrityViolationException e){
          throw  new NotFoundClientException("Falha de Integridade Referencial");
      }
    }

    private void copyDTOForClient(ClientDTO dto,Client client){
        client.setName(dto.getName());
        client.setCpf(dto.getCpf());
        client.setIncome(dto.getIncome());
        client.setBirthDate(dto.getBirthDate());
        client.setChildren(dto.getChildren());
    }
}
