package com.laywerapi.laywerapi.services;

import com.laywerapi.laywerapi.dto.request.TrialAddRequestDTO;
import com.laywerapi.laywerapi.dto.response.TrialAddResponseDTO;
import com.laywerapi.laywerapi.dto.response.TrialResponseDTO;
import com.laywerapi.laywerapi.entity.Trial;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;

import java.util.List;

public interface TrialService {
    TrialAddResponseDTO addTrial(UserRegistrationDetails loggedUser, TrialAddRequestDTO trialAddRequestDTO, Long clientId);

    List<TrialResponseDTO> getAllClientTrials(UserRegistrationDetails loggedUser, Long clientId);
}
