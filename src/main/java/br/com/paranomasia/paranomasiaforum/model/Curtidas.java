package br.com.paranomasia.paranomasiaforum.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Curtidas {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long idDoUsuario;
	private long idDoTopico;
	private long avaliacao;
	

	//Getters and Setters
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdDoUsuario() {
		return idDoUsuario;
	}
	public void setIdDoUsuario(long idDoUsuario) {
		this.idDoUsuario = idDoUsuario;
	}
	public long getIdDoTopico() {
		return idDoTopico;
	}
	public void setIdDoTopico(long idDoTopico) {
		this.idDoTopico = idDoTopico;
	}
	public long getAvaliacao() {
		return avaliacao;
	}
	public void setAvaliacao(long avaliacao) {
		this.avaliacao = avaliacao;
	}
	
}
