package br.com.gustavoakira.devconnect.application.services.auth;

import br.com.gustavoakira.devconnect.adapters.config.JwtProvider;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.repository.IUserRepository;
import br.com.gustavoakira.devconnect.application.usecases.auth.TokenGrantUseCase;
import br.com.gustavoakira.devconnect.application.usecases.auth.command.TokenGrantCommand;
import br.com.gustavoakira.devconnect.application.usecases.auth.response.TokenGrantResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TokenGrantUseCaseImpl implements TokenGrantUseCase {

    private final IDevProfileRepository devProfileRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;
    private final IUserRepository userRepository;

    public TokenGrantUseCaseImpl(IDevProfileRepository repository, JwtProvider jwtProvider, PasswordEncoder encoder, IUserRepository userRepository) {
        this.devProfileRepository = repository;
        this.jwtProvider = jwtProvider;
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Override
    public TokenGrantResponse execute(TokenGrantCommand command) throws BusinessException, EntityNotFoundException {
        if (!"password".equalsIgnoreCase(command.grantType())) {
            throw new BusinessException("Unsupported grant_type "+command.grantType());
        }

        User user;

        try {
            user = userRepository.findByEmail(command.username());
        } catch (EntityNotFoundException e) {
            final DevProfile legacyProfile = devProfileRepository.findByEmail(command.username());

            if (!encoder.matches(command.password(), legacyProfile.getPassword().getValue())) {
                throw new BusinessException("Invalid credentials");
            }

            user = userRepository.save(new User(legacyProfile.getName(),legacyProfile.getEmail(),legacyProfile.getPassword().getValue(),legacyProfile.isActive()));
        }

        if(user == null){
            throw  new EntityNotFoundException("User not found");
        }

        if (!encoder.matches(command.password(), user.getPassword().getValue())) {
            throw new BusinessException("Invalid credentials");
        }
        final long expiresIn = 2 * 60 * 60L * 1000;
        final String token = jwtProvider.generateToken(user,expiresIn);

        return new TokenGrantResponse(token, expiresIn);
    }
}
