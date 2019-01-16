package languageData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public abstract class LanguageLesson {
    // the lesson elements are ordered string arrays, all string arrays are size 2
    //    they contain the english and mandarin equivalents
	String lessonText = new String();
	
    // these are used for Living Language lessons
    public ArrayList<String[]> lessonVocab1 = new ArrayList<String[]>();
    public ArrayList<String[]> lessonVocab2 = new ArrayList<String[]>();
    public ArrayList<String[]> grammarBuilder1 = new ArrayList<String[]>();
    public ArrayList<String[]> grammarBuilder2 = new ArrayList<String[]>();
    public ArrayList<String[]> lessonWorkOut1 = new ArrayList<String[]>();
    public ArrayList<String[]> lessonWorkOut2 = new ArrayList<String[]>();

    
    // TODO this session specific data might not best be kept here, move it? a db? a seperate bean for only this data?
    public Integer maxLessons;
	public Integer lessonNum;

	public String srcLang;
	public String destLang;

	public int destIndex;
	public int srcIndex;
	
	static public Map<String, List<String>> sectionToPagesMap = new HashMap<String, List<String>>();
	static {
		sectionToPagesMap.put("vocab1", Arrays.asList("showVocab", "typeTranslation", "typeSound"));
		sectionToPagesMap.put("vocab2", Arrays.asList("showVocab", "typeTranslation", "typeSound"));
		sectionToPagesMap.put("grammarbuilder1", Arrays.asList("typeTranslation", "typeSound"));
		sectionToPagesMap.put("grammarbuilder2", Arrays.asList("typeTranslation", "typeSound"));
		sectionToPagesMap.put("workout1", Arrays.asList("typeTranslation", "typeSound"));
		sectionToPagesMap.put("workout2", Arrays.asList("typeTranslation", "typeSound"));
	}
	public ArrayList<ArrayList<String[]>> sections = new ArrayList<ArrayList<String []>>();
	public ArrayList<String> partialPaths = new ArrayList<String>();
	
	public int currentPage;
	public int sectionNum; // index of current section, for accessing partialPaths and sections
	public ArrayList<String[]> currentWords; // words for current section
	public String partialPath; // partial path for current section

		
	public abstract void setLesson(int lessonNum);
	public abstract boolean loadNextSection();
	public abstract boolean loadPrevSection();
	
	// TODO

}
