package application;

import java.util.ArrayList;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import application.constant.UserInfoConstant;
import application.domain.Role;
import application.domain.User;
import application.repository.UserRepository;
import application.service.UserService;

@SpringBootApplication
public class JwtAuthServiceApp implements CommandLineRunner {

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthServiceApp.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) throws Exception {
		if(userRepository.findByUsername("admin") == null) {
			User admin = new User();
			admin.setUsername(UserInfoConstant.USER);
			admin.setPassword(UserInfoConstant.PASSWORD);
			admin.setEmail("admin@email.com");
			admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
			userService.signup(admin);

//			User client = new User();
//			client.setUsername("client");
//			client.setPassword("client");
//			client.setEmail("client@email.com");
//			client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
//			userService.signup(client);
		}
	}

}
