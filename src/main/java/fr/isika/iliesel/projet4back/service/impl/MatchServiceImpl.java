package fr.isika.iliesel.projet4back.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.iliesel.projet4back.model.Match;
import fr.isika.iliesel.projet4back.model.User;
import fr.isika.iliesel.projet4back.repo.MatchRepository;
import fr.isika.iliesel.projet4back.service.MatchService;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {

	@Autowired
	private MatchRepository matchRepository;

	// retourne la liste de tous les matchs favoris d'un utilisateur
	@Override
	public List<Match> getAllMatchFavByUser(User user) {
		return matchRepository.findByUser(user);
	}

	// supprime le match favori d'un utilisateur et la référence du match
	@Override
	public void deleteMatchFavByUserAndRefMatch(User user, String matchRef) {
		this.matchRepository.deleteByUserAndMatchRef(user, matchRef);
	}

	// retourne la liste des matchs favoris d'un utilisateur pour une référence de match donnée
	@Override
	public List<Match> getMatchFavByUser(User user, String matchRef) {
		return this.matchRepository.findByUserAndMatchRef(user, matchRef);
	}

}
