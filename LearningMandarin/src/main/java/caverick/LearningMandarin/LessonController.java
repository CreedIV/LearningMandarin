package caverick.LearningMandarin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import languageData.AccentTranslator;
import languageData.LanguageLesson;
import lombok.extern.slf4j.Slf4j;
import webData.SectionSelection;
import webData.SectionWord;

@Slf4j
@Controller
@RequestMapping("/lesson")
public class LessonController {

	
	LanguageLesson lessonService; 
	
	@Autowired
	AccentTranslator translator;
	
	@Autowired
	LessonController(LanguageLesson lessonService){
		this.lessonService = lessonService;
	}
	
	
	@GetMapping("/")
	public String promptForSection(Model model){
		if(lessonService == null)
			return "redirect:/";

		List<String> lessonSections = new ArrayList<String>();
		if(!lessonService.getLessonVocab1().isEmpty()) {
			lessonSections.add("vocab1");
		}
		if(!lessonService.getLessonWorkOut1().isEmpty()){
			lessonSections.add("workout1");
		}
		if(!lessonService.getGrammarBuilder1().isEmpty()) {
			lessonSections.add("grammarbuilder1");   // TODO I am strongly coupled to these strings... in a number of places, decouple it
		}
		if(!lessonService.getLessonVocab2().isEmpty()) {
			lessonSections.add("vocab2");
		}
		if(!lessonService.getLessonWorkOut2().isEmpty()) {
			lessonSections.add("workout2");
		}
		if(!lessonService.getGrammarBuilder2().isEmpty()) {
			lessonSections.add("grammarbuilder2");
		}
		model.addAttribute("sections", lessonSections);
		return "selectLessonSection";		
	}
	
	@PostMapping("/")
	public String setSection(SectionSelection section) {
		lessonService.setSectionNum(lessonService.getPartialPaths().indexOf(section.getSelectedSection()));
		lessonService.setCurrentWords(lessonService.getSections().get(lessonService.getSectionNum()));
		lessonService.setPartialPath(section.getSelectedSection());
		lessonService.setCurrentPage(-1);
		log.info("loading section " + lessonService.getPartialPaths().indexOf(section.getSelectedSection()));
		return "redirect:/lesson/Next";
	}
	
	@GetMapping("/Next")
	public String nextSection(Model model) {
		lessonService.setCurrentPage(lessonService.getCurrentPage() + 1);
		log.info("loading page " + lessonService.getCurrentPage() );
		if(lessonService.getCurrentPage() >= LanguageLesson.sectionToPagesMap.get(lessonService.getPartialPath()).size()) {
			if(lessonService.loadNextSection() == false) {
				return "completedAllLessons";
			}else {
				lessonService.setCurrentPage(lessonService.getCurrentPage()+1);
			}
		}
		loadSectionWordsIntoModel(model);
		model.addAttribute("section", lessonService.getPartialPath());
		String page = LanguageLesson.sectionToPagesMap.get(lessonService.getPartialPath()).get(lessonService.getCurrentPage());
		log.info("going to view " + page);
		return page;
	}
	
	@GetMapping("/Previous")
	public String prevSection(Model model) {
		lessonService.setCurrentPage(lessonService.getCurrentPage() - 1);
		log.info("loading page " + lessonService.getCurrentPage() );
		if(lessonService.getCurrentPage() < 0) {
			if(lessonService.loadPrevSection() == false){
				lessonService.setCurrentPage(0);
			}
		}
		loadSectionWordsIntoModel(model);
		model.addAttribute("section", lessonService.getPartialPath());
		String page = LanguageLesson.sectionToPagesMap.get(lessonService.getPartialPath()).get(lessonService.getCurrentPage());
		log.info("going to view " + page);
		return page;
	}
	
	@GetMapping("/Bottom")
	public String showBottomPage(){
		return "bottomPage";
	}
	
	private void loadSectionWordsIntoModel(Model model) {
		List<SectionWord> sectionWords = new ArrayList<SectionWord>();
		ArrayList<String[]> vocabWords = lessonService.getCurrentWords();
		String partialPath = lessonService.getPartialPath();
		
		for(int i = 0; i < vocabWords.size(); i++) {
			SectionWord temp = new SectionWord(
					translator.translate(vocabWords.get(i)[lessonService.getSrcIndex()]),
					createWavFilePath(lessonService.getLessonNum(), lessonService.getSrcLang() + "/" + partialPath ,  vocabWords.get(i)[lessonService.getSrcIndex()]),
					translator.translate(vocabWords.get(i)[lessonService.getDestIndex()]),
					createWavFilePath(lessonService.getLessonNum(), lessonService.getDestLang() + "/" + partialPath,  vocabWords.get(i)[lessonService.getDestIndex()]));	
			sectionWords.add(i, temp);
		}
		model.addAttribute("sectionWords",sectionWords);	
	}

	private String createWavFilePath(Integer lessonNum, String partialPath, String lessonDataString) {
		String filename = lessonDataString.replaceAll("[ .?!,()]", "") + ".wav";
		String path = "/audio/lesson" + lessonNum + "/" + partialPath + "/" + filename;
		return path;
	}
}
