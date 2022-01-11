package hu.cparker.hyper.membership;

import hu.cparker.hyper.membership.dto.CreateMembershipCommand;
import hu.cparker.hyper.membership.dto.MembershipDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

@Service
@AllArgsConstructor
public class MembershipService {

    private MembershipRepository repo;

    private ModelMapper modelMapper;

//    private DailyRewardService dailyRewardService;

    private Map<LocalDate, Double> dailyRewards;

    private List<Membership> memberships;

    private double dailyRewardStack;

    public List<MembershipDto> listMemberships() {
        List<Membership> memberships = repo.findAll();

        Type target = new TypeToken<List<MembershipDto>>(){}.getType();
        return modelMapper.map(memberships, target);
    }

    public MembershipDto saveMembership(CreateMembershipCommand command) {
        Membership membership = new Membership(command.getName(), command.getDateOfPurchase(), command.getValue());
        Membership savedMembership = repo.save(membership);

        return modelMapper.map(savedMembership, MembershipDto.class);
    }

    public Double getDailyReward(LocalDate date) {
        List<MembershipDto> membershipDtos = listMemberships();
        checkDate(date, membershipDtos);
//        Map<LocalDate, Double> dailyRewards = dailyRewardService.getDailyRewards();
//        double dailyRewardStack = dailyRewardService.getDailyRewardStack();

        ListIterator<MembershipDto> membershipIt = membershipDtos.listIterator();
        while (membershipIt.hasNext()) {
            MembershipDto membership = membershipIt.next();
            if (date.isBefore(membership.getDateOfPurchase())) break;
            for (int i = 1; i <= membership.getDuration(); i++) {
                fillDailyRewards(membership, i, dailyRewards);
                dailyRewardStack += dailyRewards.get(membership.getDateOfPurchase());
                if (dailyRewardStack >= 50) {
                    Membership newMembership = new Membership("Rebuy", membership.getDateOfPurchase().plusDays(i), 50);
                    membershipIt.add(modelMapper.map(newMembership, MembershipDto.class));
                    dailyRewardStack -= 50;
                }
            }

        }

        return dailyRewards.get(date);
    }

    private void fillDailyRewards(MembershipDto membership, int i, Map<LocalDate, Double> dailyRewards) {
        if (dailyRewards.containsKey(membership.getDateOfPurchase().plusDays(i))) {
            dailyRewards.put(membership.getDateOfPurchase().plusDays(i), dailyRewards.get(membership.getDateOfPurchase().plusDays(i)) + membership.getDailyReward());
        } else {
            dailyRewards.put(membership.getDateOfPurchase().plusDays(i), membership.getDailyReward());
        }
    }

    private void checkDate(LocalDate date, List<MembershipDto> memberships) {
        LocalDate dateOfPurchase = memberships.get(0).getDateOfPurchase();
        if (date.isBefore(dateOfPurchase)) {
            throw new IllegalArgumentException("Dátum nem lehet korábbi, mint " + dateOfPurchase);
        }
    }
}
