package live.allstudy.repository;

import live.allstudy.entity.PartnerRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author JIAHAO_CHAI
 */
@Repository
public interface RequestRepository extends MongoRepository<PartnerRequestEntity, String> {
//    Optional<PartnerRequestEntity> findByEmail(String email);
    List<PartnerRequestEntity> findAllByRequestEmail(String requestEmail);
    List<PartnerRequestEntity> findAllByStatusAndReceiveEmail(String status, String receiveEmail);
    Optional<PartnerRequestEntity> findByReceiveEmailAndRequestEmail(String receiveEmail, String requestEmail);
}
