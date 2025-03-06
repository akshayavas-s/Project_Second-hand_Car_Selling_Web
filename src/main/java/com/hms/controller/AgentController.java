package com.hms.controller;

import com.hms.entity.evaluation.Agent;
import com.hms.entity.evaluation.Area;
import com.hms.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @PostMapping("/create")
    public ResponseEntity<Agent> createAgent(
            @RequestBody Agent agent
    ){
        Agent newAgent = agentService.createAgent(agent);
        return ResponseEntity.ok(newAgent);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Agent>> getAllAgents(Pageable pageable) {
        return ResponseEntity.ok(agentService.getAllAgents(pageable));
    }

    @PostMapping("/createArea")
    public ResponseEntity<?> createArea(@RequestParam int pinCode, @RequestParam Long agentId) {
        Area createdArea = agentService.createArea(pinCode, agentId);
        if (createdArea == null){
            return ResponseEntity.badRequest().body("Agent not found");
        }
        return ResponseEntity.ok(createdArea);
    }

    @GetMapping("/getArea")
    public ResponseEntity<Page<Area>> getAllAreas(Pageable pageable) {
        return ResponseEntity.ok(agentService.getAllAreas(pageable));
    }
}
