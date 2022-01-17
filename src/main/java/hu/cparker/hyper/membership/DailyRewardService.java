package hu.cparker.hyper.membership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyRewardService {

    private Double dailyRewardStack;

    public Double getDailyRewardStack() {
        return 0.0;
    }
}
