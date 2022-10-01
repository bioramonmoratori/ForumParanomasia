package br.com.paranomasia.paranomasiaforum.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.paranomasia.paranomasiaforum.model.Topico;

public interface TopicosRepository extends JpaRepository<Topico, Long>{

	List<Topico> findByAvaliacao(double avaliacao, Sort sort);
	@Override
	Page<Topico> findAll(Pageable paginacao);
	
	@Override
	Optional<Topico> findById(Long id);

}
