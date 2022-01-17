package hu.cparker.hyper.membership;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyReward {

    private LocalDate date;
    private Double reward;

    public DailyReward(LocalDate date, Double reward) {
        this.date = date;
        this.reward = reward;
    }
}
