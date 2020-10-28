package application.service.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import application.constant.UserInfoConstant;
import application.dto.UserDataDTO;
import application.dto.UserResponseDTO;
import application.exception.CustomException;
import application.model.User;
import application.repository.UserRepository;
import application.security.JwtTokenProvider;
import application.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public String signin(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Override
	public User whoami(HttpServletRequest req) {
		return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	}

	@Override
	public String signup(User user) {
		if (!userRepository.existsByUsername(user.getUsername())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
		} else {
			throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Override
	public UserResponseDTO updateInfo(UserDataDTO userInfo) {
		User user = userRepository.findByUsername(UserInfoConstant.USER);
		user.setCompanyName(userInfo.getCompanyName());
//		user.setEmail(userInfo.getEmail());
		user.setAddress(userInfo.getAddress());
		user.setPhoneNumber(userInfo.getPhoneNumber());
		user = userRepository.save(user);
		UserResponseDTO userDTO = modelMapper.map(user, UserResponseDTO.class);
		return userDTO;
	}

//	@Override
//	public void delete(String username) {
//		userRepository.deleteByUsername(username);
//	}
//
//	@Override
//	public User search(String username) {
//		User user = userRepository.findByUsername(username);
//		if (user == null) {
//			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
//		}
//		return user;
//	}
//
//	@Override
//	public String refresh(String username) {
//		return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
//	}

}
