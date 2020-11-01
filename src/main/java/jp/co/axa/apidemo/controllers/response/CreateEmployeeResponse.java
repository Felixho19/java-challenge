package jp.co.axa.apidemo.controllers.response;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateEmployeeResponse {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer salary;

    @NotNull
    private String department;
}
