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
	public @ResponseBody String criarpagamento() throws IOException {
		

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
		map.add("itemDescription1","produto1");
		map.add("itemAmount1","5.00");
		map.add("itemQuantity1","1");
		//-
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(uri, request,  String.class);
		
		String teste = response.toString();
		
		//String result = restTemplate.getForObject(uri, String.class);
		
		System.out.println("AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIII::" + teste);

		//return response.toString();
	    return response.toString();
	}
	
}
