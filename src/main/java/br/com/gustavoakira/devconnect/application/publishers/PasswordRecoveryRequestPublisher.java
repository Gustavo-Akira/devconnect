package br.com.gustavoakira.devconnect.application.publishers;

import br.com.gustavoakira.devconnect.application.domain.PasswordRecovery;
import br.com.gustavoakira.devconnect.application.domain.User;

public interface PasswordRecoveryRequestPublisher {
    void send(PasswordRecovery recovery, User user);
}
