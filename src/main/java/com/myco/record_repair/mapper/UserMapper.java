package com.myco.record_repair.mapper;

import org.springframework.stereotype.Component;

import com.myco.record_repair.entity.User;
import com.myco.record_repair.model.UserModel;

@Component
public class UserMapper {
	
	public User toUserEntity(UserModel userModel) {
		User user = new User();
		user.setElasticId(userModel.getId());
		user.setFirstName(userModel.getFirstName());
		user.setLastName(userModel.getLastName());
		user.setMobile(userModel.getMobile());
		user.setEmail(userModel.getEmail());
		user.setGender(userModel.getGender());
		user.setCreated(userModel.getCreated());
		user.setUpdate(userModel.getUpdate());
		return user;
	}
	
	public UserModel toUserModel(User user) {
		UserModel userModel = new UserModel();
		userModel.setId(user.getElasticId());
		userModel.setFirstName(user.getFirstName());
		userModel.setLastName(user.getLastName());
		userModel.setMobile(user.getMobile());
		userModel.setEmail(user.getEmail());
		userModel.setGender(user.getGender());
		userModel.setCreated(user.getCreated());
		userModel.setUpdate(user.getUpdate());
		return userModel;
	}

}
