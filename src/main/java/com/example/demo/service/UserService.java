package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public void save(User user) throws EmailAlreadyExistsException {
		if (existsByEmail(user.getEmail())) {
			throw new EmailAlreadyExistsException("このメールアドレスは既に登録されています");
		}
		userRepository.save(user);
	}
}
