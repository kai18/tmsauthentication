package com.training.jwt.repository;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.training.jwt.model.User;

@Repository
public interface UserRepository extends CassandraRepository<User> {

	/*@Query("SELECT * FROM tms_users WHERE user_id=?0 LIMIT 1")
	User findById(Integer userId);
	
	@Query("SELECT * FROM tms_users WHERE email_id=?0 ALLOW FILTERING")
	User findByEmailId(String userId);

	@Query("SELECT * FROM tms_users WHERE user_id in (?0) ")
	Iterable<User> getUserListByUserIds(Set<Integer> userIds);*/

	public User findByEmailId(String emailId);
	public User findById(UUID id);
}
