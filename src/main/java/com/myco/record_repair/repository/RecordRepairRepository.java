package com.myco.record_repair.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myco.record_repair.entity.User;

@Repository
public interface RecordRepairRepository extends JpaRepository<User, Integer> {

}
