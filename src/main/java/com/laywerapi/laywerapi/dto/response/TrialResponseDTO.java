package com.laywerapi.laywerapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.laywerapi.laywerapi.entity.Trial;
import lombok.Data;

import java.util.Date;

@Data
public class TrialResponseDTO {
    private String issue;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    private String clientFirstName;
    private String clientLastName;
    private String clientPhone;

    public TrialResponseDTO(Trial trial) {
        this.issue = trial.getIssue();
        this.date = trial.getDate();
        this.clientFirstName = trial.getClientId().getFirstName();
        this.clientLastName = trial.getClientId().getLastName();
        this.clientPhone = trial.getClientId().getPhone();
    }

}
