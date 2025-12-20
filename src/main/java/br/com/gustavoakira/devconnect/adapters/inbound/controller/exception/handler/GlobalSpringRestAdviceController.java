package br.com.gustavoakira.devconnect.adapters.inbound.controller.exception.handler;

import br.com.gustavoakira.devconnect.adapters.inbound.controller.exception.handler.dto.ExceptionResponse;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.exceptions.ForbiddenException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalSpringRestAdviceController {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(
            EntityNotFoundException exception,
            HttpServletRequest request
    ){
        return ResponseEntity.status(404)
                .body(
                       createExceptionResponseFromMessageAndCodeAndDetailList(HttpStatus.NOT_FOUND,exception.getMessage(),request.getPathInfo(),"ENTITY_NOT_FOUND",new ArrayList<>())
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        final List<ExceptionResponse.ErrorDetail> details = ex.getBindingResult().getFieldErrors().stream().map(fieldError ->
            new ExceptionResponse.ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage() != null  ? fieldError.getDefaultMessage() : "Invalid Value")
        ).toList();

        return ResponseEntity.badRequest().body(
                createExceptionResponseFromMessageAndCodeAndDetailList(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getPathInfo(),"VALIDATION_ERROR",details)
        );
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> hdndleBusinessException(
            BusinessException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.badRequest().body(
                createExceptionResponseFromMessageAndCodeAndDetailList(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getPathInfo(),"BUSINESS_VALIDATION_ERROR",new ArrayList<>())
        );
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ){
        return ResponseEntity.internalServerError().body(
          createExceptionResponseFromMessageAndCodeAndDetailList(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getPathInfo(), "INTERNAL_ERROR", new ArrayList<>())
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionResponse> handleForbiddenException(
            ForbiddenException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                createExceptionResponseFromMessageAndCodeAndDetailList(HttpStatus.FORBIDDEN, ex.getMessage(), request.getPathInfo(), "AUTH_ERROR", new ArrayList<>())
        );
    }

    private ExceptionResponse createExceptionResponseFromMessageAndCodeAndDetailList(HttpStatus status,
                                                                                     String message,
                                                                                     String link,
                                                                                     String code,
                                                                                     List<ExceptionResponse.ErrorDetail> details){
        return new ExceptionResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                link,
                code,
                details
        );
    }
}
