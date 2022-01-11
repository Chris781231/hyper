package hu.cparker.hyper;

import hu.cparker.hyper.membership.DailyRewardService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class HyperApplication {

	public static void main(String[] args) {
		SpringApplication.run(HyperApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Bean
	public DailyRewardService dailyRewardService() {
		return new DailyRewardService();
	}

}
