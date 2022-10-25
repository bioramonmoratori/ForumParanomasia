package br.com.paranomasia.paranomasiaforum.model;

import java.text.DecimalFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Topico {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long idDoUsuario;
	
	private String nomeDoUsuario;
	
	@NotBlank @NotNull
	private String mensagem;
	private double avaliacao;
	
	
	public Topico(Long idDoUsuario, String nomeDoUsuario, String mensagem) {
		
		this.idDoUsuario = idDoUsuario;
		this.nomeDoUsuario = nomeDoUsuario;
		this.mensagem = mensagem;
		this.avaliacao = 0;
	
	}
	
	public Topico() {
		
	}
	
	//Getters and Setters
	public Long getId() {
		return id;
	}
	public Long getIdDoUsuario() {
		return idDoUsuario;
	}
	public void setIdDoUsuario(Long idDoUsuario) {
		this.idDoUsuario = idDoUsuario;
	}
	public String getNomeDoUsuario() {
		return nomeDoUsuario;
	}
	public void setNomeDoUsuario(String nomeDoUsuario) {
		this.nomeDoUsuario = nomeDoUsuario;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public double getAvaliacao() {
		double avaliacaoFormatada = this.avaliacao;
		DecimalFormat formato = new DecimalFormat("#.#"); 
		avaliacaoFormatada = Double.valueOf(formato.format(avaliacaoFormatada));
		return avaliacaoFormatada;
	}
	public void setAvaliacao(double avaliacao) {
		
		this.avaliacao = avaliacao;
	}
	

	
}
