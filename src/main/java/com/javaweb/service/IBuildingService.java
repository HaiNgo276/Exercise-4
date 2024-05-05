package com.javaweb.service;

import com.javaweb.model.dto.AssignmentDTO;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IBuildingService {
	List<BuildingSearchResponse> fillAll(BuildingSearchRequest buildingSearchRequest);

    BuildingDTO getBuildingById(Long id);

    void addOrUpdate(BuildingDTO buildingDTO);

    void deleteBuildings(List<Long> ids);

    ResponseDTO listStaffs(Long id);

    void updateAssignmentBuilding(AssignmentDTO assignmentBuildingDTO);

    List<BuildingDTO> getAllBuilding(Pageable pageable);

    int countTotalItems();
}
