package com.hms.repository;

import com.hms.entity.evaluation.Agent;
import com.hms.entity.evaluation.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByEmailId(String email);
}