package com.javaweb.api.admin;

import com.javaweb.model.request.TransactionCreateRequest;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.security.utils.SecurityUtils;
import com.javaweb.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@Transactional
@RequestMapping("/api/transaction")
public class TransactionAPI {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping
    public void addOrUpdateTransaction(@RequestBody TransactionCreateRequest transactionCreateRequest){
        Long staffId = SecurityUtils.getPrincipal().getId();
        transactionService.save(transactionCreateRequest, staffId);
    }
    @GetMapping("/{ids}")
    public ResponseDTO getTransaction(@PathVariable Long ids){
        return transactionService.findById(ids);
    }
}
