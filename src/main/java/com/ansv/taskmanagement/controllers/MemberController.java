package com.ansv.taskmanagement.controllers;


import com.ansv.taskmanagement.dto.response.MemberDTO;
import com.ansv.taskmanagement.dto.response.ResponseDataObject;
import com.ansv.taskmanagement.dto.response.UserDTO;
import com.ansv.taskmanagement.service.MemberService;
import com.ansv.taskmanagement.service.rabbitmq.RabbitMqReceiver;
import com.ansv.taskmanagement.service.rabbitmq.RabbitMqSender;
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
@RequestMapping("taskManagement/api/member")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RabbitMqReceiver rabbitMqReceiver;

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @GetMapping("")
    public ResponseEntity<ResponseDataObject<MemberDTO>> searchByCriteria(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "search") Optional<String> search) {
        ResponseDataObject<MemberDTO> response = new ResponseDataObject<>();
        Pageable page = pageRequest(new ArrayList<>(), pageNumber - 1, pageSize);
        Page<MemberDTO> listDTO = memberService.findBySearchCriteria(search, page);
        // response
        response.pagingData = listDTO;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDataObject<MemberDTO>> create(@RequestBody @Valid MemberDTO item) {
        ResponseDataObject<MemberDTO> response = new ResponseDataObject<>();
        MemberDTO dto = memberService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataObject<MemberDTO>> update(@RequestBody @Valid MemberDTO item) {
        ResponseDataObject<MemberDTO> response = new ResponseDataObject<>();
        MemberDTO dto = memberService.save(item);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataObject<MemberDTO>> getById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<MemberDTO> response = new ResponseDataObject<>();
        MemberDTO dto = memberService.findById(id);
        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/rabbitUser")
    public ResponseEntity<ResponseDataObject<UserDTO>> getRabbitUser(@RequestParam(name = "username") String username) {
        ResponseDataObject<UserDTO> response = new ResponseDataObject<>();
        UserDTO message = new UserDTO();
        message.setUsername(username);
        rabbitMqSender.sender(message);
//        rabbitMqReceiver.receivedMessage();
//        response.initData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDataObject<Integer>> deleteById(@PathVariable(value = "id") Long id) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        memberService.deleteById(id);
        response.initData(1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteByListId")
    public ResponseEntity<ResponseDataObject<Integer>> deleteByListId(@RequestBody List<Long> listId) {
        ResponseDataObject<Integer> response = new ResponseDataObject<>();
        Integer delete = memberService.deleteByListId(listId);
        response.initData(delete);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
