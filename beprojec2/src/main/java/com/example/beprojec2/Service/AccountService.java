package com.example.beprojec2.Service;

import com.example.beprojec2.DTO.AccountDTO;
import com.example.beprojec2.Entity.Account;
import com.example.beprojec2.Entity.Role;
import com.example.beprojec2.Payload.Request.CartRequest;
import com.example.beprojec2.Payload.Request.SignUpRequest;
import com.example.beprojec2.Payload.ResponData;
import com.example.beprojec2.Responsitory.AccountReponsitory;
import com.example.beprojec2.Service.Imp.AccountServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
 
@Slf4j
@Service
public class AccountService implements AccountServiceImp {
    @Autowired
    AccountReponsitory accountReponsitory;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;
    @Override
    public List<AccountDTO> getallAccounts() {
        List<Account> accounts = accountReponsitory.findAll();
        List<AccountDTO> accountDTOS = new ArrayList<>();
        for (Account account : accounts) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setAccountid(account.getAccountid());
            accountDTO.setUsername(account.getUsername());
            accountDTO.setPassword(account.getPassword());
            accountDTO.setFullname(account.getFullname());
            accountDTO.setStartdate(account.getStartdate());
            accountDTO.setRolename(account.getRole().getRolename());
            accountDTOS.add(accountDTO);
        }
        return accountDTOS;
    }
    @Override
    public AccountDTO accountDetails(String username) {
        Account account = accountReponsitory.findByUsername(username);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountid(account.getAccountid());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setPassword(account.getPassword());
        accountDTO.setFullname(account.getFullname());
        accountDTO.setStartdate(account.getStartdate());
        accountDTO.setRolename(account.getRole().getRolename());
        return accountDTO;
    }

    @Override
    public ResponData sendVerifyCode(String username) {
        ResponData responData = new ResponData();
        Account account = accountReponsitory.findByUsername(username);
        if(account != null) {
            Timestamp createLastTime=account.getCodecreatedate();
            if(createLastTime!=null) {
                long calculatetime = ChronoUnit.SECONDS.between(createLastTime.toLocalDateTime(), LocalDateTime.now());
                if (calculatetime < 60) {
                    responData.setSuccess(false);
                    responData.setExist(false);
                    responData.setDescription("Vui Long Cho 60s");
                    return responData;
                }
            }
            else{
                String code = emailService.generateResetCode(username);
                ResponData responDatasendemail = emailService.sendmailForgotPassword(username, code);
                responData.setSuccess(responDatasendemail.isSuccess());
                responData.setExist(responDatasendemail.isExist());
            }
        }
        else{
            responData.setSuccess(false);
            responData.setExist(false);
            responData.setDescription("Tai khoan khong ton tai");
        }
        return responData;
    }

    @Override
    public ResponData resetPassword(String username, String code, String newPassword) {
        ResponData responData = new ResponData();
        Account account = accountReponsitory.findByUsername(username);
        if(account != null) {
            ResponData isVerified = emailService.validateCode(username, code);
            if(isVerified.isSuccess()) {
                account.setPassword(passwordEncoder.encode(newPassword));
                accountReponsitory.save(account);
                responData.setSuccess(true);
                responData.setExist(false);
                responData.setDescription("Doi mat khau thanh cong");
                return responData;
            }
            return isVerified;
        }
        responData.setSuccess(false);
        responData.setExist(false);
        responData.setDescription("Tai Khoan khong ton tai");
        return responData;
    }

    @Override
    public ResponData changePassword(String username, String oldPassword, String newPassword) {
        ResponData responData = new ResponData();
       Account account= accountReponsitory.findByUsername(username);
       if(account != null) {
           if(passwordEncoder.matches(oldPassword, account.getPassword())) {
               account.setPassword(passwordEncoder.encode(newPassword));
               accountReponsitory.save(account);
               responData.setSuccess(true);
               responData.setExist(false);
               responData.setDescription("Doi mat khau thanh cong");
           }
       }
       responData.setSuccess(false);
       responData.setExist(false);
       return responData;
    }
}
