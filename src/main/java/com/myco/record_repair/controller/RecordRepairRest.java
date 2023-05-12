package com.myco.record_repair.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myco.record_repair.model.UserModel;
import com.myco.record_repair.service.RecordRepairService;

@RestController
@RequestMapping(value = "/")
public class RecordRepairRest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordRepairRest.class);
	
	@Autowired
	private RecordRepairService recordRepairService;
	
	@PostMapping("/repair")
	public ResponseEntity<String> storeForRepair(@RequestBody UserModel userModel) {
		LOGGER.info(">>>> storeForRepair() userModel : {} " , userModel);
		String response = recordRepairService.storeBackUpRecord(userModel);
		if(response.contains("SUCCESS")) {
			LOGGER.info("<<<< storeForRepair() RESPONSE : {} " , response);
			return new ResponseEntity<>("Record Backup Successful", HttpStatus.OK);
		}
		return new ResponseEntity<>("Service Unavailable",HttpStatus.BAD_GATEWAY);
	}
	
	

}
