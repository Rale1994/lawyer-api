package com.laywerapi.laywerapi.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class TrialAddRequestDTO {

    @NotNull(message = "Issue is required")
    private String issue;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

}
