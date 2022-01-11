package hu.cparker.hyper.membership.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMembershipCommand {

    @Schema(description = "name of membership", example = "300")
    @NotBlank
    private String name;

    @Schema(description = "date of purchase", example = "2022-01-01")
    @NotBlank
    private LocalDate dateOfPurchase;

    @Schema(description = "value of membership", example = "300")
    @NotBlank
    private int value;
}
