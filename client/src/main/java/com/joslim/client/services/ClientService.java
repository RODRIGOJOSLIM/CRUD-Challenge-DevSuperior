package com.joslim.client.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joslim.client.entities.Client;
import com.joslim.client.repositories.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	ClientRepository repository;

	public List<Client> findAll() {
		
		return repository.findAll();
	}

}
