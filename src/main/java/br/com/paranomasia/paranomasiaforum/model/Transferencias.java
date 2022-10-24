package br.com.paranomasia.paranomasiaforum.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transferencias {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private double valor;
	
	private Long idDoUsuario;
	
	private String dataDaTransferencia;
	
	private String codigoDaTransferencia;
	
	private double saldoAnterior;
	
	private double saldoFinal;
	
	private int status;
	
	private boolean creditoJaDepositado;

	
	//Getters and Setters
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getValor() {
		return this.valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Long getIdDoUsuario() {
		return this.idDoUsuario;
	}
	public void setIdDoUsuario(Long idDoUsuario) {
		this.idDoUsuario = idDoUsuario;
	}
	public String getDataDaTransferencia() {
		return this.dataDaTransferencia;
	}
	public void setDataDaTransferencia(String dataDaTransferencia) {
		this.dataDaTransferencia = dataDaTransferencia;
	}
	public String getCodigoDaTransferencia() {
		return this.codigoDaTransferencia;
	}
	public void setCodigoDaTransferencia(String codigoDaTransferencia) {
		this.codigoDaTransferencia = codigoDaTransferencia;
	}
	public double getSaldoAnterior() {
		return this.saldoAnterior;
	}
	public void setSaldoAnterior(double saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}
	public double getSaldoFinal() {
		return this.saldoFinal;
	}
	public void setSaldoFinal(double saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
	public int getStatus() {
		return this.status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isCreditoJaDepositado() {
		return this.creditoJaDepositado;
	}
	public void setCreditoJaDepositado(boolean creditoJaDepositado) {
		this.creditoJaDepositado = creditoJaDepositado;
	}
	
	
}
