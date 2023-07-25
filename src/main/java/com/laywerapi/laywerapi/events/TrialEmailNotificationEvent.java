package com.laywerapi.laywerapi.events;

import com.laywerapi.laywerapi.entity.User;
import com.laywerapi.laywerapi.entity.UserRegistrationDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Setter
public class TrialEmailNotificationEvent extends ApplicationEvent {
    private User user;

    public TrialEmailNotificationEvent(User user) {
        super(user);
        this.user = user;
    }
}
