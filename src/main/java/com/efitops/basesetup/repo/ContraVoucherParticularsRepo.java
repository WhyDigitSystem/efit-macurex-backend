package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ContraVoucherParticularsVO;
import com.efitops.basesetup.entity.ContraVoucherVO;

@Repository
public interface ContraVoucherParticularsRepo extends JpaRepository<ContraVoucherParticularsVO, Long> {

	List<ContraVoucherParticularsVO> findByContraVoucherVO(ContraVoucherVO contraVoucherVO);

}
