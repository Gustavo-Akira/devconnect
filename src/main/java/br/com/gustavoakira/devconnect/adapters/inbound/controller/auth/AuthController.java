package br.com.gustavoakira.devconnect.adapters.inbound.controller.auth;

import br.com.gustavoakira.devconnect.adapters.inbound.controller.auth.dto.TokenRequest;
import br.com.gustavoakira.devconnect.adapters.inbound.controller.auth.dto.TokenResponse;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.usecases.auth.TokenGrantUseCase;
import br.com.gustavoakira.devconnect.application.usecases.auth.response.TokenGrantResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
public class AuthController {

    @Autowired
    private TokenGrantUseCase tokenGrantUseCase;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid TokenRequest request) throws BusinessException, EntityNotFoundException {
        final TokenGrantResponse tokenGrantResponse = tokenGrantUseCase.execute(request.toCommand());
        return ResponseEntity.ok(new TokenResponse(tokenGrantResponse.token(), tokenGrantResponse.expiresIn()));
    }
}
