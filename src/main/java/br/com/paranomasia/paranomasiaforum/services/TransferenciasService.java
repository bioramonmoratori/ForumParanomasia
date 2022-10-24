package br.com.paranomasia.paranomasiaforum.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.paranomasia.paranomasiaforum.model.Contas;
import br.com.paranomasia.paranomasiaforum.model.Topico;
import br.com.paranomasia.paranomasiaforum.model.Transferencias;
import br.com.paranomasia.paranomasiaforum.repositories.ContasRepository;
import br.com.paranomasia.paranomasiaforum.repositories.TransferenciasRepository;

@Service
public class TransferenciasService {
	
	@Autowired
	TransferenciasRepository transferenciasRepository;
	@Autowired
	ContasRepository contasRepository;
	
	public String create(Transferencias transferencia) {
		
		Contas usuarioLogado = (Contas) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		transferencia.setIdDoUsuario(usuarioLogado.getId());
		transferencia.setSaldoAnterior(usuarioLogado.getSaldo());
		transferencia.setDataDaTransferencia("");
		transferencia.setSaldoFinal(usuarioLogado.getSaldo() + transferencia.getValor());

		String uri = "https://ws.sandbox.pagseguro.uol.com.br/v2/checkout?email=ramon22moratori@gmail.com&token=F072CB6A377045BE907DF6901B5D3597";
		System.out.println(uri);
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		
		//headers.setAccept(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		//headers.add("Accept", "application/x-www-form-urlencoded");
		//headers.add("Content-Type", "application/x-www-form-urlencoded");
		//------------
				
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("currency","BRL");
		map.add("itemId1","" + transferencia.getId());
		map.add("itemDescription1","Carteira Virtual - Paranomásia");
		map.add("itemAmount1","" + transferencia.getValor());
		map.add("itemQuantity1","1");
		//-
				
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		//String result = restTemplate.getForObject(uri, String.class); --> metodo GET
		ResponseEntity<String> response = restTemplate.postForEntity(uri, request,  String.class);
		String respostaComCodigoPagSeguro = response.toString();
		//String respostaComCodigoPagSeguro = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?><checkout><code>7C9218C670706F3CC47DFF90A6FF038A</code><date>2022-10-17T13:04:33.000-03:00</date></checkout>";
				
		//Pesquisando dentro de documento XML
		String respostaComCodigoPagSeguroCortadoEtapa1[] = respostaComCodigoPagSeguro.split("<code>");
		String respostaComCodigoPagSeguroCortadoEtapa2[] = respostaComCodigoPagSeguroCortadoEtapa1[1].split("</code>");
		        
		String codigoDeCompra = respostaComCodigoPagSeguroCortadoEtapa2[0];
		        
		//System.out.println(codigoDeCompra);
		transferencia.setCodigoDaTransferencia(codigoDeCompra);
		transferencia.setStatus(1);
		transferencia.setCreditoJaDepositado(false);
		this.saveAndFlush(transferencia);
		return "redirect:" + "https://sandbox.pagseguro.uol.com.br/v2/checkout/payment.html?code=" + codigoDeCompra;
		
	}
	
	public void saveAndFlush(Transferencias transferencia) {
		transferenciasRepository.saveAndFlush(transferencia);
	}
	
	public void atualizacaoDeSaldo() {
		Contas usuarioLogado = (Contas) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Long idDoUsuario = usuarioLogado.getId();
		
		Sort sort = Sort.by("id").descending();
		Transferencias ultimaTransferencia;
		try {
			List<Transferencias> consultaDeTransferenciasComIdDoUsuario = transferenciasRepository.findByIdDoUsuario(idDoUsuario, sort);
			 ultimaTransferencia = consultaDeTransferenciasComIdDoUsuario.get(0);
		} catch(Exception e) {
			ultimaTransferencia = null;
		}
		
		
		if(ultimaTransferencia.isCreditoJaDepositado() == false && ultimaTransferencia.getStatus() == 3) {
			System.out.println("Estamos debitando o valor na sua conta");
			
			usuarioLogado.setSaldo(ultimaTransferencia.getSaldoFinal());
			contasRepository.save(usuarioLogado);
			
			ultimaTransferencia.setCreditoJaDepositado(true);
			
			this.saveAndFlush(ultimaTransferencia);
		} else {
			System.out.println("A ultima adição de fundos já foi debitada ou o pagamento ainda nao foi confirmado");
		}
	}
}
