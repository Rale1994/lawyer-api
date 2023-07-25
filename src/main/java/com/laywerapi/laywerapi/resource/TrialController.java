package com.laywerapi.laywerapi.resource;

import com.laywerapi.laywerapi.dto.request.TrialAddRequestDTO;
import com.laywerapi.laywerapi.dto.response.TrialAddResponseDTO;
import com.laywerapi.laywerapi.dto.response.TrialResponseDTO;
import com.laywerapi.laywerapi.entity.Trial;
import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;
import com.laywerapi.laywerapi.events.TrialEmailNotificationEvent;
import com.laywerapi.laywerapi.repositories.TrialRepository;
import com.laywerapi.laywerapi.services.TrialService;
import com.laywerapi.laywerapi.shared.Constants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(Constants.BASE_URL + "/trials")
@RequiredArgsConstructor
@Api
@Slf4j
public class TrialController {

    private final TrialService trialService;
    private final ApplicationEventPublisher publisher;
    private final TrialRepository trialRepository;

    @PostMapping(path = "/addTrial/{clientId}")
    public TrialAddResponseDTO addTrial(@AuthenticationPrincipal UserRegistrationDetails loggedUser,
                                        @Valid @RequestBody TrialAddRequestDTO trialAddRequestDTO,
                                        @PathVariable Long clientId) {
        return trialService.addTrial(loggedUser, trialAddRequestDTO, clientId);
    }

    @GetMapping("/allTrials/{clientId}")
    public List<TrialResponseDTO> allTrialsClient(@AuthenticationPrincipal UserRegistrationDetails loggedUser,
                                                  @PathVariable Long clientId) {
        return trialService.getAllClientTrials(loggedUser, clientId);
    }

    @Scheduled(fixedDelay = 60000)
    void trialMailNotification() {
        List<Trial> trials = trialService.findUserTrials();
        if (!trials.isEmpty()) {
            for (Trial trial : trials) {
                User user = trial.getUserId();
                publisher.publishEvent(new TrialEmailNotificationEvent(user));
            }
        }
    }
}
