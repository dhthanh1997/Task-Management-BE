package com.ansv.taskmanagement.controllers;


import com.ansv.taskmanagement.dto.response.RoleOfProjectDTO;
import com.ansv.taskmanagement.dto.response.ResponseDataObject;
import com.ansv.taskmanagement.service.RoleOfProjectService;
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
@RequestMapping("/api/roleOfProject")
public class RoleOfProjectController extends BaseController {

    @Autowired
    private RoleOfProjectService roleOfProjectService;

    @GetMapping("")
    public ResponseEntity<ResponseDataObject<RoleOfProjectDTO>> searchByCriteria(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "search") Optional<String> search) {
        ResponseDataObject<RoleOfProjectDTO> response = new ResponseDataObject<>();
        Pageable page = pageRequest(new ArrayList<>(), pageNumber - 1, pageSize);
        Page<RoleOfProjectDTO> listDTO = roleOfProjectService.findBySearchCriteria(search, page);
        // response
        response.pagingData = listDTO;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDataObject<RoleOfProjectDTO>> create(@RequestBody @Valid RoleOfProjectDTO item) {
        ResponseDataObject<RoleOfProjectDTO> response = new ResponseDataObject<>();
        RoleOfProjectDTO dto = roleOfProjectService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataObject<RoleOfProjectDTO>> update(@RequestBody @Valid RoleOfProjectDTO item) {
        ResponseDataObject<RoleOfProjectDTO> response = new ResponseDataObject<>();
        RoleOfProjectDTO dto = roleOfProjectService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataObject<RoleOfProjectDTO>> getById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<RoleOfProjectDTO> response = new ResponseDataObject<>();
        RoleOfProjectDTO dto = roleOfProjectService.findById(id);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDataObject<Integer>> deleteById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        roleOfProjectService.deleteById(id);
        response.initData(1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteByListId")
    public ResponseEntity<ResponseDataObject<Integer>> deleteByListId(@RequestBody List<Long> listId) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        Integer delete = roleOfProjectService.deleteByListId(listId);
        response.initData(delete);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
