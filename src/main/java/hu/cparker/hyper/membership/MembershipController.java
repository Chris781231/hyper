package hu.cparker.hyper.membership;

import hu.cparker.hyper.membership.dto.CreateMembershipCommand;
import hu.cparker.hyper.membership.dto.MembershipDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/{date}")
    public double getDailyReward(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        LocalDate localDate = LocalDate.of(2021, 1, 1);
        return service.getDailyReward(date);
    }
}
