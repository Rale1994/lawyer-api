package com.laywerapi.laywerapi.shared;

import com.laywerapi.laywerapi.dto.request.ClientRequestDTO;
import com.laywerapi.laywerapi.dto.request.UserUpdateRequestDTO;
import com.laywerapi.laywerapi.entity.Client;
import com.laywerapi.laywerapi.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    public User checkingForUpdatesUser(User user, UserUpdateRequestDTO userUpdateRequestDTO) {
        if (StringUtils.isNoneBlank(userUpdateRequestDTO.getFirstName())) {
            user.setFirstName(userUpdateRequestDTO.getFirstName());
        }
        if (StringUtils.isNoneBlank(userUpdateRequestDTO.getLastName())) {
            user.setLastName(userUpdateRequestDTO.getLastName());
        }
        if (StringUtils.isNoneBlank(userUpdateRequestDTO.getPhone())) {
            user.setPhone(userUpdateRequestDTO.getPhone());
        }
        if (StringUtils.isNoneBlank(userUpdateRequestDTO.getUsername())) {
            user.setUsername(userUpdateRequestDTO.getUsername());
        }
        return user;
    }

    public Client checkingForUpdatesClient(Client client, ClientRequestDTO clientRequestDTO) {
        if (StringUtils.isNoneBlank(clientRequestDTO.getFirstName())) {
            client.setFirstName(clientRequestDTO.getFirstName());
        }
        if (StringUtils.isNoneBlank(clientRequestDTO.getLastName())) {
            client.setLastName(clientRequestDTO.getLastName());
        }
        if (StringUtils.isNoneBlank(clientRequestDTO.getEmail())) {
            client.setEmail(clientRequestDTO.getEmail());
        }
        if (StringUtils.isNoneBlank(clientRequestDTO.getPhone())) {
            client.setPhone(clientRequestDTO.getPhone());
        }
        return client;
    }
}
