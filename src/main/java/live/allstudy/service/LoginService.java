package live.allstudy.service;

import live.allstudy.dto.authDto;
import live.allstudy.entity.User;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity login(authDto user);
}
