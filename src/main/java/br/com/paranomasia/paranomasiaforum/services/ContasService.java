package br.com.paranomasia.paranomasiaforum.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.paranomasia.paranomasiaforum.model.Contas;
import br.com.paranomasia.paranomasiaforum.model.Perfil;
import br.com.paranomasia.paranomasiaforum.repositories.ContasRepository;
import br.com.paranomasia.paranomasiaforum.repositories.PerfilRepository;

@Service
public class ContasService {
	
	@Autowired
	private ContasRepository contasRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	public String create(Contas conta, BindingResult result) {
		
		//Confere se a validacao promovida pelo bean validation veio com algum erro
		if(result.hasErrors()) {
			System.out.println("Nao realizou o registro pq deu algum erro");
			return "registro";
		}
		
		//Checando se o email ja existe
		List<Contas> listaDeContas = contasRepository.findAll();
		
		for(Contas contaDaLista : listaDeContas)
		{
		     String emailDaLista = contaDaLista.getEmail();
		     if(emailDaLista.equals(conta.getEmail())) {
		    	 System.out.println("Nao realizou o registro pq o email " + conta.getEmail() + " ja existe");
		    	 return "registro";
		     }
		}
		
			
		//Encriptografando senha
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String senhaCriptografada = passwordEncoder.encode(conta.getPassword());
		conta.setSenha(senhaCriptografada);
		
		//Setando perfil
		Optional<Perfil> perfilOptional = perfilRepository.findByNome("ROLE_USUARIO");
		Perfil perfil = perfilOptional.get();
		conta.adicionarPerfil(perfil);

		contasRepository.save(conta);
			
		return "redirect:/inicio";
			
		
	}
	
	public List<Contas> findAll() {
		return contasRepository.findAll();
	}
	
	public Optional<Contas> findById(Long id) {
		return contasRepository.findById(id);
	}
}
