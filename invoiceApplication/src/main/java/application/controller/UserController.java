package application.controller;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.dto.SignInDTO;
import application.dto.TokenDTO;
import application.dto.UserDataDTO;
import application.dto.UserResponseDTO;
import application.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/signin")
	@ApiOperation(value = "${UserController.signin}")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 422, message = "Invalid username/password supplied") })
	public ResponseEntity<TokenDTO> login(//
			@ApiParam("Username") @RequestBody SignInDTO signIn) {
		String userName = signIn.getUserName();
		String password = signIn.getPassword();
		String token = userService.signin(userName, password);
		TokenDTO tokenDTO = new TokenDTO();
		tokenDTO.setToken(token);
		return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/me")
    @CrossOrigin
	@ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class, authorizations = {
			@Authorization(value = "apiKey") })
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public UserResponseDTO whoami(HttpServletRequest req) {
		return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
	}

	@PostMapping("/update-info")
	@ApiOperation(value = "${UserController.update-info}")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 422, message = "Invalid username/password supplied") })
	public ResponseEntity<UserResponseDTO> updateInfo(//
			@ApiParam("Username") @RequestBody UserDataDTO userInfo) {
		UserResponseDTO userResponseDTO = userService.updateInfo(userInfo); 
		return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
	}

//  @PostMapping("/signup")
//  @ApiOperation(value = "${UserController.signup}")
//  @ApiResponses(value = {//
//      @ApiResponse(code = 400, message = "Something went wrong"), //
//      @ApiResponse(code = 403, message = "Access denied"), //
//      @ApiResponse(code = 422, message = "Username is already in use")})
//  public String signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
//    return userService.signup(modelMapper.map(user, User.class));
//  }
//
//  @DeleteMapping(value = "/{username}")
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//  @ApiOperation(value = "${UserController.delete}", authorizations = { @Authorization(value="apiKey") })
//  @ApiResponses(value = {//
//      @ApiResponse(code = 400, message = "Something went wrong"), //
//      @ApiResponse(code = 403, message = "Access denied"), //
//      @ApiResponse(code = 404, message = "The user doesn't exist"), //
//      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
//  public String delete(@ApiParam("Username") @PathVariable String username) {
//    userService.delete(username);
//    return username;
//  }
//
//  @GetMapping(value = "/{username}")
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//  @ApiOperation(value = "${UserController.search}", response = UserResponseDTO.class, authorizations = { @Authorization(value="apiKey") })
//  @ApiResponses(value = {//
//      @ApiResponse(code = 400, message = "Something went wrong"), //
//      @ApiResponse(code = 403, message = "Access denied"), //
//      @ApiResponse(code = 404, message = "The user doesn't exist"), //
//      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
//  public UserResponseDTO search(@ApiParam("Username") @PathVariable String username) {
//    return modelMapper.map(userService.search(username), UserResponseDTO.class);
//  }

//  @GetMapping("/refresh")
//  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
//  public String refresh(HttpServletRequest req) {
//    return userService.refresh(req.getRemoteUser());
//  }

}
