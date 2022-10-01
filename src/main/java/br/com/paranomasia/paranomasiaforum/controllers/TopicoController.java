package br.com.paranomasia.paranomasiaforum.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.paranomasia.paranomasiaforum.model.Contas;
import br.com.paranomasia.paranomasiaforum.model.Topico;
import br.com.paranomasia.paranomasiaforum.services.TopicosService;

@Controller
@RequestMapping("/topico")
public class TopicoController {
	
	@Autowired
	private TopicosService topicosService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String criarTopico(Topico topico, Contas conta) {
		
		return "criarTopico";
	}
	
	
	@RequestMapping(value="/novo", method=RequestMethod.POST)
	public String novo(@Valid Topico topico, BindingResult result) {
		
		topicosService.create(topico, result);
		return "redirect:/";
	
	}
	
}
