package com.demo.camunda.repository;

import com.demo.camunda.model.BpmnFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BpmnFileRepository extends JpaRepository<BpmnFile, Long> {
}
