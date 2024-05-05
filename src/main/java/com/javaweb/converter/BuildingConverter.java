package com.javaweb.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.enums.DistrictCode;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.response.BuildingSearchResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BuildingConverter {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private RentAreaConverter rentAreaConverter;

	public BuildingSearchResponse toBuildingSearchResponse(BuildingEntity item) {
		BuildingSearchResponse building = modelMapper.map(item, BuildingSearchResponse.class);
		Map<String, String> districtList = DistrictCode.type();
		String areaResult = item.getRentAreaEntities().stream().map(it-> it.getValue().toString()).collect(Collectors.joining(","));
		building.setAddress(item.getStreet() + ", " + item.getWard() + ", " + districtList.get(item.getDistrict()));
		building.setRentArea(areaResult);
		return building;
	}
	public BuildingDTO toBuildingDTO(BuildingEntity item){
		BuildingDTO building = modelMapper.map(item, BuildingDTO.class);
		building.setAddress(item.getStreet() + ", " + item.getWard());
		List<String> arrayList = new ArrayList<>();
		String[] typeCode = item.getTypeCode().split(",");
		for(String x : typeCode){
			arrayList.add(x);
		}
		building.setTypeCode(arrayList);
		List<RentAreaEntity> rentAreaEntities = item.getRentAreaEntities();
		String rentArea = rentAreaEntities.stream().map(it->it.getValue().toString()).collect(Collectors.joining(", "));
		building.setRentArea(rentArea);
		return building;
	}
	public BuildingEntity toBuildingEntity(BuildingDTO item){
		BuildingEntity buildingEntity = modelMapper.map(item, BuildingEntity.class);
		String type = item.getTypeCode().stream().map(it->it.toString()).collect(Collectors.joining(","));
		buildingEntity.setRentAreaEntities(rentAreaConverter.toRentAreaEntity(item, buildingEntity));
		buildingEntity.setAvatar(item.getAvatar());
		buildingEntity.setTypeCode(type);
		return buildingEntity;
	}

}
