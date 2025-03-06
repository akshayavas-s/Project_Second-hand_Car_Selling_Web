package com.hms.controller;

import com.hms.entity.evaluation.Area;
import com.hms.entity.evaluation.CustomerVisit;
import com.hms.service.CRMService;
import com.hms.service.TwilioService;
import com.hms.service.WhatsAppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crm")
public class CRMController {

    private CRMService crmService;
    private TwilioService twilioService;
    private WhatsAppService wapService;

    public CRMController(CRMService crmService, TwilioService twilioService, WhatsAppService wapService) {
        this.crmService = crmService;
        this.twilioService = twilioService;
        this.wapService = wapService;
    }

//    /api/v1/crm/getAgents
    @PostMapping("/getAgents")
    public ResponseEntity<?> searchAgent(
            @RequestParam int pinCode
    ){
        List<Area> agents = crmService.searchAgent(pinCode);
        if (agents.isEmpty()){
            return new ResponseEntity<>("No agents found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    @PostMapping("/creaateCustomer")
    public ResponseEntity<CustomerVisit> createCustomer(
            @RequestBody CustomerVisit details
            ){
        CustomerVisit user = crmService.createUser(details);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/allocateAgent")
    public ResponseEntity<?> allocateAgent(
            @RequestParam long customerId
//            @RequestParam long customerId, @RequestParam long agentId
    ){
        CustomerVisit customerVisit = crmService.allocateAgent(customerId);
        if (customerVisit == null){
            return new ResponseEntity<>("Agent not found", HttpStatus.NOT_FOUND);
        }
        twilioService.sendSms("+919999999999", "SMS is Sent");
        wapService.sendWhatsAppMessage("+919999999999", "Whatsapp Message Sent");
        return new ResponseEntity<>(customerVisit, HttpStatus.OK);
    }
}
