package fr.isika.iliesel.projet4back.service;

import java.util.List;
import java.util.Set;

import fr.isika.iliesel.projet4back.model.User;
import fr.isika.iliesel.projet4back.model.UserRole;

public interface UserService {

	public User createUser(User user, Set<UserRole> userRoles) throws Exception;

	public User getUser(String username);

	public void deleteUser(Long userId);

	public User updateUser(User user);

	public List<User> getAllUser();

}
