package pl.elpepe.equipy.assigment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "assignment with provided id does not exists")
public class AssignmentNotFoundException extends RuntimeException{
}
