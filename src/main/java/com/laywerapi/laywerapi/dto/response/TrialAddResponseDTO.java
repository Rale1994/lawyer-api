package com.laywerapi.laywerapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.laywerapi.laywerapi.entity.Trial;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TrialAddResponseDTO {

    private String issue;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
    private ClientResponseDTO client;

    public TrialAddResponseDTO(Trial trial, ClientResponseDTO clientResponseDTO) {
        this.issue = trial.getIssue();
        this.date = trial.getDate();
        this.client = clientResponseDTO;
    }
}
