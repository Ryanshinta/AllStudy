package live.allstudy.repository;

import live.allstudy.entity.EventCalenderEntity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Ryan
 */
@Repository
public interface EventCalenderRepository extends MongoRepository<EventCalenderEntity,String> {
    Optional<List<EventCalenderEntity>>  findByUserId(String id);

}
