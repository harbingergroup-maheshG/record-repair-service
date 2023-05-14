package com.myco.record_repair.service;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myco.record_repair.client.UserClient;
import com.myco.record_repair.elastic.repository.ElasticSearchQuery;
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
	private ElasticSearchQuery elasticsearchQuery;

	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	@Override
	public String storeBackUpRecord(UserModel userModel) {
		LOGGER.info(">>>> storeBackUpRecord() user model : {} ", userModel);
		StringBuilder response = new StringBuilder();
		User user = userMapper.toUserEntity(userModel);
		LOGGER.info(">>>> user Entity : {} ", user);
		repairRepository.save(user);
		LOGGER.info("Response received, scheduling job in 1 hour... ");
		executor.schedule(this::executeJob, 1, TimeUnit.HOURS);
		return response.append("SUCCESS").toString();
	}

	private void executeJob() {
		LOGGER.info(">>>> executeJob() Sync elastic search with mySql DB ");
		try {
			LOGGER.info(">>>> executeJob() fetching data to sync");
			List<User> users = repairRepository.findAll();
			if (!users.isEmpty()) {
				LOGGER.info(">>>> executeJob() data to sync : {} ", users);
				UserModel model;
				for (User user : users) {
					model = userMapper.toUserModel(user);
					LOGGER.info(">>>> executeJob() sync in progress... {} ", model);
					String response = elasticsearchQuery.createOrUpdateDocument(model);
					if (null != response) {
						LOGGER.info("<<<< executeJob() sync is successful : {} ", response);
						LOGGER.info("<<<< executeJob() deleting data from repair DB  ");
						repairRepository.deleteById(user.getId());
					}

				}
			}
			LOGGER.info("<<<< Data is in Sync between repair and elastic DB");
		} catch (Exception e) {
			LOGGER.error("Error while performing DB sync , exception : {} ", e.getMessage());
			executor.schedule(this::executeJob, 1, TimeUnit.HOURS);
		}
	}
}
