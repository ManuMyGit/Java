package org.mjjaen.rest.oauth2securityserver.businessObject.service;

import java.util.List;

import org.mjjaen.rest.oauth2securityserver.businessObject.model.User;

public interface UserService {
	public User save(User user);
    public List<User> findAll();
    public void delete(Integer id);
}
