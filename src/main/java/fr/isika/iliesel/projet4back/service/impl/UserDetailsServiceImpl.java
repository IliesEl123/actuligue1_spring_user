package fr.isika.iliesel.projet4back.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.isika.iliesel.projet4back.model.User;
import fr.isika.iliesel.projet4back.repo.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	// Déclaration du logger
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserRepository userRepo;

	// Implémentation de la méthode loadUserByUsername de l'interface UserDetailsService
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Recherche de l'utilisateur correspondant au nom d'utilisateur passé en paramètre
		User user = this.userRepo.findByUsername(username);
		// Si l'utilisateur n'est pas trouvé, on lance une exception UsernameNotFoundException
		if (user == null) {
			LOGGER.error("User not found");
			throw new UsernameNotFoundException("No User Found !!");
		}
		// Sinon, on retourne l'objet User, qui implémente l'interface UserDetails de Spring Security
		return user;
	}

}
