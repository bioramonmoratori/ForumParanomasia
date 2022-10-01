package br.com.paranomasia.paranomasiaforum.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.paranomasia.paranomasiaforum.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long>{
	
	Optional<Perfil> findByNome(String nome);
}
