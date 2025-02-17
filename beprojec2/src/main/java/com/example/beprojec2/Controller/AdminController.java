package com.example.beprojec2.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.beprojec2.DTO.UserDTO;
import com.example.beprojec2.ErrorDTO.UserErrorDTO;

import com.example.beprojec2.customException.FieldRequiredException;
import com.example.beprojec2.customException.InvalidAgeException;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

@RestController// Rest Controller bao gồm tất cả chức năng của controller
@RequestMapping("/admin")
public class AdminController {

    // #RULE 1
    // GET: lấy dữ liệu
    // POST: thêm dữ liệu
    // PUT /  PATCH : sữa dữ liệu
    // DELETE: xóa dữ liệu

    @GetMapping("/check")
    public boolean check(@RequestParam (value="name",required = false) int id ) { // RULE #2
        if(id==1){
        System.out.println("oke");
        return true;
        }
        else System.out.println("not oke");
        return false;
    }
    @GetMapping ("/ktra")
    public boolean ktra(@RequestParam (required = false) Map<String,String> user){
        for(String a: user.values()){
            System.out.println(a);
            if(a==null){
                return false;
            }
        }
        return true;
    }
    // muốn trả về JSON cho fe thì phải thêm ResponeBody( đã nằm trong RestController)
    @GetMapping("/kiemtra")
    public UserDTO userDTO (@RequestBody UserDTO user){
        UserDTO user1 = new UserDTO();
        user1.setId(user.getId());
        user1.setAddress(user.getAddress());
        user1.setEmail(user.getEmail());
        user1.setPhone(user.getPhone());
        user1.setPassword(user.getPassword());
        user1.setUsername(user.getUsername());
        return user1;
    }
    public void validate(UserDTO user) throws FieldRequiredException {
        if(user.getUsername()==null || user.getUsername().equals("")){
            throw new FieldRequiredException("name is empty");
        }
    }
    @PostMapping("/user")
    public Object user(@RequestBody UserDTO user){

        return user;
    }

}
