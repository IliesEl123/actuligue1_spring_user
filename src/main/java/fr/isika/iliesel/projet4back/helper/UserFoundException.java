package fr.isika.iliesel.projet4back.helper;

public class UserFoundException extends Exception {

	public UserFoundException() {
		super("User with this username is already there in DB");
	}

	public UserFoundException(String msg) {
		super(msg);
	}
}
