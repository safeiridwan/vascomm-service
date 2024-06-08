package com.vascomm.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAPI implements Serializable {
    private Object code;
    private Object message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

}