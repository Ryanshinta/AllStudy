package live.allstudy.repository;

import java.util.List;
import java.util.Optional;

import live.allstudy.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByResetPasswordToken(String resetPasswordToken);
    UserEntity findAllByEmail(String email);
}
