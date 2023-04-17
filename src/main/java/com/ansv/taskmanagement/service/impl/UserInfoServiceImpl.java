package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.response.MemberDTO;
import com.ansv.taskmanagement.dto.response.PermissionDTO;
import com.ansv.taskmanagement.dto.response.UserInfoDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.Member;
import com.ansv.taskmanagement.model.Permission;
import com.ansv.taskmanagement.repository.MemberRepository;
import com.ansv.taskmanagement.repository.PermissionRepository;
import com.ansv.taskmanagement.service.MemberService;
import com.ansv.taskmanagement.service.UserInfoService;
import com.ansv.taskmanagement.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private static final BaseMapper<Member, MemberDTO> mapper = new BaseMapper<>(Member.class, MemberDTO.class);

    private static final BaseMapper<Permission, PermissionDTO> mapperSub = new BaseMapper<>(Permission.class, PermissionDTO.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public UserInfoDTO getUserInfo(String username) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        Optional<Member> member = memberRepository.findByUsername(username);
        List<Permission> permissions;
        if(member.isPresent()) {
            permissions = permissionRepository.findAllByRoleId(member.get().getRoleId());
            userInfoDTO.setPermissions(mapperSub.toDtoBean(permissions));
            for(Permission permission: permissions) {
                userInfoDTO.getMenu().add(permission.getParentCode());
            }
        }
        return userInfoDTO;
    }
}
