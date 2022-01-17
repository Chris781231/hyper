package hu.cparker.hyper.membership;

import hu.cparker.hyper.membership.dto.CreateMembershipCommand;
import hu.cparker.hyper.membership.dto.MembershipDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class MembershipController {

    private MembershipService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "Membership has been created")
    public MembershipDto saveMembership(@RequestBody CreateMembershipCommand command) {
        return service.saveMembership(command);
    }

    @GetMapping
    public List<MembershipDto> listMemberships() {
        return service.listMemberships();
    }

    @GetMapping("/max")
    public Map.Entry<LocalDate, Double> getMaxDailyReward() {
        return service.getMaxDailyReward();
    }

    @GetMapping("/daily_reward_changes_per_day")
    public List<DailyReward> getDailyRewardChangesPerDay() {
        return service.getDailyRewardChangesPerDay();
    }
}
