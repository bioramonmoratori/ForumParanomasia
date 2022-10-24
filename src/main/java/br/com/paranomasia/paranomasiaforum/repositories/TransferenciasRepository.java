package br.com.paranomasia.paranomasiaforum.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.paranomasia.paranomasiaforum.model.Transferencias;

public interface TransferenciasRepository extends JpaRepository<Transferencias, Long>{
	
	List<Transferencias> findByIdDoUsuario(Long idDoUsuario, Sort sort);
	
}
