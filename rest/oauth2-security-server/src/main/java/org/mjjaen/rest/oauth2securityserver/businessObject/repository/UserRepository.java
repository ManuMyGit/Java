package org.mjjaen.rest.oauth2securityserver.businessObject.repository;

import org.mjjaen.rest.oauth2securityserver.businessObject.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	public User findByUserName(String userName);
}
