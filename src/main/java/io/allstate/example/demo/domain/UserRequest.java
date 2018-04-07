package io.allstate.example.demo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotNull
    @ApiModelProperty(example = "Sean")
    private String firstName;

    @NotNull
    @ApiModelProperty(example = "Franklin")
    private String lastName;

    @NotNull
    @Pattern(regexp = "[0-9][0-9][0-9][0-9]")
    @ApiModelProperty(example = "1991")
    private String birthYear;
}
