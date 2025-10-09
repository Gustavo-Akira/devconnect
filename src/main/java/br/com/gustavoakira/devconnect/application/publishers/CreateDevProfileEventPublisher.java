package br.com.gustavoakira.devconnect.application.publishers;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;

public interface CreateDevProfileEventPublisher {
    void sendMessage(DevProfile profile);
}
