package org.unib.etf.epraksa.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

//public class NotFoundException extends RuntimeException {
//}
@Getter
@Setter
@ToString
public class NotFoundException extends HttpException {
    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, null);
    }

    public NotFoundException(Object data){
        super(HttpStatus.NOT_FOUND,data);
    }
}
