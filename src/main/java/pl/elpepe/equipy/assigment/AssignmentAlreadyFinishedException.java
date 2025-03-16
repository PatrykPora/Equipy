package pl.elpepe.equipy.assigment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "this assignment has finished already")
public class AssignmentAlreadyFinishedException extends RuntimeException{
}
