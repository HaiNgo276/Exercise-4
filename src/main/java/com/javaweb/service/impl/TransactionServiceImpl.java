package com.javaweb.service.impl;

import com.javaweb.converter.TransactionConverter;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.dto.TransactionDTO;
import com.javaweb.model.request.TransactionCreateRequest;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.TransactionRepository;
import com.javaweb.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionConverter transactionConverter;
    @Autowired
    private CustomerRepository customerRepository;

    public void save(TransactionCreateRequest transactionCreateRequest, Long staffId){
        transactionRepository.save(transactionConverter.toTransactionEntity(transactionCreateRequest, staffId));
    }

    public List<TransactionDTO> findAllByCodeAndCustomer(String code, Long id) {
        CustomerEntity customer = customerRepository.findById(id).get();
        List<TransactionEntity> transactionEntities = transactionRepository.findAllByCodeAndCustomer(code, customer);
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        for(TransactionEntity t : transactionEntities){
            transactionDTOS.add(transactionConverter.toTransactionDTO(t));
        }
        return transactionDTOS;
    }

    public ResponseDTO findById(Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        TransactionEntity transactionEntity = transactionRepository.findById(id).get();
        responseDTO.setData(transactionEntity.getNote());
        responseDTO.setMessage("success");
        return responseDTO;
    }
}
