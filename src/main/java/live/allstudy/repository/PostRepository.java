package live.allstudy.repository;

import live.allstudy.entity.PostEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author
 */

@Repository
public interface PostRepository extends MongoRepository<PostEntity,String> {

    Optional<List<PostEntity>> findByUserId(String id);
    Optional<List<PostEntity>> findByUserIdOrderByCreatedAtDesc(String id);

    Optional<List<PostEntity>> findAllByOrderByIdAsc();


}
