package live.allstudy.repository;

import live.allstudy.entity.reportEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ryan
 */


@Repository
public interface ReportRepository extends MongoRepository<reportEntity, String> {
    Optional<reportEntity> findByUserId(String id);
}
