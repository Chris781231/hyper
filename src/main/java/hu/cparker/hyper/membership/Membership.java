package hu.cparker.hyper.membership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "memberships")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate dateOfPurchase;

    private int value;

    private int duration;

    private double dailyReward;

    public Membership(String name, LocalDate dateOfPurchase, int value) {
        this.name = name;
        this.dateOfPurchase = dateOfPurchase;
        this.value = value;
        this.duration = 600;
        this.dailyReward = value * 0.005;
    }
}
