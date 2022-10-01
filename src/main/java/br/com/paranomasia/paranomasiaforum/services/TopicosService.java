package br.com.paranomasia.paranomasiaforum.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.paranomasia.paranomasiaforum.model.Contas;
import br.com.paranomasia.paranomasiaforum.model.Topico;
import br.com.paranomasia.paranomasiaforum.repositories.TopicosRepository;

@Service
public class TopicosService {
	
	@Autowired
	TopicosRepository topicosRepository;
	
	public String create(Topico topico, BindingResult result) {
		
		//Confere se a validacao promovida pelo bean validation veio com algum erro
		if(result.hasErrors()) {
			return "topico";
		}
		
		Contas usuarioLogado = (Contas) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		topico.setNomeDoUsuario(usuarioLogado.getNome());
		topico.setAvaliacao(0);
		topico.setIdDoUsuario(usuarioLogado.getId());
		topicosRepository.save(topico);
		return "redirect:/dashboard";
	}
	
	
	public Page<Topico> findAll(Pageable paginacao) {
		
		return topicosRepository.findAll(paginacao);
	}
	
	public List<Topico> findByAvaliacao(double avaliacao) {
		
		Sort sort = Sort.by("avaliacao").ascending();
		return topicosRepository.findByAvaliacao(avaliacao, sort);
	}
	
	public Optional<Topico> findById(Long id) {
		return topicosRepository.findById(id);
	}
	
	public void saveAndFlush(Topico topico) {
		topicosRepository.saveAndFlush(topico);
	}
	
}
