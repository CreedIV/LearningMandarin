function bindEnterToButton(){
	var input = document.getElementById('translation')
	input.addEventListener("keyup", function(event) {
		document.getElementById('destWord').innerHTML = ""; // clear solution when typing
		// Number 13 is the "Enter" key on the keyboard
		if (event.keyCode === 13) {
			// Trigger the button element with a click
			document.getElementById("myButton").click();
		}
	});
}


var myIndex = 0

function match(idToChange, valueToChange, wordSource){
	var userString = document.getElementById("translation").value;
	var wordToMatch = words[myIndex].destWord;  
	console.log(wordToMatch)
	console.log(userString)
	if(wordToMatch == userString){	
		document.getElementById('translation').value = ""	// clear old user input
		if (myIndex <= words.length) {
			myIndex++;  
			document.getElementById(idToChange)[valueToChange] = words[myIndex][wordSource]; // prompt for next word
		}else{
			console.log("we need to redirect")
			// redirect to next page....
		}
	}else{
		// wrong answer, show what we expected
		console.log("wrong anwser!")
		document.getElementById('destWord').innerHTML = words[myIndex].destWord;
		var firstMistake = lastMistakePoint(userString, wordToMatch )
		document.getElementById('translation').setSelectionRange(firstMistake, firstMistake);
	}		
}

function match_text(){
	match('srcWord','innerHTML', 'srcWord')
}

function match_audio(){
	match('destAudio','src','destAudioPath')
	document.getElementById('destAudioBtn').click()
}

function  answer(){
	document.getElementById('translation').value = words[myIndex].destWord;
}


function lastMistakePoint(userString, correctString) {
	// find the last incorrect character of user input, derived from minEdit algorithm...
	var m = userString.length;
	var n = correctString.length;
	
	// user string along second axis, correct string along first
	var distMatrix = [];
	var backpoint = []
	var i
	var j
	for(i=0; i<n+1; i++) {
		distMatrix[i] = [];
		backpoint[i] = []
	    for(j=0; j<m+1; j++) {
	    	distMatrix[i][j] = undefined;
	    	backpoint[i][j] = undefined; // B is below(insertion), L is left(deletion), D is diag (sub)
	    }
	}
		
	distMatrix[0][0] = 0;
	backpoint[0][0] = ' ';
	
	for(i = 1; i < m+1; i++) {
		distMatrix[0][i] = distMatrix[0][i-1] + 1; // cost of deleteing from user string is 1
		backpoint[0][i] = 'L';
	}
	
	for(i = 1; i < n+1; i++) {
		distMatrix[i][0] = distMatrix[i-1][0] + 1; // cost of inserting chars of correct string is 1
		backpoint[i][0] = 'B';
	}
	
	var belowdist
	var leftdist
	var diagdist
	
	for(i = 1; i < n+1; i++) { // each correct char
		for(j = 1; j < m+1; j++) { // each user supplied char
			if(userString.charAt(j-1) != correctString.charAt(i-1)) {
				belowdist =distMatrix[i-1][j] +1; // insertion cost is 1
				leftdist = distMatrix[i][j-1] +1; // deletion cost is 1
				diagdist = distMatrix[i-1][j-1] + 1; // substitution cost is 1
				if(diagdist <= leftdist && diagdist <= belowdist) {
					distMatrix[i][j] = diagdist;
					backpoint[i][j] = 'D';
				}else if(leftdist <= belowdist) {
					distMatrix[i][j] = leftdist;
					backpoint[i][j] = 'L';
				}else {
					distMatrix[i][j] = belowdist;
					backpoint[i][j] = 'B';
				}
			}else { // correctly matching chars have no cost.
				distMatrix[i][j] = distMatrix[i-1][j-1];
				backpoint[i][j] = 'D';
			}
		}
	}
	
	var mistakePoints = []
	i = n;
	j = m;
	while(i > 0 || j > 0) {

		if(backpoint[i][j] == 'D') {
			if(distMatrix[i][j] != distMatrix[i-1][j-1])
				mistakePoints.push(j);
			i--;
			j--;
		}else if(backpoint[i][j] == 'L') {
			mistakePoints.push(j);
			j--;
		}else if(backpoint[i][j] == 'B') {
			mistakePoints.push(j);
			i--;
		}
	}

	if(mistakePoints.length != 0)
		return mistakePoints[0];
	return null;
}

  
