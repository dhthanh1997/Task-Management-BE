package com.ansv.taskmanagement.controllers;


import com.ansv.taskmanagement.dto.response.MemberDTO;
import com.ansv.taskmanagement.dto.response.ResponseDataObject;
import com.ansv.taskmanagement.dto.response.UserDTO;
import com.ansv.taskmanagement.dto.response.UserInfoDTO;
import com.ansv.taskmanagement.service.MemberService;
import com.ansv.taskmanagement.service.RoleOfApplicationService;
import com.ansv.taskmanagement.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/userInfo")
public class UserController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;


    @GetMapping("/{username}")
    public ResponseEntity<ResponseDataObject<UserInfoDTO>> getByUserInfo(@PathVariable(value = "username") String username) {
        ResponseDataObject<UserInfoDTO> response = new ResponseDataObject<>();
        UserInfoDTO dto = userInfoService.getUserInfo(username);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
