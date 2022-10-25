package br.com.paranomasia.paranomasiaforum.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.paranomasia.paranomasiaforum.model.Contas;

public interface ContasRepository extends JpaRepository<Contas, Long>{
	
	Optional<Contas> findByEmail(String email);
	
	
}
