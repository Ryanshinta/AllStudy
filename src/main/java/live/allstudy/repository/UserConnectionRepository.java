package live.allstudy.repository;

import live.allstudy.entity.UserConnectionEntity;
import live.allstudy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserConnectionRepository extends MongoRepository<UserConnectionEntity,String> {
    Optional<UserConnectionEntity> findById(String id);
}
