package br.com.paranomasia.paranomasiaforum.pagamentos;


import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping("/cartaodecredito")
public class CartaoDeCreditoController {
	
	@RequestMapping(value = "/novo", method = RequestMethod.GET)
	public String criarpagamento() throws IOException {
		

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
		map.add("itemId1","1");
		map.add("itemDescription1","Carteira Virtual - Paranomásia");
		map.add("itemAmount1","100.47");
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
        
        System.out.println(codigoDeCompra);
        
        return "redirect:" + "https://sandbox.pagseguro.uol.com.br/v2/checkout/payment.html?code=" + codigoDeCompra;
		//return response.toString();
	}
	
	@RequestMapping(value = "/consultar", method = RequestMethod.GET)
	public @ResponseBody String consultarpagamento() throws IOException {
		
		
		String uri = "https://ws.sandbox.pagseguro.uol.com.br/v2/transactions/BE41AEBB-E019-4ED4-93F6-50248F205B41?email=ramon22moratori@gmail.com&token=F072CB6A377045BE907DF6901B5D3597";
		System.out.println(uri);
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_XML);
		//headers.add("Accept", "application/x-www-form-urlencoded");
		//headers.add("Content-Type", "application/x-www-form-urlencoded");
		//------------
	
		
		String response = restTemplate.getForObject(uri, String.class); 
		//ResponseEntity<String> response = restTemplate.postForEntity(uri, request,  String.class);
		
        String respostaEtapa1[] = response.split("<status>");
        String respostaEtapa2[] = respostaEtapa1[1].split("</status>");
        
        String statusDeCompra = respostaEtapa2[0];
		
		System.out.println(statusDeCompra);
		return statusDeCompra;
	}
	
}
