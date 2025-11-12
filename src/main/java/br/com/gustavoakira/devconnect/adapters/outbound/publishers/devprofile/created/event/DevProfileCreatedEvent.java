package br.com.gustavoakira.devconnect.adapters.outbound.publishers.devprofile.created.event;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class DevProfileCreatedEvent {
    private Long id;
    private String name;
    private String city;
    private String country;
    private String state;
    private List<String> stack = new ArrayList<>();

    public DevProfileCreatedEvent(DevProfile profile){
        this.id = profile.getId();
        this.name = profile.getName();
        this.city = profile.getAddress().getCity();
        this.state = profile.getAddress().getState();
        this.country = profile.getAddress().getCountry();
        this.stack = profile.getStack();
    }
}
