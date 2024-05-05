package com.javaweb.service.impl;


import com.javaweb.converter.BuildingConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.AssignmentDTO;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.IBuildingService;
import com.javaweb.utils.UploadFileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingServiceImpl implements IBuildingService {
	@Autowired
	private BuildingRepository buildingRepository;
	@Autowired
	private BuildingConverter buildingConverter;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UploadFileUtils uploadFileUtils;


	@Override
	public List<BuildingSearchResponse> fillAll(BuildingSearchRequest buildingSearchRequest){
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(buildingSearchRequest);
		List<BuildingSearchResponse> result = new ArrayList<>();
		for(BuildingEntity item : buildingEntities) {
			BuildingSearchResponse building = buildingConverter.toBuildingSearchResponse(item);
			result.add(building);
		}
		return result;
	}

	private String saveThumbnail(BuildingDTO buildingDTO, BuildingEntity buildingEntity) {
		String path = "/building/" + buildingDTO.getImageName();
		if (null != buildingDTO.getImageBase64()) {
			if (null != buildingEntity.getAvatar()) {
				if (!path.equals(buildingEntity.getAvatar())) {
					File file = new File("C://home/office" + buildingEntity.getAvatar());
					file.delete();
				}
			}
			byte[] bytes = Base64.decodeBase64(buildingDTO.getImageBase64().getBytes());
			uploadFileUtils.writeOrUpdate(path, bytes);
			buildingEntity.setAvatar(path);
		}
		return path;
	}
	public void addOrUpdate(BuildingDTO buildingDTO) {
		BuildingEntity building = buildingConverter.toBuildingEntity(buildingDTO);
		saveThumbnail(buildingDTO, building);
		buildingRepository.save(building);
	}
	public BuildingDTO getBuildingById(Long id){
		BuildingEntity building = buildingRepository.findById(id).get();
		return buildingConverter.toBuildingDTO(building);
	}
	public void deleteBuildings(List<Long> Ids) {
		buildingRepository.deleteAllByIdIn(Ids);
	}

	@Override
	public ResponseDTO listStaffs(Long buildingId) {
		BuildingEntity building = buildingRepository.findById(buildingId).get();
		List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(1, "STAFF");
		List<UserEntity> staffAssignment = building.getUserEntities();
		List<StaffResponseDTO> staffResponseDTOS = new ArrayList<>();
		ResponseDTO responseDTO = new ResponseDTO();
		for(UserEntity it : staffs){
			StaffResponseDTO staffResponseDTO = new StaffResponseDTO();
			staffResponseDTO.setStaffId(it.getId());
			staffResponseDTO.setFullName(it.getFullName());
			if(staffAssignment.contains(it)){
				staffResponseDTO.setChecked("checked");
			}
			else{
				staffResponseDTO.setChecked("");
			}
			staffResponseDTOS.add(staffResponseDTO);
		}
		responseDTO.setData(staffResponseDTOS);
		responseDTO.setMessage("success");
		return responseDTO;
	}
	@Override
	public void updateAssignmentBuilding(AssignmentDTO assignmentBuildingDTO) {
		BuildingEntity buildingEntity = buildingRepository.findById(assignmentBuildingDTO.getId()).get();
		List<UserEntity> staffList = userRepository.findByIdIn(assignmentBuildingDTO.getStaffs());
		buildingEntity.setUserEntities(staffList);
		buildingRepository.save(buildingEntity);
	}

	@Override
	public List<BuildingDTO> getAllBuilding(Pageable pageable) {
		List<BuildingEntity> buildingEntities = buildingRepository.getAllBuildings(pageable);
		List<BuildingDTO> results = new ArrayList<>();
		for (BuildingEntity buildingEntity : buildingEntities) {
			BuildingDTO buildingDTO = buildingConverter.toBuildingDTO(buildingEntity);
			results.add(buildingDTO);
		}
		return results;
	}

	@Override
	public int countTotalItems() {
		return buildingRepository.countTotalItem();
	}
}