package com.javaweb.service;

import com.javaweb.model.dto.AssignmentDTO;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.request.CustomerCreateRequest;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.model.response.CustomerSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService {
    List<CustomerSearchResponse> fillAll(CustomerSearchRequest customerSearchRequest);

    void addOrUpdateCustomer(CustomerCreateRequest customerCreateRequest);

    void deleteCustomer(List<Long> ids);

    ResponseDTO listStaffByCustomerId(Long id);

    void updateAssignmentCustomer(AssignmentDTO assignmentCustomerDTO);

    CustomerCreateRequest findOneById(Long id);

    int countTotalItems();

    List<CustomerDTO> getAllCustomer(Pageable pageable);
}
