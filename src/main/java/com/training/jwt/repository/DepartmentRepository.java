package com.training.jwt.repository;

import java.util.Set;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.training.jwt.model.Department;
import com.training.jwt.model.User;

public interface DepartmentRepository extends CassandraRepository<Department> {

	@Query("SELECT * FROM tms_department WHERE department_id=?0 LIMIT 1")
	User findById(Integer deptId);

	@Query("SELECT * FROM tms_department WHERE department_id in (?0) ")
	Iterable<Department> getDeptListByIds(Set<Integer> deptIds);

}
