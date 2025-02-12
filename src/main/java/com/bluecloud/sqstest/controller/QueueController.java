package com.bluecloud.sqstest.controller;

import com.bluecloud.sqstest.dto.MessageDto;
import com.bluecloud.sqstest.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    public MessageService messageService;

    @PostMapping
    public ResponseEntity<String> createQueue( @RequestBody  MessageDto messageDto) {

        messageService.sendMessageToNormalPriorityQueue(messageDto);

        return ResponseEntity.ok("Message send");

    }


    @PostMapping("/high-priority")
    public ResponseEntity<String> highPriority( @RequestBody  MessageDto messageDto) {

        messageService.sendMessageToHighPriorityQueue(messageDto);

        return ResponseEntity.ok("Message send");

    }
}
