package br.com.gustavoakira.devconnect.adapters.outbound.persistence.repository.user;

import br.com.gustavoakira.devconnect.adapters.outbound.exceptions.EntityNotFoundException;
import br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity.UserEntity;
import br.com.gustavoakira.devconnect.application.domain.User;
import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.repository.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final SpringDataPostgresUserRepository repository;
    private final PasswordEncoder encoder;

    public UserRepositoryImpl(SpringDataPostgresUserRepository repository, PasswordEncoder encoder){
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public User findById(Long id) throws BusinessException, EntityNotFoundException {
        return this.repository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found with id " + id)).toDomain();
    }

    @Override
    public User findByEmail(String email) throws EntityNotFoundException, BusinessException {
        return this.repository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("User not found with email " + email)).toDomain();
    }

    @Override
    public User save(User user) throws BusinessException {
        final UserEntity entity = new UserEntity(user);
        entity.setPassword(encoder.encode(user.getPassword().getValue()));
        return this.repository.save(entity).toDomain();
    }
}
