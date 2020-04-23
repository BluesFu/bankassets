package com.core.bankassets.controller;

import com.core.bankassets.entities.Teller;
import com.core.bankassets.service.LoginService;
import com.core.bankassets.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;


/**
 * @author fsy
 */
@Controller
@RequestMapping("dev-api/")
public class LoginController {
    @Resource
    private LoginService loginService;

    @PostMapping("login")
    @ResponseBody
    public Object login(@RequestParam("username")String username,
                        @RequestParam("password")String password
                 ){
        Teller teller=new Teller();
        teller.setName(username);
        teller.setPassword(password);
         if(loginService.login(teller)!=null){
            Result success=new Result("success");
            return success;
         }
        Result failure=new Result("false");
        return failure;
    }

}
