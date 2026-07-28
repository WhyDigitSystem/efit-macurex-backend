package com.efitops.basesetup.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.UserVO;
@Repository
public interface UserRepo extends JpaRepository<UserVO, Long> {

	boolean existsByUserNameOrEmail(String userName, String email);

	@Query("select a from UserVO a where a.userName=?1")
	UserVO findByUserName(String userName);

	@Query(value = "select u from UserVO u where u.id =?1")
	UserVO getUserById(Long usersId);


	UserVO findByUserNameOrEmailOrMobileNo(String userName, String userName2, String userName3);

	@Query(value = "select u from UserVO u where u.orgId =?1")
	List<UserVO> findAllByOrgId(Long orgId);

	boolean existsByUserNameOrEmailOrMobileNo(String userName, String email, String email2);

	@Query("SELECT u.id FROM UserVO u WHERE u.employee.employeeCode IN :empCodes")
	List<Long> findUserIdsByEmployeeCodes(@Param("empCodes") List<String> empCodes);

	UserVO findByUserNameOrEmailOrMobileNoOrEmployee_EmployeeName(
	        String userName,
	        String email,
	        String mobileNo,
	        String employeeName);

	  Optional<UserVO> findByEmail(String email);

	  UserVO findByEmployee_EmployeeName(String employeeName);
//	UserVO findByUserNameAndUsersId(String userName, Long usersId);



	
	


}

