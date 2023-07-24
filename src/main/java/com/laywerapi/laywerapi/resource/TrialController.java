package com.laywerapi.laywerapi.resource;

import com.laywerapi.laywerapi.dto.request.TrialAddRequestDTO;
import com.laywerapi.laywerapi.dto.response.TrialAddResponseDTO;
import com.laywerapi.laywerapi.dto.response.TrialResponseDTO;
import com.laywerapi.laywerapi.entity.Trial;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;
import com.laywerapi.laywerapi.services.TrialService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/trials")
@RequiredArgsConstructor
@Api
public class TrialController {

    private final TrialService trialService;

    @PostMapping(path = "/addTrial/{clientId}")
    public TrialAddResponseDTO addTrial(@AuthenticationPrincipal UserRegistrationDetails loggedUser,
                                        @RequestBody TrialAddRequestDTO trialAddRequestDTO,
                                        @PathVariable Long clientId) {
        return trialService.addTrial(loggedUser, trialAddRequestDTO, clientId);
    }

    @GetMapping("/allTrials/{clientId}")
    public List<TrialResponseDTO> allTrialsClient(@AuthenticationPrincipal UserRegistrationDetails loggedUser,
                                                  @PathVariable Long clientId) {
        return trialService.getAllClientTrials(loggedUser, clientId);

    }
}
