package com.hms.controller;

import com.hms.payload.AppUserDto;
import com.hms.payload.LoginDto;
import com.hms.payload.TokenDto;
import com.hms.service.AppService;
import com.hms.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private AppService appService;

    @Autowired
    private OTPService otpService;

    //http://localhost:8080/api/v1/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(
            @RequestBody AppUserDto user
    ){
        user.setRole("ROLE_USER");
        AppUserDto use = appService.createUser(user);
        return new ResponseEntity<>(use, HttpStatus.CREATED);
    }

    @PostMapping("/content-manager-signup")
    public ResponseEntity<?> createContentManagerAccount(
            @RequestBody AppUserDto user
    ){
        user.setRole("ROLE_CONTENT-MANAGER");
        AppUserDto use = appService.createUser(user);
        return new ResponseEntity<>(use, HttpStatus.CREATED);
    }

    @PostMapping("/blog-manager-signup")
    public ResponseEntity<?> createBlogManagerAccount(
            @RequestBody AppUserDto user
    ){
        user.setRole("ROLE_BLOG-MANAGER");
        AppUserDto use = appService.createUser(user);
        return new ResponseEntity<>(use, HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(
            @RequestBody LoginDto dto
    ){
        String token = appService.verifyLogin(dto);
        if(token!=null){
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid Username/Password", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login-otp")
    public ResponseEntity<?> generateOTP(
            @RequestParam String mobile
    ){
        String otp = appService.loginWithOTP(mobile);
        if(otp != null){
            return new ResponseEntity<>(otp+" "+mobile, HttpStatus.OK);
        }
        return new ResponseEntity<>("Error: Mobile number not found", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOtp(
            @RequestParam String otp,
            @RequestParam String mobile
    ){
        String token = appService.validateOtp(otp, mobile);
        if(token != null){
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid otp", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
