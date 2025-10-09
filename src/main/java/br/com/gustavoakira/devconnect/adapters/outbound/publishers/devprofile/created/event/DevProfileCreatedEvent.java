package br.com.gustavoakira.devconnect.adapters.outbound.publishers.devprofile.created.event;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DevProfileCreatedEvent {
    private Long id;
    private String name;
    public DevProfileCreatedEvent(DevProfile profile){
        this.id = profile.getId();
        this.name = profile.getName();
    }
}
