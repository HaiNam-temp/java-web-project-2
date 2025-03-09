package com.example.beprojec2.Controller;

import com.example.beprojec2.Payload.Request.SignUpRequest;
import com.example.beprojec2.Payload.ResponData;
import com.example.beprojec2.Service.Imp.AccountServiceImp;
import com.example.beprojec2.Service.Imp.LoginServiceImp;
import com.example.beprojec2.util.JwtUtilHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    LoginServiceImp loginServiceimp;
    @Autowired
    JwtUtilHelper jwtUtilHelper;
    @GetMapping("/signin")
    public ResponData signin(@RequestParam String username, @RequestParam String password) {
        ResponData responData = loginServiceimp.checkLogin(username, password);
        if(responData.isSuccess()) {
            responData.setData(jwtUtilHelper.generateToken(username));
            return responData;
        }
        else return responData;
    }
    @PostMapping("/signup")
    public ResponData addAccount(@RequestBody SignUpRequest signUpRequest) {
        return loginServiceimp.addAccount(signUpRequest);
    }
}
