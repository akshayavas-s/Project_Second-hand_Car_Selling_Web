package com.hms.service;

import com.hms.entity.evaluation.Agent;
import com.hms.entity.evaluation.Area;
import com.hms.entity.evaluation.CustomerVisit;
import com.hms.exception.ResourceExistsException;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.AgentRepository;
import com.hms.repository.AreaRepository;
import com.hms.repository.CustomerVisitRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CRMService {

    private AreaRepository areaRepository;
    private CustomerVisitRepository customerRepository;
    private AgentRepository agentRepository;

    public CRMService(AreaRepository areaRepository, CustomerVisitRepository customerRepository, AgentRepository agentRepository) {
        this.areaRepository = areaRepository;
        this.customerRepository = customerRepository;
        this.agentRepository = agentRepository;
    }

    public List<Area> searchAgent(int pinCode) {
        List<Area> agents = areaRepository.searchByPinCode(pinCode);
        return agents;
    }

    public CustomerVisit createUser(CustomerVisit details) {
        Optional<CustomerVisit> opUser = customerRepository.findByMobile(details.getMobile());
        if(opUser.isPresent()){
            throw new ResourceExistsException("Customer Credentials already exists");
        }
        CustomerVisit save = customerRepository.save(details);
        return save;
    }

    public CustomerVisit allocateAgent(
            long customerId
//            long customerId, long agentId
    ){
        CustomerVisit customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        List<Area> areas = areaRepository.searchByPinCode(customer.getPinCode());
        if (areas.isEmpty()) {
            throw new ResourceNotFoundException("No areas found for this pin code");
        }

        List<Agent> agents = areas.stream().map(Area::getAgent).distinct().toList();
        if (agents.isEmpty()) {
            throw new ResourceNotFoundException("No agents available for this pin code");
        }

        Agent assignedAgent = agents.stream()
                .min(Comparator.comparingInt(agent -> customerRepository.countByAgent(agent)))
                .orElseThrow(() -> new ResourceNotFoundException("No available agents"));

        customer.setAgent(assignedAgent);

//        Agent agent = agentRepository.findById(agentId)
//                .orElseThrow(() -> new ResourceNotFoundException("Agent not found"));
//        CustomerVisit customer = customerRepository.findById(customerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
//        customer.setAgent(agent);

        return customerRepository.save(customer);
    }
}
