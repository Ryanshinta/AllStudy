package live.allstudy.repository;

import live.allstudy.entity.EventCalenderEntity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Ryan
 */
@Repository
public interface EventCalenderRepository extends MongoRepository<EventCalenderEntity,String> {
    Optional<List<EventCalenderEntity>>  findByUserId(String id);

    //Optional<List<EventCalenderEntity>> findByUserIdAAndStartBetween(String id, Instant from, Instant to);
    Optional<List<EventCalenderEntity>> findByStartBetween(Instant from, Instant to);
    Optional<List<EventCalenderEntity>> findByStart(Instant date);
}
