package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.QADRegisterDetailsVO;
import com.efitops.basesetup.entity.QADRegisterVO;

@Repository
public interface QADRegisterDetailsRepo extends JpaRepository<QADRegisterDetailsVO, Long> {



	List<QADRegisterDetailsVO> findByqadRegisterVO(QADRegisterVO qadRegisterVO);

}
