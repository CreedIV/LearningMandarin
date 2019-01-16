package webData;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LessonSelection {
	private String language;
	private int lesson;
}
