package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionPlanDetailsVO;
import com.efitops.basesetup.entity.ProductionPlanVO;

@Repository
public interface ProductionPlanDetailsRepo extends JpaRepository<ProductionPlanDetailsVO, Long> {

	List<ProductionPlanDetailsVO> findByProductionPlanVO(ProductionPlanVO productionPlanVO);

	@Query(value = "SELECT * FROM productionplandetails p " +
            "JOIN productionplan p1 ON p.productionplanid = p1.productionplanid " +
            "WHERE LOWER(TRIM(p.machinename)) = LOWER(TRIM(:machineName)) " +
            "AND p1.orgid = :orgId",
    nativeQuery = true)
List<ProductionPlanDetailsVO> findByMachineNameIgnoreCase(
     @Param("machineName") String machineName,
     @Param("orgId") Long orgId);


}
