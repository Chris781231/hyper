package hu.cparker.hyper.membership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyRewardService {

    private Map<LocalDate, Double> dailyRewards;

    private List<Membership> memberships;

    private double dailyRewardStack;


}
