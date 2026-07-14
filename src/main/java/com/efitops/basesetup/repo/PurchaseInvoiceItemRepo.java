package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseInvoiceItemVO;
import com.efitops.basesetup.entity.PurchaseInvoiceVO;
@Repository
public interface PurchaseInvoiceItemRepo extends JpaRepository<PurchaseInvoiceItemVO, Long> {

	List<PurchaseInvoiceItemVO> findByPurchaseInvoiceVO(PurchaseInvoiceVO purchaseInvoiceVO);

}
