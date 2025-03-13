package pl.elpepe.equipy.asset;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "asset with provided id do not exists")
public class AssetNotFoundException extends RuntimeException {
}
