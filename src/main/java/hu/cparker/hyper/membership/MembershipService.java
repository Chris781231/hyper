package hu.cparker.hyper.membership;

import hu.cparker.hyper.membership.dto.CreateMembershipCommand;
import hu.cparker.hyper.membership.dto.MembershipDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;

@Service
public class MembershipService {

    private MembershipRepository repo;

    private ModelMapper modelMapper;

    private Map<LocalDate, Double> dailyRewards;

    private List<Membership> memberships;

    private Double dailyRewardStack;

    public MembershipService(MembershipRepository repo, ModelMapper modelMapper, Map<LocalDate, Double> dailyRewards, List<Membership> memberships) {
        this.repo = repo;
        this.modelMapper = modelMapper;
        this.dailyRewards = new TreeMap<>();
        this.memberships = new ArrayList<>();
        this.dailyRewardStack = 0.0;
    }

//    private DailyRewardService dailyRewardService;

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

    public Map.Entry<LocalDate, Double> getMaxDailyReward() {
        initDailyRewards();

        return dailyRewards.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow(IllegalStateException::new);
    }

    public List<DailyReward> getDailyRewardChangesPerDay() {
        initDailyRewards();
        List<DailyReward> changedDailyRewards = new ArrayList<>();
        DailyReward prevDailyReward = null;

        for (Map.Entry<LocalDate, Double> reward : dailyRewards.entrySet()) {
            DailyReward dailyReward = new DailyReward(reward.getKey(), reward.getValue());
            if (prevDailyReward == null || !dailyReward.getReward().equals(prevDailyReward.getReward())) {
                changedDailyRewards.add(dailyReward);
            }
            prevDailyReward = dailyReward;
        }

        return changedDailyRewards;
    }

    private void initDailyRewards() {
        init();
        fillMemberships();
        memberships.forEach(this::fillDailyRewards);
    }

    private void init() {
        memberships.clear();
        dailyRewards.clear();
    }

    private void fillMemberships() {
        List<MembershipDto> membershipDtos = listMemberships();
        Type target = new TypeToken<List<Membership>>(){}.getType();
        memberships = modelMapper.map(membershipDtos, target);
    }


    private void fillDailyRewards(Membership membership) {
        for (int i = 1; i <= membership.getDuration(); i++) {
            LocalDate actualDate = membership.getDateOfPurchase().plusDays(i);
            if (dailyRewards.containsKey(actualDate)) {
                dailyRewards.put(actualDate, dailyRewards.get(actualDate) + membership.getDailyReward());
            } else {
                dailyRewards.put(actualDate, membership.getDailyReward());
            }
        }
    }

    private void checkDate(LocalDate date, List<MembershipDto> memberships) {
        LocalDate dateOfPurchase = memberships.get(0).getDateOfPurchase();
        if (date.isBefore(dateOfPurchase)) {
            throw new IllegalArgumentException("Dátum nem lehet korábbi, mint " + dateOfPurchase);
        }
    }
}
