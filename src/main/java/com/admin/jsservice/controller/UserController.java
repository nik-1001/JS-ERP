package com.admin.jsservice.controller;


import com.admin.jsservice.constant.UrlConstant;
import com.admin.jsservice.dto.UserDTO;
import com.admin.jsservice.dto.UserLoginDTO;
import com.admin.jsservice.entity.User;
import com.admin.jsservice.exception.BadCredentialsException;
import com.admin.jsservice.exception.PasswordNotMatchException;
import com.admin.jsservice.response.UserDeleteResponse;
import com.admin.jsservice.response.UserResponse;
import com.admin.jsservice.response.UserViewResponse;
import com.admin.jsservice.service.IUserService;
import com.admin.jsservice.service.Impl.IUserServiceImpl;
import com.admin.jsservice.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    //private final Logger log = LogManager.getLogger(UserController.class);


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    IUserServiceImpl userService;

    @Autowired
    IUserService iUserService;


    //@RequestMapping(value = UrlConstant.URL_USER_LOGIN,method = RequestMethod.POST)
    @PostMapping(UrlConstant.URL_USER_LOGIN)
    public ResponseEntity<?> userLogin(@RequestBody UserLoginDTO userLoginDTO) throws Exception {

        log.info("Rest request to login");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(),
                    userLoginDTO.getPassword()));
        }catch(UsernameNotFoundException e){
            e.printStackTrace();
            throw new BadCredentialsException("bad credentials");
        }

        UserDetails userDetails=userService.loadUserByUsername(userLoginDTO.getEmail());
        String token= jwtUtil.generateToken(userDetails);
        log.info(token);


        return new ResponseEntity<>(new UserResponse(token,"Token generated successfully"),HttpStatus.OK);
    }

  /*  @PostMapping(UrlConstant.URL_USER_SAVE)
    public ResponseEntity<?>saveUser(@RequestBody User user){
        log.info("in save user");
        Long u=userService.save(user);
        String body="User '"+u+"' saved";

        return new ResponseEntity<>(body,HttpStatus.OK);
    }*/

    @PostMapping(UrlConstant.URL_USER_SAVE)
    public ResponseEntity<?>registerNewUserAccount(@RequestBody @Valid UserDTO userDTO) throws Exception {
        log.info("in registered new user");
        if(userDTO.getPassword()!=null && userDTO.getConfirmPassword()!=null) {
            if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                throw new PasswordNotMatchException();
            }
        }

       Long u= userService.save(userDTO);
        String body="User '"+u+"' saved";
        return new ResponseEntity<>(body,HttpStatus.OK);
    }

    @GetMapping("/welcome")
    public String welcome(){
        String text="Welcome";
        return text;
    }
    @GetMapping(UrlConstant.URL_USER_VIEW)
    public ResponseEntity<?>viewUser(){
        log.info("Rest request to view registered user");
        List<User> user= iUserService.viewUser();
        return new ResponseEntity<>(new UserViewResponse(user),HttpStatus.OK);
    }

    @PutMapping(UrlConstant.URL_USER_SEARCH)
    public ResponseEntity<?>searchUser(@RequestParam(required = false) String email,
                                       @RequestParam(required = false) String country,
                                       @RequestParam(required = false) String state,
                                       @RequestParam(required = false) String aadhaar,
                                       @RequestParam(required = false) String pan_card){
        log.info("Rest request to search user");
      List<User>user=iUserService.searchUser(email,country,state,aadhaar,pan_card);
     return new ResponseEntity<>(new UserViewResponse(user),HttpStatus.OK);
  }

    @PatchMapping(UrlConstant.URL_USER_UPDATE)
    public ResponseEntity<?>updateUser(@RequestParam String email,@RequestBody User user){
        log.info("rest request to update user");
        User u=iUserService.updateUser(email,user);
        return new ResponseEntity<>(u,HttpStatus.OK);
    }

    @DeleteMapping(UrlConstant.URL_USER_DELETE)
    public ResponseEntity<?>deleteUser(@RequestParam String email){
        log.info("rest request to delete user");
        iUserService.deleteUser(email);
        return new ResponseEntity<>(new UserDeleteResponse(email,"User deleted successfully"),
                HttpStatus.OK);
    }




}
