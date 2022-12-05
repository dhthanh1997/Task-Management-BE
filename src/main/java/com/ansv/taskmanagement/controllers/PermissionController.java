package com.ansv.taskmanagement.controllers;


import com.ansv.taskmanagement.dto.response.PermissionDTO;
import com.ansv.taskmanagement.dto.response.ResponseDataObject;
import com.ansv.taskmanagement.service.PermissionService;
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
@RequestMapping("/api/permission")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("")
    public ResponseEntity<ResponseDataObject<PermissionDTO>> searchByCriteria(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "search") Optional<String> search) {
        ResponseDataObject<PermissionDTO> response = new ResponseDataObject<>();
        Pageable page = pageRequest(new ArrayList<>(), pageNumber - 1, pageSize);
        Page<PermissionDTO> listDTO = permissionService.findBySearchCriteria(search, page);
        // response
        response.pagingData = listDTO;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDataObject<PermissionDTO>> create(@RequestBody @Valid PermissionDTO item) {
        ResponseDataObject<PermissionDTO> response = new ResponseDataObject<>();
        PermissionDTO dto = permissionService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataObject<PermissionDTO>> update(@RequestBody @Valid PermissionDTO item) {
        ResponseDataObject<PermissionDTO> response = new ResponseDataObject<>();
        PermissionDTO dto = permissionService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataObject<PermissionDTO>> getById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<PermissionDTO> response = new ResponseDataObject<>();
        PermissionDTO dto = permissionService.findById(id);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDataObject<Integer>> deleteById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        permissionService.deleteById(id);
        response.initData(1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteByListId")
    public ResponseEntity<ResponseDataObject<Integer>> deleteByListId(@RequestBody List<Long> listId) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        Integer delete = permissionService.deleteByListId(listId);
        response.initData(delete);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
