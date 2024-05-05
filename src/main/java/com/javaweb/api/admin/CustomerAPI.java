package com.javaweb.api.admin;

import com.javaweb.model.dto.AssignmentDTO;
import com.javaweb.model.request.CustomerCreateRequest;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/api/customer")
public class CustomerAPI {
    @Autowired
    private ICustomerService customerService;

    @PostMapping
    public void addOrUpdateCustomer(@RequestBody CustomerCreateRequest customerCreateRequest){
        customerService.addOrUpdateCustomer(customerCreateRequest);
    }

    @PostMapping("/{ids}")
    public void deleteCustomers(@PathVariable List<Long> ids){
        customerService.deleteCustomer(ids);
    }

    @GetMapping("/{id}/staffs")
    public ResponseDTO loadStaffByCustomerId(@PathVariable Long id){
        ResponseDTO result = customerService.listStaffByCustomerId(id);
        return result;
    }

    @PostMapping("/assignment")
    public void updateAssignmentCustomer(@RequestBody AssignmentDTO assignmentCustomerDTO){
        customerService.updateAssignmentCustomer(assignmentCustomerDTO);
    }
}