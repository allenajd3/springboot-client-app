package com.springboot.client.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.client.dto.Message;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AppController {

	@GetMapping("/list")
	ResponseEntity<List<Message>> list(){
		return ResponseEntity.ok().body(Collections.singletonList(new Message("Test list"))); 
	}
	
	@PostMapping("/create")
	ResponseEntity<Message> create(@RequestBody Message message){
		log.info("Mensaje guardado: " + message.getText());
		return ResponseEntity.ok().body(message);
	}
	
	
	@GetMapping("/authorized")
	public Map<String,String> authorized(@RequestParam String code){
		return Collections.singletonMap("code",code);
	}
}
