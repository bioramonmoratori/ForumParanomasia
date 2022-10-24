package br.com.paranomasia.paranomasiaforum.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.paranomasia.paranomasiaforum.model.Contas;
import br.com.paranomasia.paranomasiaforum.model.Topico;
import br.com.paranomasia.paranomasiaforum.model.Transferencias;
import br.com.paranomasia.paranomasiaforum.services.ContasService;
import br.com.paranomasia.paranomasiaforum.services.CurtidasService;
import br.com.paranomasia.paranomasiaforum.services.TopicosService;
import br.com.paranomasia.paranomasiaforum.services.TransferenciasService;

@Controller
@RequestMapping("/")
public class ForumController {
	
	@Autowired
	private ContasService contasService;
	
	@Autowired
	private TopicosService topicosService;
	
	@Autowired
	private CurtidasService curtidasService;
	
	@Autowired
	private Curtir curtir;
	
	@Autowired
	private TransferenciasService transferenciasService;
	
	@RequestMapping(value="/registro", method=RequestMethod.GET)
	public String registrar(Contas contas) {
		
		return "registro";
	}
	
	@RequestMapping(value="/novo", method=RequestMethod.POST)
	public String novo(@Valid Contas contas, BindingResult result) {
		
		return contasService.create(contas, result);
		
	}
	
	@RequestMapping(value="/inicio", method=RequestMethod.GET)
	public String inicio() {
		
		return "inicio";
		
	}
	
	@GetMapping("/")
	public String dashboard(Model model, Integer page, Integer filter) {
		
		if(page == null) {
			page = 0;
		}
		
		//Filtros (criar uma classe de filtros)
		
		//exemplo de filtros multiplos
		//sort = Sort.by(Sort.Order.desc("avaliacao"), Sort.Order.asc("nomeDoUsuario"));
		//-----------------------------
		
		Sort sort = null;
		
		if(filter == null) {
			filter = 1;
		}
		
		if(filter == 1) {
			sort = Sort.by(Sort.Order.desc("id"));
		}
		
		if(filter == 2) {
			sort = Sort.by(Sort.Order.asc("id"));
		}
		
		if(filter == 3) {
			sort = Sort.by(Sort.Order.desc("avaliacao"));
		}
		
		if(filter == 4) {
			sort = Sort.by(Sort.Order.asc("avaliacao"));
		}
		
		//Paginacao
		PageRequest paginacao = PageRequest.of(page, 4, sort);
		
		//Busca no repositorio
		Page<Topico> topicos = topicosService.findAll(paginacao);
		
		//atualizando saldo
		transferenciasService.atualizacaoDeSaldo();
		
		
		//Autenticacao e HTML
		Contas usuarioLogado = (Contas) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("topicos", topicos);
		model.addAttribute("usuarioLogado", usuarioLogado);
		model.addAttribute("filter", filter);
		model.addAttribute("curtidas", curtidasService);
		model.addAttribute("curtir", curtir);
		return "dashboard";
	}
	
	@RequestMapping(value="/curtir", method=RequestMethod.POST)
	public String curtir(Curtir curtir, @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer) {
		
		curtidasService.create(curtir.getIdDoTopico(), curtir.getIdDoUsuario(), curtir.getAvaliacao());
		return "redirect:" + referrer;
	}
	
	@RequestMapping(value="/adicionarfundos", method=RequestMethod.GET)
	public String adicionarfundos(Transferencias transferencia) {
		
		return "criarpagamento";
	}
	
	@RequestMapping(value="/criarpagamento", method=RequestMethod.POST)
	public String criarpagamento(@Valid Transferencias transferencia, BindingResult result) {
		
		return transferenciasService.create(transferencia);
		
	}
	
	@RequestMapping(value="/consultasaldo", method=RequestMethod.GET)
	public @ResponseBody String consultasaldo() {
		
		transferenciasService.atualizacaoDeSaldo();
		return "";
		
	}
}
