package com.labproject.SkyTracker.OpenSky;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@JsonIgnoreProperties
public class SkyResponseObject {

    private String[][] states;

    public String[][] getStates() {
        return states;
    }

    public void setStates(String[][] states) {
        this.states = states;
    }

}
