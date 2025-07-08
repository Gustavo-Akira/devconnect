package br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile;

import br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile.dto.DevProfileResponse;
import br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile.dto.SaveDevProfileRequest;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.DevProfileUseCases;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.SaveDevProfileCommand;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/dev-profiles/")
public class DevProfileController {
    @Autowired
    private DevProfileUseCases cases;

    @PostMapping
    public ResponseEntity<DevProfileResponse> saveDevProfile(@RequestBody @Valid SaveDevProfileRequest request) throws BusinessException {
        DevProfile profile = cases.saveDevProfileUseCase().execute(request.toCommand());
        return ResponseEntity.created(URI.create("/v1/dev-profiles/"+profile.getId())).body(DevProfileResponse.fromDomain(profile));
    }
}
