package application.service;

import javax.servlet.http.HttpServletRequest;

import application.dto.UserDataDTO;
import application.dto.UserResponseDTO;
import application.domain.User;

public interface UserService {

	String signin(String username, String password);

	Object whoami(HttpServletRequest req);

	String signup(User admin);

	UserResponseDTO updateInfo(UserDataDTO userInfo);

}
