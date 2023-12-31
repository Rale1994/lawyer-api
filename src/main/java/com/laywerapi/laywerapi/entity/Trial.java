package com.laywerapi.laywerapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laywerapi.laywerapi.dto.request.TrialAddRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity(name = "trials")
@Getter
@Setter
@NoArgsConstructor
@Audited
public class Trial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String issue;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User userId;
    @ManyToOne
    @JoinColumn(name = "clientId")
    @JsonIgnore
    private Client clientId;

    public Trial(TrialAddRequestDTO trialAddRequestDTO, User loggedUser, Client client) {
        this.issue = trialAddRequestDTO.getIssue();
        this.date = trialAddRequestDTO.getDate();
        this.clientId = client;
        this.userId = loggedUser;
    }
}
