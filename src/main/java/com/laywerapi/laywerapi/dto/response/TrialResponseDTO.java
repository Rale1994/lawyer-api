package com.laywerapi.laywerapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.laywerapi.laywerapi.entity.Trial;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TrialResponseDTO {
    private String issue;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
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
