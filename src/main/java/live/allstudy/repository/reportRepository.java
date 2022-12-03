package live.allstudy.repository;

import live.allstudy.entity.reportEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Ryan
 */
public interface reportRepository extends MongoRepository<reportEntity, String> {

}
