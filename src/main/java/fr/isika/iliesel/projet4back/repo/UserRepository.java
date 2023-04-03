package fr.isika.iliesel.projet4back.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.isika.iliesel.projet4back.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);

}
