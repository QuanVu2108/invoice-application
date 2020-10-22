package application.service;

import javax.servlet.http.HttpServletRequest;

import application.model.User;

public interface UserService {

	String signin(String username, String password);

	Object whoami(HttpServletRequest req);

	String signup(User admin);

}
