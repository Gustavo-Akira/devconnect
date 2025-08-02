package br.com.gustavoakira.devconnect.application.services.auth;

import br.com.gustavoakira.devconnect.adapters.config.JwtProvider;
import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IDevProfileRepository;
import br.com.gustavoakira.devconnect.application.usecases.auth.TokenGrantUseCase;
import br.com.gustavoakira.devconnect.application.usecases.auth.command.TokenGrantCommand;
import br.com.gustavoakira.devconnect.application.usecases.auth.response.TokenGrantResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TokenGrantUseCaseImpl implements TokenGrantUseCase {

    private final IDevProfileRepository repository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;

    public TokenGrantUseCaseImpl(IDevProfileRepository repository, JwtProvider jwtProvider, PasswordEncoder encoder) {
        this.repository = repository;
        this.jwtProvider = jwtProvider;
        this.encoder = encoder;
    }

    @Override
    public TokenGrantResponse execute(TokenGrantCommand command) throws BusinessException, EntityNotFoundException {
        if (!"password".equalsIgnoreCase(command.grantType())) {
            throw new BusinessException("Unsupported grant_type "+command.grantType());
        }

        final DevProfile profile = repository.findByEmail(command.username());

        if (!encoder.matches(command.password(), profile.getPassword().getValue())) {
            throw new BusinessException("Invalid credentials");
        }
        final long expiresIn = 2 * 60 * 60L;
        final String token = jwtProvider.generateToken(profile,expiresIn);

        return new TokenGrantResponse(token, expiresIn);
    }
}
