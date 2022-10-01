package br.com.paranomasia.paranomasiaforum.controllers;

import org.springframework.stereotype.Service;

@Service
public class Curtir {
	
	private Long idDoTopico;
	private Long idDoUsuario;
	private Long avaliacao;
	
	public Curtir(Long idDoTopico, Long idDoUsuario, Long avaliacao) {
		
		this.idDoTopico = idDoTopico;
		this.idDoUsuario = idDoUsuario;
		this.avaliacao = avaliacao;
		
	}
	
	public Curtir() {
		
	}
	public Long getIdDoTopico() {
		return idDoTopico;
	}
	public void setIdDoTopico(Long idDoTopico) {
		this.idDoTopico = idDoTopico;
	}
	public Long getIdDoUsuario() {
		return idDoUsuario;
	}
	public void setIdDoUsuario(Long idDoUsuario) {
		this.idDoUsuario = idDoUsuario;
	}
	public Long getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Long avaliacao) {
		this.avaliacao = avaliacao;
	}
	
}
