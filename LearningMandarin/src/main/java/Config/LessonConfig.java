package Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import languageData.AccentTranslator;
import languageData.LanguageLesson;
import languageData.LivingLanguageChinese;

@Configuration()
public class LessonConfig {

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS) //TODO make bean scoped to session, so multiple people can use the page and have own languageLesson data
	public LanguageLesson lessonService() {
		return new LivingLanguageChinese();
	}
	
	@Bean
	public AccentTranslator translator() {
		return new AccentTranslator();
	}
}
