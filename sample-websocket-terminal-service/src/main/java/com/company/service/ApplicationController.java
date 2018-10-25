package com.company.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.company.model.TerminalMessage;
import com.company.model.TerminalResponse;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.model.VariableDesc;
import org.kie.server.springboot.jbpm.ContainerAliasResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    @Autowired
    ProcessService processService;

    @Autowired
    RuntimeDataService dataService;

    @Autowired
    private ContainerAliasResolver aliasResolver;

    private String containerAlias = "sample-websocket-terminal-kjar";
    private String processId = "terminalprocess";

    @GetMapping("/")
    public String getTerminal(Model model) {
        return "terminal";
    }

    @MessageMapping("/terminalmessage")
    @SendTo("/topic/terminal")
    public TerminalResponse terminalResponse(TerminalMessage message) throws Exception {
        return new TerminalResponse(startTerminalProcess(message.getMessage()));
    }

    private String startTerminalProcess(String message) {
        System.out.println("******** starting process with message : " + message);
        Map<String, Object> processInputs = new HashMap<>();
        processInputs.put("cMessage",
                          message);
        long pid = processService.startProcess(aliasResolver.latest(containerAlias), processId, processInputs);
        Collection<VariableDesc> pvars = dataService.getVariablesCurrentState(pid);
        for(VariableDesc var : pvars) {
            if(var.getVariableId().equals("cOutput")) {
                return var.getNewValue();
            }
        }
        return "no results for command";
    }
}
