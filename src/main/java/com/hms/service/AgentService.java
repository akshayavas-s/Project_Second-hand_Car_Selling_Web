package com.hms.service;

import com.hms.entity.evaluation.Agent;
import com.hms.entity.evaluation.Area;
import com.hms.exception.ResourceExistsException;
import com.hms.repository.AgentRepository;
import com.hms.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.Optional;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private AreaRepository areaRepository;

    public Agent createAgent(Agent agent) {
        Optional<Agent> opUser = agentRepository.findByEmailId(agent.getEmailId());
        if (opUser.isPresent()) {
            throw new ResourceExistsException("Email already exists!");
        }
        return agentRepository.save(agent);
    }

    public Page<Agent> getAllAgents(Pageable pageable) {
        return agentRepository.findAll(pageable);
    }

    public Area createArea(int pinCode, Long agentId) {
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent with ID " + agentId + " not found!"));
        Area area = new Area();
        area.setPinCode(pinCode);
        area.setAgent(agent);
        return areaRepository.save(area);
    }

    public Page<Area> getAllAreas(Pageable pageable) {
        return areaRepository.findAll(pageable);
    }
}
