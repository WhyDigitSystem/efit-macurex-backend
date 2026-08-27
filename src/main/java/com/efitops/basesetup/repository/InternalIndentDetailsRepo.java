package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.InternalIndentDetailsVO;
import com.efitops.basesetup.entity.InternalIndentVO;

public interface InternalIndentDetailsRepo extends JpaRepository<InternalIndentDetailsVO, Long> {

	List<InternalIndentDetailsVO> findByInternalIndentVO(InternalIndentVO internalIndentVO);

}
