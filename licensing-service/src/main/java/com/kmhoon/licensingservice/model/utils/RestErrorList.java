package com.kmhoon.licensingservice.model.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
public class RestErrorList extends ArrayList<ErrorMessage> {

    @Serial
    private static final long serialVersionUID = -6648757070043361234L;
    private HttpStatus httpStatus;

    public RestErrorList(HttpStatus status, ErrorMessage... errors) {
        this(status.value(), errors);
    }

    public RestErrorList(int status, ErrorMessage... errors) {
        this.httpStatus = HttpStatus.valueOf(status);
        addAll(Arrays.asList(errors));
    }
}
