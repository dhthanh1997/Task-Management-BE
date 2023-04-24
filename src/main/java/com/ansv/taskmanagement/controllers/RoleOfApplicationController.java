package com.ansv.taskmanagement.controllers;


import com.ansv.taskmanagement.dto.response.RoleOfApplicationDTO;
import com.ansv.taskmanagement.dto.response.ResponseDataObject;
import com.ansv.taskmanagement.dto.response.RolePermissionDTO;
import com.ansv.taskmanagement.service.PermissionService;
import com.ansv.taskmanagement.service.RoleOfApplicationService;
import com.ansv.taskmanagement.service.RolePermissionService;
import com.ansv.taskmanagement.util.TreeComponent;
//import org.checkerframework.checker.units.qual.A;
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
@RequestMapping("/api/roleOfApplication")
public class RoleOfApplicationController extends BaseController {

    @Autowired
    private RoleOfApplicationService roleOfApplicationService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping("")
    public ResponseEntity<ResponseDataObject<RoleOfApplicationDTO>> searchByCriteria(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "search") Optional<String> search) {
        ResponseDataObject<RoleOfApplicationDTO> response = new ResponseDataObject<>();
        Pageable page = pageRequest(new ArrayList<>(), pageNumber - 1, pageSize);
        Page<RoleOfApplicationDTO> listDTO = roleOfApplicationService.findBySearchCriteria(search, page);
        // response
        response.pagingData = listDTO;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDataObject<RoleOfApplicationDTO>> create(@RequestBody @Valid RoleOfApplicationDTO item) {
        ResponseDataObject<RoleOfApplicationDTO> response = new ResponseDataObject<>();
        RoleOfApplicationDTO dto = roleOfApplicationService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataObject<RoleOfApplicationDTO>> update(@PathVariable(value = "id") Long id, @RequestBody @Valid RoleOfApplicationDTO item) {
        ResponseDataObject<RoleOfApplicationDTO> response = new ResponseDataObject<>();
        RoleOfApplicationDTO dto = roleOfApplicationService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataObject<RoleOfApplicationDTO>> getById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<RoleOfApplicationDTO> response = new ResponseDataObject<>();
        RoleOfApplicationDTO dto = roleOfApplicationService.findById(id);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/rolePermission")
    public ResponseEntity<ResponseDataObject<TreeComponent>> getRolePermission(@RequestParam(name = "roleId") Optional<Long> roleId) {
        ResponseDataObject<TreeComponent> response = new ResponseDataObject<>();
        List<TreeComponent> dto = roleOfApplicationService.getRolePermission(roleId);
        response.initListData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDataObject<Integer>> deleteById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        roleOfApplicationService.deleteById(id);
        rolePermissionService.deleteByRoleId(id);
        response.initData(1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteByListId")
    public ResponseEntity<ResponseDataObject<Integer>> deleteByListId(@RequestBody List<Long> listId) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        Integer delete = roleOfApplicationService.deleteByListId(listId);
        for(Long id: listId) {
            rolePermissionService.deleteByRoleId(id);
        }
        response.initData(delete);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
