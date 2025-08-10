package br.com.gustavoakira.devconnect.adapters.inbound.controller.project;

import br.com.gustavoakira.devconnect.adapters.inbound.controller.project.dto.CreateProjectRequest;
import br.com.gustavoakira.devconnect.adapters.inbound.controller.project.dto.UpdateProjectRequest;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.Project;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.project.ProjectUseCases;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/projects")
public class ProjectController {
    @Autowired
    private ProjectUseCases useCases;

    @PostMapping
    public ResponseEntity<Project> saveProject(@RequestBody @Valid CreateProjectRequest request) throws BusinessException {
        final Project project = useCases.getSaveProjectUseCase().execute(request.toDomain(getLoggedUserId()));
        return ResponseEntity.created(URI.create("/v1/projects/"+project.getId())).body(project);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) throws BusinessException, EntityNotFoundException {
        return ResponseEntity.ok(useCases.getFindProjectByIdUseCase().execute(id));
    }

    @PutMapping
    public ResponseEntity<Project> updateProject(@RequestBody @Valid UpdateProjectRequest request) throws BusinessException, EntityNotFoundException {
        return ResponseEntity.ok(useCases.getUpdateProjectUseCase().execute(request.toDomain(getLoggedUserId())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) throws EntityNotFoundException {
        useCases.getDeleteProjectUseCase().execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dev-profile/{devId}")
    public ResponseEntity<PaginatedResult<Project>> getProjectByDevProfile(@PathVariable Long devId, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") int page) throws BusinessException {
        return ResponseEntity.ok(useCases.getFindAllByDevProfileUseCase().execute(devId,size,page));
    }

    private Long getLoggedUserId(){
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
