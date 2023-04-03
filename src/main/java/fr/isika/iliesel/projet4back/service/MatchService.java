package fr.isika.iliesel.projet4back.service;

import java.util.List;

import fr.isika.iliesel.projet4back.model.Match;
import fr.isika.iliesel.projet4back.model.User;

public interface MatchService {

	public List<Match> getAllMatchFavByUser(User user);

	public void deleteMatchFavByUserAndRefMatch(User user, String matchRef);

	public List<Match> getMatchFavByUser(User user, String matchRef);
}
