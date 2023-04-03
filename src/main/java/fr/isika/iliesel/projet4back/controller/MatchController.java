package fr.isika.iliesel.projet4back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.isika.iliesel.projet4back.model.Match;
import fr.isika.iliesel.projet4back.model.User;
import fr.isika.iliesel.projet4back.service.MatchService;
import fr.isika.iliesel.projet4back.service.UserService;

@RestController
@RequestMapping("/user/favoritematch")
@CrossOrigin("*")
public class MatchController {

	@Autowired
	private UserService userService;

	@Autowired
	private MatchService matchService;

	// Ajout d'un match favori pour un utilisateur
	@PostMapping("/add/{username}/{matchRef}")
	void addFavoriteMatch(@PathVariable("username") String username, @PathVariable("matchRef") String matchRef) {
		User user = this.userService.getUser(username);

		Match match = new Match();
		match.setUser(user);
		match.setMatchRef(matchRef);

		user.getMatchsFav().add(match);
		this.userService.updateUser(user);
	}

	// Récupération de tous les matchs favoris d'un utilisateur
	@GetMapping("/{username}")
	public List<Match> getFavMatchs(@PathVariable("username") String username) {
		User user = new User();
		user = this.userService.getUser(username);
		return this.matchService.getAllMatchFavByUser(user);
	}

	// Récupération des matchs favoris d'un utilisateur pour un match particulier
	@GetMapping("/{username}/{matchRef}")
	public List<Match> getFavMatchs(@PathVariable("username") String username,
									@PathVariable("matchRef") String matchRef) {
		User user = new User();
		user = this.userService.getUser(username);
		return this.matchService.getMatchFavByUser(user, matchRef);
	}

	// Suppression d'un match favori pour un utilisateur
	@DeleteMapping("/delete/{username}/{matchRef}")
	void deleteFavoriteMatch(@PathVariable("username") String username, @PathVariable("matchRef") String matchRef) {
		User user = new User();
		user = this.userService.getUser(username);
		this.matchService.deleteMatchFavByUserAndRefMatch(user, matchRef);
	}
}
