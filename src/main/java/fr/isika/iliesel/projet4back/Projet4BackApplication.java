package fr.isika.iliesel.projet4back;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.isika.iliesel.projet4back.helper.UserFoundException;
import fr.isika.iliesel.projet4back.model.Match;
import fr.isika.iliesel.projet4back.model.Role;
import fr.isika.iliesel.projet4back.model.User;
import fr.isika.iliesel.projet4back.model.UserRole;
import fr.isika.iliesel.projet4back.service.UserService;

@SpringBootApplication
public class Projet4BackApplication implements CommandLineRunner {

	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Projet4BackApplication.class);

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(Projet4BackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Starting code");

		try {
			User user = new User();

			user.
					setFirstName("Admin");
			user.setLastName("Admin");
			user.setUsername("Admin");
			user.setPassword(this.bCryptPasswordEncoder.encode("Admin"));
			user.setEmail("admin@gmail.com");
			user.setProfile("default.png");

			Role role1 = new Role();
			role1.setRoleId(44L);
			role1.setRoleName("ADMIN");

			Set<UserRole> userRoleSet = new HashSet<>();
			UserRole userRole = new UserRole();
			userRole.setRole(role1);
			userRole.setUser(user);

			userRoleSet.add(userRole);

			Match match = new Match();
			match.setMatchRef("toto vs titi");
			match.setUser(user);

			List<Match> matchsFav = new ArrayList<Match>();
			matchsFav.add(match);
			user.setMatchsFav(matchsFav);

			User user1 = this.userService.createUser(user, userRoleSet);
			System.out.println(user1.getUsername());
		} catch (UserFoundException e) {
			e.printStackTrace();
		}

	}

}
