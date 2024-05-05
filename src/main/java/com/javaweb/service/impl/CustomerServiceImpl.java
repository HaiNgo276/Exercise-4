package com.javaweb.service.impl;

import com.javaweb.converter.CustomerConverter;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.AssignmentDTO;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.request.CustomerCreateRequest;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.model.response.CustomerSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerConverter customerConverter;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<CustomerSearchResponse> fillAll(CustomerSearchRequest customerSearchRequest){
        List<CustomerEntity> customerEntities = customerRepository.findAll(customerSearchRequest);
        List<CustomerSearchResponse> customerSearchResponses = new ArrayList<>();
        for(CustomerEntity item : customerEntities){
            CustomerSearchResponse customer = customerConverter.toCustomerSearchRespone(item);
            customerSearchResponses.add(customer);
        }
        return customerSearchResponses;
    }

    public void addOrUpdateCustomer(CustomerCreateRequest customerCreateRequest){
        CustomerEntity customer = customerConverter.toCustomerEntity(customerCreateRequest);
        customerRepository.save(customer);
    }

    public void deleteCustomer(List<Long> id){
        for(Long item : id){
            CustomerEntity customerEntity = customerRepository.findById(item).get();
            if(customerEntity != null) {
                customerEntity.setIsActive(0);
                customerRepository.save(customerEntity);
            }
        }
    }

    public ResponseDTO listStaffByCustomerId(Long customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId).get();
        List<UserEntity> staffList = userRepository.findByStatusAndRoles_Code(1, "STAFF");
        List<UserEntity> assignedStaffList = customerEntity.getUserEntities();
        List<StaffResponseDTO> staffResponseDTOS = new ArrayList<>();
        for(UserEntity u : staffList){
            StaffResponseDTO staffResponseDTO = new StaffResponseDTO();
            staffResponseDTO.setStaffId(u.getId());
            staffResponseDTO.setFullName(u.getFullName());
            if(assignedStaffList.contains(u)){
                staffResponseDTO.setChecked("checked");
            }
            else staffResponseDTO.setChecked("");
            staffResponseDTOS.add(staffResponseDTO);
        }
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(staffResponseDTOS);
        responseDTO.setMessage("success");
        return responseDTO;
    }

    public void  updateAssignmentCustomer(AssignmentDTO assignmentCustomerDTO){
        CustomerEntity customerEntity = customerRepository.findById(assignmentCustomerDTO.getId()).get();
        List<UserEntity> staffList = userRepository.findByIdIn(assignmentCustomerDTO.getStaffs());
        customerEntity.setUserEntities(staffList);
        customerRepository.save(customerEntity);
    }

    public CustomerCreateRequest findOneById(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id).get();
        return customerConverter.toCustomerCreateRequest(customerEntity);
    }

    public List<CustomerDTO> getAllCustomer(Pageable pageable) {
        List<CustomerEntity> customerEntities = customerRepository.getAllCustomers(pageable);
        List<CustomerDTO> results = new ArrayList<>();
        for (CustomerEntity customerEntity : customerEntities) {
            CustomerDTO customerDTO = customerConverter.toCustomerDTO(customerEntity);
            results.add(customerDTO);
        }
        return results;
    }

    @Override
    public int countTotalItems() {
        return customerRepository.countTotalItem();
    }
}
