package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.ListOfValuesVO;

@Repository
public interface ListOfValuesDetailsRepo extends JpaRepository<ListOfValuesDetailsVO, Long>{

	List<ListOfValuesDetailsVO> findBylistOfValuesVO(ListOfValuesVO listOfValuesVO);

	void deleteByListOfValuesVO_Id(Long id);

	List<ListOfValuesDetailsVO> findByListOfValuesVO(ListOfValuesVO listOfValuesVO);
}

