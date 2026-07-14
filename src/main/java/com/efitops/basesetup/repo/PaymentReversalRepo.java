package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.PaymentReversalVO;

public interface PaymentReversalRepo extends JpaRepository<PaymentReversalVO, Long> {

	@Query (nativeQuery = true,value = "select * from paymentreversal where orgid=?1")
	List<PaymentReversalVO> getAllPaymentReversalByOrgId(Long orgId);

	@Query (nativeQuery = true,value = "select * from paymentreversal where paymentreversalid=?1")
	List<PaymentReversalVO> getAllPaymentReversalById(Long id);

	@Query (nativeQuery = true,value = "select * from paymentreversak where active=1")
	List<PaymentReversalVO> findPaymentReversalByActive();

}
