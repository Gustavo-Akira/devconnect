package br.com.gustavoakira.devconnect.application.usecases.project;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;

public interface DeleteProjectUseCase {
    void execute(Long id) throws EntityNotFoundException;
}
