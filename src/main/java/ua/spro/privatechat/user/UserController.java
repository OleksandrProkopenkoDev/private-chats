package ua.spro.privatechat.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @MessageMapping("/user.addUser")
  @SendTo("/user/topic")
  public User addUser(@Payload User user) {
    userService.saveUser(user);
    return user;
  }

  @MessageMapping("/user.disconnectUser")
  @SendTo("/user/topic")
  public User disconnect(@Payload User user) {
    userService.disconnect(user);
    return user;
  }

  @GetMapping("/user")
  public ResponseEntity<List<User>> findConnectedUsers() {
    return ResponseEntity.ok(userService.findConnectedUsers());
  }
}