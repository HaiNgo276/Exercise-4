package com.javaweb.controller.admin;

import com.javaweb.constant.SystemConstant;
import com.javaweb.converter.CustomerConverter;
import com.javaweb.enums.StatusType;
import com.javaweb.enums.TransactionType;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.dto.TransactionDTO;
import com.javaweb.model.dto.UserDTO;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.model.request.CustomerCreateRequest;
import com.javaweb.security.utils.SecurityUtils;
import com.javaweb.service.ICustomerService;
import com.javaweb.service.ITransactionService;
import com.javaweb.service.impl.UserService;
import com.javaweb.utils.DisplayTagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Transactional
@Controller(value = "customersControllerOfAdmin")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerConverter customerConverter;
    @Autowired
    private ITransactionService transactionService;


    @RequestMapping(value = "/admin/customer-list", method = RequestMethod.GET)
    public ModelAndView getNews(@ModelAttribute CustomerSearchRequest customerSearchRequest,
                                @ModelAttribute(SystemConstant.MODEL) UserDTO model, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin/customer/list");
        mav.addObject("modelSearch", customerSearchRequest);
        mav.addObject("staffs", userService.getStaffs());
        if(SecurityUtils.getAuthorities().contains("ROLE_STAFF")){
            customerSearchRequest.setStaffId(SecurityUtils.getPrincipal().getId());
            mav.addObject("customerList", customerService.fillAll(customerSearchRequest));
        }
        else {
            mav.addObject("customerList", customerService.fillAll(customerSearchRequest));
        }
        DisplayTagUtils.of(request, model);
        List<CustomerDTO> news = customerService.getAllCustomer(PageRequest.of(model.getPage() - 1, model.getMaxPageItems()));
        model.setListResult(news);
        model.setTotalItems(customerService.countTotalItems());
        mav.addObject(SystemConstant.MODEL, model);
        return mav;
    }

    @RequestMapping(value = "/admin/customer-edit", method = RequestMethod.GET)
    public ModelAndView customerEdit(@ModelAttribute CustomerCreateRequest customerCreateRequest, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("admin/customer/edit");
        mav.addObject("statuses", StatusType.type());
        mav.addObject("TransactionList", TransactionType.type());
        mav.addObject("customerEdit", customerCreateRequest);
        return mav;
    }

    @RequestMapping(value = "/admin/customer-edit-{id}", method = RequestMethod.GET)
    public ModelAndView customerEdit(@PathVariable("id") Long id, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("admin/customer/edit");
        CustomerCreateRequest customerCreateRequest = customerService.findOneById(id);
        mav.addObject("statuses", StatusType.type());
        mav.addObject("TransactionList", TransactionType.type());
        mav.addObject("customerEdit", customerCreateRequest);
        List<TransactionDTO> CSKH = transactionService.findAllByCodeAndCustomer("CSKH", id);
        List<TransactionDTO> DDX = transactionService.findAllByCodeAndCustomer("DDX", id);
        mav.addObject("CSKH", CSKH);
        mav.addObject("DDX", DDX);
        return mav;
    }
}
