package com.javaweb.controller.admin;


import com.javaweb.constant.SystemConstant;
import com.javaweb.enums.BuildingType;
import com.javaweb.enums.DistrictCode;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.security.utils.SecurityUtils;
import com.javaweb.service.IBuildingService;
import com.javaweb.service.impl.UserService;
import com.javaweb.utils.DisplayTagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller(value="buildingControllerOfAdmin")
public class BuildingController {
    @Autowired
    private UserService userService;

    @Autowired
    private IBuildingService buildingService;

    @RequestMapping(value = "/admin/building-list", method = RequestMethod.GET)
    public ModelAndView buildingList(@ModelAttribute BuildingSearchRequest buildingSearchRequest, HttpServletRequest request,
                                     @ModelAttribute(SystemConstant.MODEL) BuildingDTO model){
        ModelAndView mav = new ModelAndView("admin/building/list");
        mav.addObject("modelSearch", buildingSearchRequest);
        mav.addObject("listStaffs", userService.getStaffs());
        mav.addObject("districtCode", DistrictCode.type());
        mav.addObject("buildingType", BuildingType.type());
        if(SecurityUtils.getAuthorities().contains("ROLE_STAFF")){
            buildingSearchRequest.setStaffId(SecurityUtils.getPrincipal().getId());
            mav.addObject("buildingList", buildingService.fillAll(buildingSearchRequest));
        }
        else {
            mav.addObject("buildingList", buildingService.fillAll(buildingSearchRequest));
        }
        DisplayTagUtils.of(request, model);
        List<BuildingDTO> news = buildingService.getAllBuilding(PageRequest.of(model.getPage() - 1, model.getMaxPageItems()));
        model.setListResult(news);
        model.setTotalItems(buildingService.countTotalItems());
        mav.addObject(SystemConstant.MODEL, model);
        return mav;
    }
    

    @RequestMapping(value = "/admin/building-edit", method = RequestMethod.GET)
    public ModelAndView buildingEdit(@ModelAttribute("buildingEdit") BuildingDTO buildingDTO, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("admin/building/edit");
        mav.addObject("districtCode", DistrictCode.type());
        mav.addObject("buildingType", BuildingType.type());
        return mav;
    }

    @RequestMapping(value = "/admin/building-edit-{id}", method = RequestMethod.GET)
    public ModelAndView buildingEdit(@PathVariable("id") Long Id, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("admin/building/edit");
        BuildingDTO buildingDTO = buildingService.getBuildingById(Id);
        mav.addObject("buildingEdit", buildingDTO);
        mav.addObject("districtCode", DistrictCode.type());
        mav.addObject("buildingType", BuildingType.type());
        return mav;
    }
}
