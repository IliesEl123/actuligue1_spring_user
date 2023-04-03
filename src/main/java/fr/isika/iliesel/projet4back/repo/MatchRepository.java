package fr.isika.iliesel.projet4back.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import fr.isika.iliesel.projet4back.model.Match;
import fr.isika.iliesel.projet4back.model.User;

public interface MatchRepository extends JpaRepository<Match, Long> {

	public List<Match> findByUser(User user);

	public List<Match> findByUserAndMatchRef(User user, String matchRef);

	public void deleteByUserAndMatchRef(@Param("user") User user, @Param("matchRef") String matchRef);

}
