package br.com.gustavoakira.devconnect.adapters.inbound.controller.exception.handler;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.ForbiddenException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test-not-found")
    public void throwNotFound() throws EntityNotFoundException {
        throw new EntityNotFoundException("Resource not found");
    }

    @PostMapping("/test-validation")
    public void throwValidation(@Valid @RequestBody TestDto dto) {}

    @GetMapping("/test-generic-exception")
    public void  throwGenericException() throws Exception{
        throw new Exception("Generic Exception");
    }

    @GetMapping("/test-business-exception")
    public void  throwBusinessException() throws Exception{
        throw new BusinessException("Business Exception");
    }

    @GetMapping("/test-forbidden-exception")
    public void  throwForbiddenException() throws Exception{
        throw new ForbiddenException("Forbidden Exception");
    }
}

