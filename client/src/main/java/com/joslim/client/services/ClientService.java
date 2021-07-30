package com.joslim.client.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joslim.client.dto.ClientDTO;
import com.joslim.client.entities.Client;
import com.joslim.client.repositories.ClientRepository;
import com.joslim.client.services.exceptions.DatabaseException;
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

	@Transactional
	public ClientDTO insertClient(ClientDTO dto) {
		Client entity = new Client();
		entity.setName(dto.getName());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
		
		entity = repository.save(entity);
		
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO updateClient(Long id, ClientDTO dto) {
		try {
			Client entity = repository.getOne(id);
		
			entity.setName(dto.getName());
			entity.setCpf(dto.getCpf());
			entity.setIncome(dto.getIncome());
			entity.setBirthDate(dto.getBirthDate());
			entity.setChildren(dto.getChildren());
		
		entity = repository.save(entity);
		return new ClientDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}


	public void deleteClient(Long id) {
	  try {
		  repository.deleteById(id);
	  }
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException ("Integrity violation");
		}
	}

}
