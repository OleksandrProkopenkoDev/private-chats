package ua.spro.privatechat.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class User {
  private String nickname;
  private String fullname;
  private Status status;
}
