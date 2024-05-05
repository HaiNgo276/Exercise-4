package com.javaweb.converter;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.enums.StatusType;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.request.CustomerCreateRequest;
import com.javaweb.model.response.CustomerSearchResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomerConverter {
    @Autowired
    private ModelMapper modelMapper;

    public CustomerSearchResponse toCustomerSearchRespone(CustomerEntity customerEntity){
        CustomerSearchResponse customerSearchResponse = modelMapper.map(customerEntity, CustomerSearchResponse.class);
        customerSearchResponse.setFullname(customerEntity.getFullname());
        Map<String, String> status = StatusType.type();
        if(customerEntity.getStatus() != null) customerSearchResponse.setStatus(status.get(customerEntity.getStatus()));
        return customerSearchResponse;
    }

    public CustomerEntity toCustomerEntity(CustomerCreateRequest customerCreateRequest){
        CustomerEntity customerEntity = modelMapper.map(customerCreateRequest, CustomerEntity.class);
        return customerEntity;
    }

    public CustomerDTO toCustomerDTO(CustomerEntity customerEntity){
        CustomerDTO customerDTO = modelMapper.map(customerEntity, CustomerDTO.class);
        return customerDTO;
    }

    public CustomerCreateRequest toCustomerCreateRequest(CustomerEntity customerEntity){
        CustomerCreateRequest customerCreateRequest = modelMapper.map(customerEntity, CustomerCreateRequest.class);
        return customerCreateRequest;
    }
}
