package br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile;

import br.com.gustavoakira.devconnect.application.usecases.devprofile.DevProfileUseCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dev-profiles/")
public class DevProfileController {
    @Autowired
    private DevProfileUseCases cases;

}
