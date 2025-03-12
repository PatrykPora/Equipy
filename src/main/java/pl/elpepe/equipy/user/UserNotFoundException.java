package pl.elpepe.equipy.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "no user with provided id")
public class UserNotFoundException extends RuntimeException{
}
