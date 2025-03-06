package pl.elpepe.equipy.asset;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "duplicated serial number")
public class DuplicatedSerialNumberException extends RuntimeException {
}
