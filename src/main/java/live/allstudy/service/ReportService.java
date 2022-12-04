package live.allstudy.service;


import live.allstudy.entity.EventCalenderEntity;
import live.allstudy.entity.PostEntity;
import live.allstudy.entity.UserEntity;
import live.allstudy.entity.reportEntity;
import live.allstudy.repository.EventCalenderRepository;
import live.allstudy.repository.PostRepository;
import live.allstudy.repository.UserRepository;
import live.allstudy.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    EventCalenderRepository eventCalenderRepository;

    private boolean isUserExist(String userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean increaseLogin(String userId) {
        if (!isUserExist(userId)) {
            return false;
        }
        Optional<reportEntity> userReport = reportRepository.findByUserId(userId);
        reportEntity reportEntity;
        if (userReport.isEmpty()) {
            reportEntity = new reportEntity();
            reportEntity.setUserId(userId);
            reportEntity.setTotalLogin(1);
        } else {
            reportEntity = userReport.get();
            if (reportEntity.getTotalLogin() == null) {
                reportEntity.setTotalLogin(1);
            } else {
                reportEntity.setTotalLogin(reportEntity.getTotalLogin() + 1);
            }
        }
        reportRepository.save(reportEntity);

        return true;
    }

    public boolean updateTotalLike(String userId) {
        if (!isUserExist(userId)) {
            return false;
        }
        Optional<reportEntity> userReport = reportRepository.findByUserId(userId);
        Optional<List<PostEntity>> userPost = postRepository.findByUserId(userId);

        if (userReport.isEmpty() || userPost.isEmpty()) {
            reportEntity reportEntity = new reportEntity();
            reportEntity.setUserId(userId);
            reportEntity.setTotalLike(0);
            reportRepository.save(reportEntity);
        } else {
            Integer like = 0;
            reportEntity reportEntity = userReport.get();
            List<PostEntity> postEntities = userPost.get();


            for (PostEntity post : postEntities) {
                if (post.getLike() == null) {
                    continue;
                } else {
                    like = like + post.getLike().size();
                }
            }

            reportEntity.setTotalLike(like);
            reportRepository.save(reportEntity);
        }
        return true;
    }

    public boolean updateTotalFollower(String userId) {
        if (!isUserExist(userId)) {
            return false;
        }

        Optional<reportEntity> userReport = reportRepository.findByUserId(userId);
        Optional<UserEntity> userEntityOpt = userRepository.findById(userId);

        UserEntity userEntity = userEntityOpt.get();
        Integer totalFollower = userEntity.getFollower().size();

        reportEntity reportEntity;
        if (userReport.isEmpty()) {
            reportEntity = new reportEntity();
            reportEntity.setUserId(userId);
            reportEntity.setTotalFollowers(totalFollower);
        } else {
            reportEntity = userReport.get();
            reportEntity.setTotalFollowers(totalFollower);
        }
        reportRepository.save(reportEntity);
        return true;
    }

    public boolean updateTotalFollowing(String userId) {
        if (!isUserExist(userId)) {
            return false;
        }

        Optional<reportEntity> userReport = reportRepository.findByUserId(userId);
        Optional<UserEntity> userEntityOpt = userRepository.findById(userId);

        UserEntity userEntity = userEntityOpt.get();
        Integer totalFollowing = userEntity.getFollowing().size();

        reportEntity reportEntity;
        if (userReport.isEmpty()) {
            reportEntity = new reportEntity();
            reportEntity.setUserId(userId);
            reportEntity.setTotalFollowing(totalFollowing);
        } else {
            reportEntity = userReport.get();
            reportEntity.setTotalFollowing(totalFollowing);
        }
        reportRepository.save(reportEntity);
        return true;
    }


    public boolean updateTotalPost(String userId) {
        if (!isUserExist(userId)) {
            return false;
        }

        Optional<reportEntity> userReport = reportRepository.findByUserId(userId);
        Optional<List<PostEntity>> byUserId = postRepository.findByUserId(userId);
        Integer totalPost = 0;
        if (byUserId.isPresent()){
           totalPost = byUserId.get().size();
        }


        reportEntity reportEntity;
        if (userReport.isEmpty()) {
            reportEntity = new reportEntity();
            reportEntity.setUserId(userId);
        }else {
            reportEntity = userReport.get();
        }
        reportEntity.setTotalPost(totalPost);
        reportRepository.save(reportEntity);
        return true;
    }


    public boolean increasePublicRoomJoin(String userId) {
        if (!isUserExist(userId)) {
            return false;
        }
        Optional<reportEntity> userReport = reportRepository.findByUserId(userId);
        reportEntity reportEntity;
        if (userReport.isEmpty()) {
            reportEntity = new reportEntity();
            reportEntity.setUserId(userId);
            reportEntity.setPublicRoomJoin(1);
        } else {
            reportEntity = userReport.get();
            if (reportEntity.getPublicRoomJoin() == null) {
                reportEntity.setPublicRoomJoin(1);
            } else {
                reportEntity.setPublicRoomJoin(reportEntity.getPublicRoomJoin() + 1);
            }
        }
        reportRepository.save(reportEntity);
        return true;
    }


    public boolean increasePrivateRoomJoin(String userId) {
        if (!isUserExist(userId)) {
            return false;
        }
        Optional<reportEntity> userReport = reportRepository.findByUserId(userId);
        reportEntity reportEntity;
        if (userReport.isEmpty()) {
            reportEntity = new reportEntity();
            reportEntity.setUserId(userId);
            reportEntity.setPrivateRoomJoin(1);
        } else {
            reportEntity = userReport.get();
            if (reportEntity.getPrivateRoomJoin() == null) {
                reportEntity.setPrivateRoomJoin(1);
            } else {
                reportEntity.setPrivateRoomJoin(reportEntity.getPrivateRoomJoin() + 1);
            }
        }
        reportRepository.save(reportEntity);
        return true;
    }

    public boolean updateTotalEvent(String userId) {
        if (!isUserExist(userId)) {
            return false;
        }

        Optional<reportEntity> userReport = reportRepository.findByUserId(userId);
        Optional<List<EventCalenderEntity>> byUserId = eventCalenderRepository.findByUserId(userId);
        Integer totalEvent = 0;
        if (byUserId.isPresent()){
            totalEvent = byUserId.get().size();
        }


        reportEntity reportEntity;
        if (userReport.isEmpty()) {
            reportEntity = new reportEntity();
            reportEntity.setUserId(userId);
        }else {
            reportEntity = userReport.get();
        }
        reportEntity.setTotalEvent(totalEvent);
        reportRepository.save(reportEntity);
        return true;
    }



}
