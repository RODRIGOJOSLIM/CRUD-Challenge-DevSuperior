package com.joslim.client.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joslim.client.dto.ClientDTO;
import com.joslim.client.entities.Client;
import com.joslim.client.repositories.ClientRepository;
import com.joslim.client.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	ClientRepository repository;

	@Transactional(readOnly = true)
	public List<ClientDTO> findAll() {
	List<Client> list = repository.findAll();
	List<ClientDTO> listDTO = list.stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
	
	return listDTO;
		
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		 
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found !"));
		return new ClientDTO(entity);
	}

}
