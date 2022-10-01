package br.com.paranomasia.paranomasiaforum.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.paranomasia.paranomasiaforum.model.Curtidas;
import br.com.paranomasia.paranomasiaforum.model.Topico;
import br.com.paranomasia.paranomasiaforum.repositories.CurtidasRepository;

@Service
public class CurtidasService {
	
	@Autowired
	CurtidasRepository curtidasRepository;
	
	@Autowired
	TopicosService topicosService;
	
	public List<Curtidas> findAll() {
		
		return curtidasRepository.findAll();
	}
	
	public Boolean jaFoiCurtido(Long idDoTopico, Long idDoUsuario) {
		
		if(curtidasRepository.findByIdDoTopicoAndIdDoUsuario(idDoTopico, idDoUsuario).isEmpty()) {
			return true;
		}
		
		return false;
		
	}
	
	public String create(Long idDoTopico, Long idDoUsuario, Long avaliacao) {
		
		Curtidas curtidas = new Curtidas();
		curtidas.setIdDoTopico(idDoTopico);
		curtidas.setIdDoUsuario(idDoUsuario);
		curtidas.setAvaliacao(avaliacao);
		
		curtidasRepository.save(curtidas);
		
		Optional<Topico> buscandoTopicoOptional = topicosService.findById(idDoTopico);
		Topico topicoEncontrado = buscandoTopicoOptional.get();
		
		int numeroDeCurtidas = curtidasRepository.findByIdDoTopico(idDoTopico).size();
		System.out.println("Numero de Curtidas encontrado no BD de topicos: "+numeroDeCurtidas);
		
		
		double mediaAvaliacao = (avaliacao + topicoEncontrado.getAvaliacao()) / numeroDeCurtidas;
		System.out.println("Avaliacao gerada pelo botao: "+avaliacao);
		System.out.println("Avaliacao antiga: "+topicoEncontrado.getAvaliacao());
		System.out.println("Media: "+mediaAvaliacao);
		
		topicoEncontrado.setAvaliacao(mediaAvaliacao);
		topicosService.saveAndFlush(topicoEncontrado);
		
		return "/";
	}
}
