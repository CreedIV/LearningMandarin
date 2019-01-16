package webData;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SectionWord {

	public SectionWord(String srcWord, String srcPath, String destWord, String destPath) {
		this.srcWord = srcWord;
		this.srcAudioPath = srcPath;
		this.destWord = destWord;
		this.destAudioPath = destPath;
	}
	private String srcWord;
	private String srcAudioPath;
	private String destWord;
	private String destAudioPath;
}
