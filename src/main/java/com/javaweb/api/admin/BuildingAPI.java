package com.javaweb.api.admin;

import com.javaweb.model.dto.AssignmentDTO;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RestController(value = "buildingAPIOfAdmin")
@Transactional
@RequestMapping("/api/building")
public class BuildingAPI {
    @Autowired
    private IBuildingService buildingService;

    @PostMapping
    public void addOrUpdateBuilding(@RequestBody BuildingDTO buildingDTO){
        buildingService.addOrUpdate(buildingDTO);
    }

    @DeleteMapping("/{ids}")
    public void deleteBuilding(@PathVariable List<Long> ids){
        buildingService.deleteBuildings(ids);
        System.out.println("ok");
    }

    @GetMapping("/{id}/staffs")
    public ResponseDTO loadStaffs(@PathVariable Long id){
        ResponseDTO result = buildingService.listStaffs(id);
        return result;
    }

    @PostMapping("/assignment")
    public void updateAssignmentBuilding(@RequestBody AssignmentDTO assignmentBuildingDTO){
        buildingService.updateAssignmentBuilding(assignmentBuildingDTO);
    }

}

