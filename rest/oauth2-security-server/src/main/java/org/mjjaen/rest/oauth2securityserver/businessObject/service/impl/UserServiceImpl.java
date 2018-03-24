package org.mjjaen.rest.oauth2securityserver.businessObject.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mjjaen.rest.oauth2securityserver.businessObject.model.CustomUserDetails;
import org.mjjaen.rest.oauth2securityserver.businessObject.model.User;
import org.mjjaen.rest.oauth2securityserver.businessObject.repository.UserRepository;
import org.mjjaen.rest.oauth2securityserver.businessObject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value="userService")
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(userName);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new CustomUserDetails(user);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(Integer id) {
		userRepository.deleteById(id);
	}
	
	@Override
	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
