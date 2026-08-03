package com.example.demo;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UserService {
private final UserRepository loginRepository;
private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository loginRepository, PasswordEncoder passwordEncoder) {
		this.loginRepository = loginRepository;
		this.passwordEncoder = passwordEncoder;
	}
	@Transactional
	public User loginCheck(String rawpassword, String username) {
		User user = loginRepository.findByUsername(username)
				.orElseThrow(() -> new AuthenticationFailedException("ユーザーが見つかりません: " + username));
		if(!passwordEncoder.matches(rawpassword, user.getPassword())) {
			throw new AuthenticationFailedException("パスワードが違います");
		}
		return user;
	}

	@Transactional
	public void register(String username, String rawpassword) {
		if(loginRepository.findByUsername(username).isPresent()) {
			throw new DuplicateUsernameException("このユーザー名はすでに使われています");
		}
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(passwordEncoder.encode(rawpassword));
		loginRepository.save(newUser);
	}
}
