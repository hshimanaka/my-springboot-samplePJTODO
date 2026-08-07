package com.example.demo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UserService {
private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	@Transactional
	public User loginCheck(String rawpassword, String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new AuthenticationFailedException("ユーザーが見つかりません: " + username));
		if(!passwordEncoder.matches(rawpassword, user.getPassword())) {
			throw new AuthenticationFailedException("パスワードが違います");
		}
		return user;
	}

	@Transactional
	public void register(String username, String rawpassword) {
		if(userRepository.findByUsername(username).isPresent()) {
			throw new DuplicateUsernameException("このユーザー名はすでに使われています");
		}
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(passwordEncoder.encode(rawpassword));
		userRepository.save(newUser);
	}
}
