package hu.cparker.hyper.membership.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipDto {

    private String name;

    private LocalDate dateOfPurchase;

    private int value;

    private int duration;

    private double dailyReward;
}
