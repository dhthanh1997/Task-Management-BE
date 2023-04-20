package com.ansv.taskmanagement.service;

import com.ansv.taskmanagement.constants.StateEnum;
import com.ansv.taskmanagement.dto.response.MemberDTO;
import com.ansv.taskmanagement.dto.response.PermissionDTO;
import com.ansv.taskmanagement.dto.response.TaskDTO;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.Member;
import com.ansv.taskmanagement.model.Permission;
import com.ansv.taskmanagement.model.RolePermission;
import com.ansv.taskmanagement.model.Task;
import com.ansv.taskmanagement.repository.MemberRepository;
import com.ansv.taskmanagement.repository.PermissionRepository;
import com.ansv.taskmanagement.service.impl.MemberServiceImpl;
import com.ansv.taskmanagement.service.impl.PermissionServiceImpl;
import com.ansv.taskmanagement.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserInfoServiceTest {

    @Mock
    private Member member;

    @Mock
    private MemberDTO memberDTO;

    @Mock
    private Permission permission;

    @Mock
    private PermissionDTO permissionDTO;

    @Mock
    private List<Member> members;

    @Mock
    private List<Permission> permissions;

    @Mock
    private List<RolePermission> rolePermissions;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @Mock
    private BaseMapper<Member, MemberDTO> mapper = new BaseMapper<>(Member.class, MemberDTO.class);

    @Mock
    private BaseMapper<Permission, PermissionDTO> mapperSub = new BaseMapper<>(Permission.class, PermissionDTO.class);

    private void dataSourceMember() {
        for (int i = 1; i <= 10; i++) {
            Member entity = Member.builder()
                    .id(DataUtils.parseToLong(i))
                    .name("member " + i)
                    .email("member" + i + "@gmail.com")
                    .roleId(1L)
                    .username("member" + i)
                    .build();
            this.members.add(entity);
        }

    }

    private void dataSourcePermission() {
        for (int i = 1; i <= 10; i++) {
            Permission entity = Permission.builder()
                    .id(DataUtils.parseToLong(i))
                    .name("permission " + i)
                    .code("permission" + i)
                    .build();
            this.permissions.add(entity);
        }

    }

    private void dataSourceRolePermission() {
        for (int i = 1; i <= 10; i++) {
            RolePermission entity = RolePermission.builder()
                    .id(DataUtils.parseToLong(i))
                    .permissionId(DataUtils.parseToLong(i))
                    .roleId(1L).build();
            this.rolePermissions.add(entity);
        }

    }



    @BeforeEach
    void setUp() {
        this.dataSourceMember();
        this.dataSourcePermission();
        this.dataSourceRolePermission();
    }

    @Test
    void getUserInfo() {
    }
}