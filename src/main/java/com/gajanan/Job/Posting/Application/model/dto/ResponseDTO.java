package com.gajanan.Job.Posting.Application.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;  // Indicates the result of the request
    private String message;  //A descriptive message providing details about the status.
    private Object data;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date timestamp = new Date(); //The time when the response was generated.


    public ResponseDTO(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
