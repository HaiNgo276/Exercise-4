package com.javaweb.repository.custom;


import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface BuildingRepositoryCustom {
	List<BuildingEntity> findAll(BuildingSearchRequest searchRequest);
	List<BuildingEntity> getAllBuildings(Pageable pageable);
	int countTotalItem();
}
