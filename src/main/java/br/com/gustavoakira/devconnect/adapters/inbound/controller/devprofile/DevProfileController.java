package br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile;

import br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile.dto.DevProfileResponse;
import br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile.dto.SaveDevProfileRequest;
import br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile.dto.UpdateDevProfileRequest;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.shared.PaginatedResult;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.DevProfileUseCases;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.DeleteDevProfileCommand;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.filters.DevProfileFilter;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.query.DevProfileFindAllQuery;
import br.com.gustavoakira.devconnect.application.usecases.devprofile.query.FindDevProfileByIdQuery;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/dev-profiles")
public class DevProfileController {
    @Autowired
    private DevProfileUseCases cases;

    @PostMapping
    public ResponseEntity<DevProfileResponse> saveDevProfile(@RequestBody @Valid SaveDevProfileRequest request) throws BusinessException {
        final DevProfile profile = cases.saveDevProfileUseCase().execute(request.toCommand());
        return ResponseEntity.created(URI.create("/v1/dev-profiles/"+profile.getId())).body(DevProfileResponse.fromDomain(profile));
    }

    @PutMapping
    public ResponseEntity<DevProfileResponse> updateDevProfile(@RequestBody @Valid UpdateDevProfileRequest request) throws BusinessException, EntityNotFoundException {
        return ResponseEntity.ok().body(DevProfileResponse.fromDomain(cases.updateDevProfileUseCase().execute(request.toCommand(), getLoggedUserId())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevProfile(@PathVariable Long id) throws EntityNotFoundException {
        cases.deleteDevProfileUseCase().execute(new DeleteDevProfileCommand(id),getLoggedUserId());
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<DevProfileResponse> getDevProfile(@PathVariable Long id) throws BusinessException, EntityNotFoundException {
        final DevProfile profile=cases.findDevProfileByIdUseCase().execute(new FindDevProfileByIdQuery(id));
        return ResponseEntity.ok(DevProfileResponse.fromDomain(profile));
    }

    @GetMapping("/profile")
    public ResponseEntity<DevProfileResponse> getProfile() throws BusinessException, EntityNotFoundException {
        return ResponseEntity.ok(DevProfileResponse.fromDomain(cases.findDevProfileByIdUseCase().execute(new FindDevProfileByIdQuery(getLoggedUserId()))));
    }

    @GetMapping
    public ResponseEntity<PaginatedResult<DevProfileResponse>> findAll(
            @RequestParam(defaultValue = "0") Integer number,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String tech,
            @RequestParam(required = false) List<String> stack
    ) throws BusinessException {
        final PaginatedResult<DevProfile> profiles;

        final boolean hasFilters = (name != null && !name.isBlank()) ||
                (city != null && !city.isBlank()) ||
                (stack != null && !stack.isEmpty());

        if (hasFilters) {
            final DevProfileFilter filter = new DevProfileFilter(name, city, stack);
            profiles = cases.findAllDevProfileWithFilterUseCase().execute(filter, number, size);
        } else {
            profiles = cases.findAllDevProfileUseCase().execute(new DevProfileFindAllQuery(number, size));
        }

        return ResponseEntity.ok(toResponse(profiles));
    }

    private Long getLoggedUserId(){
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    private PaginatedResult<DevProfileResponse> toResponse(PaginatedResult<DevProfile> domain) {
        return new PaginatedResult<>(
                domain.getContent().stream().map(DevProfileResponse::fromDomain).toList(),
                domain.getPage(),
                domain.getSize(),
                domain.getTotalElements()
        );
    }
}
