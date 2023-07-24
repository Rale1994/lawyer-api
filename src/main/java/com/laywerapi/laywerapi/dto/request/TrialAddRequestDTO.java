package com.laywerapi.laywerapi.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class TrialAddRequestDTO {

    @NotNull(message = "Issue is required")
    private String issue;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;

}
