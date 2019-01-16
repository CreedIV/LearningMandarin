package caverick.LearningMandarin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"Config","webData","caverick.LearningMandarin", "languageData"})
public class LearningMandarinApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningMandarinApplication.class, args);
	}

}

