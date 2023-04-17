package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.dto.response.MemberDTO;
import com.ansv.taskmanagement.dto.response.UserInfoDTO;

public interface UserInfoService {

    UserInfoDTO getUserInfo(String username);

}