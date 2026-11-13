package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.UserLoginBranchAccessibleVO;
import com.efitops.basesetup.entity.UserVO;

public interface UserBranchAccessRepo  extends JpaRepository<UserLoginBranchAccessibleVO, Long> {

	@Query(value = """
		    SELECT DISTINCT
		        br.branch_id,
		        br.branch,
		        br.code
		    FROM userbranchaccess uba
		    INNER JOIN users u
		        ON uba.user_id = u.userid
		    INNER JOIN branch br
		        ON uba.branch = br.branch_id
		    WHERE u.orgid = ?1
		      AND u.userid = ?2
		    """, nativeQuery = true)
		Set<Object[]> findGlobalParametersBranchByUserName(Long orgid, Long user);

	List<UserLoginBranchAccessibleVO> findByUserVO(UserVO userVO);

	

}

