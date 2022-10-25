package br.com.paranomasia.paranomasiaforum.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import br.com.paranomasia.paranomasiaforum.model.Contas;
import br.com.paranomasia.paranomasiaforum.model.Perfil;
import br.com.paranomasia.paranomasiaforum.model.Transferencias;
import br.com.paranomasia.paranomasiaforum.repositories.ContasRepository;
import br.com.paranomasia.paranomasiaforum.repositories.PerfilRepository;

@Service
public class ContasService {
	
	@Autowired
	private ContasRepository contasRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private Transferencias transferencia;
	
	@Autowired
	private TransferenciasService transferenciaService;
	
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
		conta.setSaldo(0);
		
		//Setando perfil
		Optional<Perfil> perfilOptional = perfilRepository.findByNome("ROLE_USUARIO");
		Perfil perfil = perfilOptional.get();
		conta.adicionarPerfil(perfil);
		
		//CriandoTransferenciaInicial
		transferencia.setCodigoDaTransferencia("000");
		transferencia.setCreditoJaDepositado(false);
		transferencia.setIdDoUsuario(conta.getId());
		transferencia.setSaldoAnterior(0.00);
		transferencia.setSaldoFinal(0.00);
		transferencia.setStatus(1);
		transferencia.setValor(0.00);
		transferenciaService.saveAndFlush(transferencia);
		
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
