package caverick.LearningMandarin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import languageData.LanguageLesson;
import lombok.extern.slf4j.Slf4j;
import webData.LessonSelection;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {

	
	LanguageLesson lessonService; 
	
	@Autowired
	HomeController(LanguageLesson lessonService){
		this.lessonService = lessonService;
	}
	
	@GetMapping()
	public String home() {
		return "home";
	}
	
	@PostMapping()
	public String selectLesson(LessonSelection lesson) {
		lessonService.setDestLang(lesson.getLanguage().toLowerCase());
		if(lessonService.getDestLang().equals("english")) {
			lessonService.setSrcLang("chinese");
			lessonService.setDestIndex(1);
			lessonService.setSrcIndex(0);
		}else {
			lessonService.setSrcLang("english");
			lessonService.setDestIndex(0);
			lessonService.setSrcIndex(1);
			lessonService.setDestLang("chinese");
		}
		lessonService.setLessonNum(lesson.getLesson());
		lessonService.setLesson(lessonService.getLessonNum()); 
		
		return "redirect:lesson/";
	}
	
}
