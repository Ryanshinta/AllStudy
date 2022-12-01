package live.allstudy.repository;

import live.allstudy.entity.StudyRoomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ryan
 */
@Repository
public interface StudyRoomRepository extends MongoRepository<StudyRoomEntity,String> {
}
