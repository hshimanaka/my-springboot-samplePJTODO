package com.example.demo;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class LoginService {
private final LoginRepository loginRepository;
private final PasswordEncoder passwordEncoder;
	
	public LoginService(LoginRepository loginRepository, PasswordEncoder passwordEncoder) {
		this.loginRepository = loginRepository;
		this.passwordEncoder = passwordEncoder;
	}
	@Transactional
	public User loginCheck(String rawpassword, String username) {
		User user = loginRepository.findByUsername(username)
				.orElseThrow(() -> new LoginNotFoundException("ユーザーが見つかりません: " + username));
		if(!passwordEncoder.matches(rawpassword, user.getPassword())) {
			throw new LoginNotFoundException("パスワードが違います");
		}
		return user;
	}

	@Transactional
	public void register(String username, String rawpassword) {
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(passwordEncoder.encode(rawpassword));
		loginRepository.save(newUser);
	}

}
