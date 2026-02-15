package br.com.gustavoakira.devconnect.adapters.inbound.controller.passwordrecovery;

import br.com.gustavoakira.devconnect.adapters.inbound.controller.passwordrecovery.dto.RequestPasswordRecoveryRequest;
import br.com.gustavoakira.devconnect.application.usecases.passwordrecovery.RequestPasswordRecoveryUseCase;
import br.com.gustavoakira.devconnect.application.usecases.passwordrecovery.command.RequestPasswordRecoveryCommand;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/password-recovery")
public class PasswordRecoveryController {

    private final RequestPasswordRecoveryUseCase requestPasswordRecoveryUseCase;
    private final Logger logger = LoggerFactory.getLogger(PasswordRecoveryController.class);

    public PasswordRecoveryController(RequestPasswordRecoveryUseCase requestPasswordRecoveryUseCase){
        this.requestPasswordRecoveryUseCase = requestPasswordRecoveryUseCase;
    }

    @PostMapping()
    public ResponseEntity<Void> requestPasswordRecovery(@RequestBody @Valid RequestPasswordRecoveryRequest request){
        logger.debug("Password recover requested");
        requestPasswordRecoveryUseCase.execute(new RequestPasswordRecoveryCommand(request.email()));
        return ResponseEntity.noContent().build();
    }
}
