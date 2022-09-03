package live.allstudy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import live.allstudy.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByName(String name);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    
}
