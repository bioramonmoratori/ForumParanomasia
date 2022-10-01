package br.com.paranomasia.paranomasiaforum.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.paranomasia.paranomasiaforum.model.Contas;
import br.com.paranomasia.paranomasiaforum.repositories.ContasRepository;

@Service
public class AutenticacaoService implements UserDetailsService{
	
	final ContasRepository contasRepository;
	
    public AutenticacaoService(ContasRepository contasRepository) {
        this.contasRepository = contasRepository;
    }

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Contas> usuario = contasRepository.findByEmail(username);
		
		if(usuario.isPresent()) {
			System.out.println(usuario.get().getAuthorities());
			return usuario.get();
		} 
		
		throw new UsernameNotFoundException("Usuario Nao Encontrado");
	}
	
}