package com.company.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class CommandDecision implements Serializable {

    private boolean weatherCommand;

    private List<String> dangerousCommands = Arrays.asList("del",
                                                           "delete",
                                                           "rm",
                                                           "rm -rf",
                                                           "mkdir",
                                                           "shutdown",
                                                           "reboot");

    public boolean isWeatherCommand() {
        return weatherCommand;
    }

    public void setWeatherCommand(boolean weatherCommand) {
        this.weatherCommand = weatherCommand;
    }

    public boolean isDangerounsCommand(String command) {
        boolean danger = false;
        for(String dc : dangerousCommands) {
            if(command.startsWith(dc)) {
                return true;
            }
        }

        return false;
    }
}
