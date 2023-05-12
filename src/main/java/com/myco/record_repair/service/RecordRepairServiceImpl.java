package com.myco.record_repair.service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myco.record_repair.client.UserClient;
import com.myco.record_repair.entity.User;
import com.myco.record_repair.mapper.UserMapper;
import com.myco.record_repair.model.UserModel;
import com.myco.record_repair.repository.RecordRepairRepository;

@Service
public class RecordRepairServiceImpl implements RecordRepairService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordRepairServiceImpl.class);
	
	@Autowired
	UserMapper userMapper;

	@Autowired
	private RecordRepairRepository repairRepository;
	
	@Autowired
	private UserClient userClient;
	
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	
	@Override
	public String storeBackUpRecord(UserModel userModel) {
		LOGGER.info(">>>> storeBackUpRecord() user model : {} ", userModel );
		StringBuilder response = new StringBuilder();
		User user = userMapper.toUserEntity(userModel);
		LOGGER.info(">>>> user Entity : {} ", user);
		repairRepository.save(user);
		return response.append("SUCCESS").toString();
	}

	private void callScheduler(User storedUser) {
		LOGGER.info("Response received, scheduling job in 1 hour...");
        executor.schedule(this::executeJob, 1, TimeUnit.HOURS);
	}
	
	private void executeJob() {
	}

}
