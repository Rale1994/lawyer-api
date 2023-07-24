package com.laywerapi.laywerapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.laywerapi.laywerapi.entity.Trial;
import lombok.Data;

import java.util.Date;

@Data
public class TrialAddResponseDTO {

    private String issue;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    private ClientResponseDTO client;

    public TrialAddResponseDTO(Trial trial, ClientResponseDTO clientResponseDTO) {
        this.issue = trial.getIssue();
        this.date = trial.getDate();
        this.client = clientResponseDTO;
    }
}
