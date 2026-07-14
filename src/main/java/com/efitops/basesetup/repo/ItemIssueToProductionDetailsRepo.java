package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemIssueToProductionDetailsVO;
import com.efitops.basesetup.entity.ItemIssueToProductionVO;

@Repository
public interface ItemIssueToProductionDetailsRepo extends JpaRepository<ItemIssueToProductionDetailsVO, Long> {

	List<ItemIssueToProductionDetailsVO> findByItemIssueToProductionVO(ItemIssueToProductionVO itemIssueToProductionVO);

}
