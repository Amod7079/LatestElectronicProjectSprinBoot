package com.CoderXAmod.Electronic.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {


private String ImageName;
        private String Message;
        private boolean success;
        private HttpStatus status;


    }


