package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.InstrumentCalibrationVO;

public interface InstrumentCalibrationRepo extends JpaRepository<InstrumentCalibrationVO, Long> {

	@Query(nativeQuery = true, value = """
			SELECT concat(prefix, lpad(last_no, 5, 0)) AS docid
			FROM documenttypemapping_details
			WHERE org_id = ?1
			  AND fin_year = ?2
			  AND screen_code = ?3
			""")
	String getInstrumentCalibrationDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
			SELECT *
			FROM instrument_calibration_basic
			WHERE instrument_calibration_basic_id = ?1
			""", nativeQuery = true)
	InstrumentCalibrationVO getInstrumentCalibrationById(Long id);

	@Query(value = """
			SELECT *
			FROM instrument_calibration_basic
			WHERE org_id = ?1
			AND branch = ?2
			  AND active = true
			  AND cancel = false
			""", nativeQuery = true)
	List<InstrumentCalibrationVO> getInstrumentCalibrationByOrgId(Long orgId,Long branch);
	
//	machineno dropdown

	@Query(value = """
	        SELECT
	            mem.machine_equipments_master_id AS machineMasterId,
	            mem.machine_instrument_no AS machineInstrumentNo,
	            mem.location AS locationId,
	            l.location_name AS locationName
	        FROM machine_equipments_master mem
	        LEFT JOIN location l
	            ON l.id = mem.location
	        WHERE mem.machine_equipments_master_id = :machineId
	          AND mem.branch = :branch
	          AND mem.org_id = :orgId
	          AND mem.active = 1
	          AND mem.cancel = 0
	        ORDER BY mem.machine_instrument_no
	        """, nativeQuery = true)
	List<Object[]> getMachineNoForInstrumentCalibration(
	        @Param("machineId") Long machineId,
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId);
	

}
