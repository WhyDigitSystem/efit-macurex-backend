package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.PaymentOtherAccountVO;

public interface PaymentOtherAccountRepo extends JpaRepository<PaymentOtherAccountVO, Long>{

}
