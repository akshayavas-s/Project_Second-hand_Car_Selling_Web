package com.hms.repository;

import com.hms.entity.evaluation.Agent;
import com.hms.entity.evaluation.CustomerVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerVisitRepository extends JpaRepository<CustomerVisit, Long> {
    Optional<CustomerVisit> findByMobile(String mobile);

    @Query("SELECT COUNT(c) FROM CustomerVisit c WHERE c.agent = :agent")
    int countByAgent(@Param("agent") Agent agent);
}