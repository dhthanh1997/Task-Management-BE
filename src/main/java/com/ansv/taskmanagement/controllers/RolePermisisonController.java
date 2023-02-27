package com.ansv.taskmanagement.controllers;


import com.ansv.taskmanagement.dto.response.RolePermissionDTO;
import com.ansv.taskmanagement.dto.response.ResponseDataObject;
import com.ansv.taskmanagement.service.RolePermissionService;
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
@RequestMapping("/api/rolePermission")
public class RolePermisisonController extends BaseController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping("")
    public ResponseEntity<ResponseDataObject<RolePermissionDTO>> searchByCriteria(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "search") Optional<String> search) {
        ResponseDataObject<RolePermissionDTO> response = new ResponseDataObject<>();
        Pageable page = pageRequest(new ArrayList<>(), pageNumber - 1, pageSize);
        Page<RolePermissionDTO> listDTO = rolePermissionService.findBySearchCriteria(search, page);
        // response
        response.pagingData = listDTO;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDataObject<RolePermissionDTO>> create(@RequestBody @Valid RolePermissionDTO item) {
        ResponseDataObject<RolePermissionDTO> response = new ResponseDataObject<>();
        RolePermissionDTO dto = rolePermissionService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/rolePermission")
    public ResponseEntity<ResponseDataObject<List<RolePermissionDTO>>> createRolePermission(@RequestBody List<RolePermissionDTO> item) {
        ResponseDataObject<List<RolePermissionDTO>> response = new ResponseDataObject<>();
        List<RolePermissionDTO>  dto = rolePermissionService.saveRolePermission(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataObject<RolePermissionDTO>> update(@PathVariable(value = "id") Long id, @RequestBody @Valid RolePermissionDTO item) {
        ResponseDataObject<RolePermissionDTO> response = new ResponseDataObject<>();
        RolePermissionDTO dto = rolePermissionService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataObject<RolePermissionDTO>> getById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<RolePermissionDTO> response = new ResponseDataObject<>();
        RolePermissionDTO dto = rolePermissionService.findById(id);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDataObject<Integer>> deleteById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        rolePermissionService.deleteById(id);
        response.initData(1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteByListId")
    public ResponseEntity<ResponseDataObject<Integer>> deleteByListId(@RequestBody List<Long> listId) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        Integer delete = rolePermissionService.deleteByListId(listId);
        response.initData(delete);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
