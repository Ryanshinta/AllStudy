package live.allstudy.repository;

import live.allstudy.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author
 */

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {
    Optional<List<PostEntity>> findByUserId(String id);

    Optional<List<PostEntity>> findByUserIdOrderByCreatedAtDesc(String id);
}
