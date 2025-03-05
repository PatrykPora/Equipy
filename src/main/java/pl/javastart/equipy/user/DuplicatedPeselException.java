package pl.javastart.equipy.user;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "duplicated pesel")
public class DuplicatedPeselException extends RuntimeException {
}
