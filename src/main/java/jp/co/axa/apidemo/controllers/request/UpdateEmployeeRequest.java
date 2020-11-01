package jp.co.axa.apidemo.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class UpdateEmployeeRequest {
    @NotNull
    private String name;

    @NotNull
    private Integer salary;

    @NotNull
    private String department;
}
