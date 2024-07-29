package ua.spro.privatechat.user;

import static ua.spro.privatechat.user.Status.ONLINE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  public void saveUser(User user) {
    user.setStatus(ONLINE);
    repository.save(user);
  }

  public void disconnect(User user) {
    var storedUser = repository.findById(user.getNickname()).orElse(null);
    if(storedUser != null) {
      storedUser.setStatus(Status.OFFLINE);
      repository.save(storedUser);
    }
  }

  public List<User> findConnectedUsers(){
    return repository.findAllByStatus(ONLINE);
  }
}
