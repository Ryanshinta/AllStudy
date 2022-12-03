package live.allstudy.repository;

import live.allstudy.entity.StudyRoomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ryan
 */
@Repository
public interface StudyRoomRepository extends MongoRepository<StudyRoomEntity,String> {
    List<StudyRoomEntity> findBySessionID(String sessionId);
}
