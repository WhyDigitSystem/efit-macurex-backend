package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BankingWithdrawalVO;
import com.efitops.basesetup.entity.WithdrawalParticularsVO;

@Repository
public interface WithdrawalParticularsRepo extends JpaRepository<WithdrawalParticularsVO, Long>{

	List<WithdrawalParticularsVO> findByBankingWithdrawalVO(BankingWithdrawalVO bankingWithdrawalVO);

}
