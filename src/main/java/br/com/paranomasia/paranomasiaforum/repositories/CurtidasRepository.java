package br.com.paranomasia.paranomasiaforum.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.paranomasia.paranomasiaforum.model.Curtidas;

public interface CurtidasRepository extends JpaRepository<Curtidas, Long>{
	
	List<Curtidas> findByIdDoTopicoAndIdDoUsuario(Long idDoTopico, Long idDoUsuario);
	List<Curtidas> findByIdDoTopico(Long idDoTopico);
	
}
