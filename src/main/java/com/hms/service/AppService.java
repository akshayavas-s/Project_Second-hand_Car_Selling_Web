package com.hms.service;

import com.hms.entity.AppUser;
import com.hms.exception.ResourceExistsException;
import com.hms.payload.AppUserDto;
import com.hms.payload.LoginDto;
import com.hms.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppService {

    @Autowired
    private AppUserRepository aur;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private OTPService otpService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    Bcrypt password encoder object is created

    public AppUserDto createUser(AppUserDto user) {
        Optional<AppUser> opUsername = aur.findByUsername(user.getUsername());
        Optional<AppUser> opEmail = aur.findByEmail(user.getEmail());

        if (opUsername.isPresent()) {
            throw new ResourceExistsException("Username is already taken");
        }

        if (opEmail.isPresent()) {
            throw new ResourceExistsException("Email is already registered");
        }

        AppUser users = convertDtoToEntity(user);
//        String encode = passwordEncoder.encode(users.getPassword());
//        users.setPassword(encode);

        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));//10 is the no. of rounds the encryption happens
        //2^10 (max=30)
        users.setPassword(hashpw);
        AppUser savedUser = aur.save(users);
        AppUserDto savedDto = convertEntityToDto(savedUser);
        return savedDto;
    }

    AppUserDto convertEntityToDto(
            AppUser savedUser
    ) {
        AppUserDto u = modelMapper.map(savedUser, AppUserDto.class);
        return u;
    }

    AppUser convertDtoToEntity(
            AppUserDto user
    ) {
        AppUser u = modelMapper.map(user, AppUser.class);
        return u;
    }

    public String verifyLogin(
            LoginDto dto
    ) {
        Optional<AppUser> opUser = aur.findByUsername(dto.getUsername());
        if (opUser.isPresent()) {
            AppUser user = opUser.get();
           // return BCrypt.checkpw(dto.getPassword(), user.getPassword());//compare password
            if(BCrypt.checkpw(dto.getPassword(), user.getPassword())){
                //generate token
                String token = jwtService.generateToken(user.getUsername());
                return token;
            }
        }
        return null;
    }

    public String loginWithOTP(String mobile) {
        Optional<AppUser> opMobile = aur.findByMobile(mobile);
        if(opMobile.isPresent()) {
            String otp = otpService.generateOTP(mobile);
            return otp;
        }
        return null;
    }

    public String validateOtp(
            String otp, String mobile
    ) {
        boolean status = otpService.validateOTP(otp, mobile);
        if (status) {
            Optional<AppUser> opUser = aur.findByMobile(mobile);
            if(opUser.isPresent()) {
                AppUser user = opUser.get();
                String token = jwtService.generateToken(user.getUsername());
                return token;
            }
        }
        return null;
    }
}
