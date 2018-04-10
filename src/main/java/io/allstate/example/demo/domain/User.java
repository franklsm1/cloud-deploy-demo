package io.allstate.example.demo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    @ApiModelProperty(example = "Sean")
    private String firstName;

    @ApiModelProperty(example = "Franklin")
    private String lastName;

    @ApiModelProperty(example = "1991")
    private String birthYear;

}
