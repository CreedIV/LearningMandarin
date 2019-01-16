var accentMap1 = {}
var accentMap2 = {}
var accentMap3 = {}
var accentMap4 = {}
var accentMap5 = {}

accentMap4['A'] = '\u00C0'; // falling
accentMap1['A'] = '\u0100'; // flat
accentMap2['A'] = '\u00C1'; // rising
accentMap3['A'] = '\u0102'; // fall rise
accentMap4['E'] = '\u00C8'; // falling
accentMap1['E'] = '\u0112'; // flat
accentMap2['E'] = '\u00C9'; // rising
accentMap3['E'] = '\u0114'; // fall rise
accentMap4['I'] = '\u00CC'; // falling
accentMap1['I'] = '\u012A'; // flat
accentMap2['I'] = '\u00CD'; // rising
accentMap3['I'] = '\u012C'; // fall rise
accentMap4['O'] = '\u00D2'; // falling
accentMap1['O'] = '\u014C'; // flat
accentMap2['O'] = '\u00D3'; // rising
accentMap3['O'] = '\u014E'; // fall rise  
accentMap4['U'] = '\u00D9'; // falling
accentMap1['U'] = '\u016A'; // flat
accentMap2['U'] = '\u00DA'; // rising
accentMap3['U'] = '\u016C'; // fall rise
accentMap5['U'] = '\u01D9';
		

function translate_typing(id) {
  oldValue = document.getElementById(id).value;
  len = oldValue.length
  if(len >= 2){
	  newchar = translate_chars(oldValue.charAt(len-2), oldValue.charAt(len-1))
	  if(newchar !== '')
		  document.getElementById(id).value = oldValue.substr(0, len-2) + newchar
  }
}

function translate_value(id) {
	oldValue = document.getElementById(id).value;
	document.getElementById(id).value = translate_string(oldValue);
}

function translate_chars(prevChar, currChar){
	var prevCharUpper = prevChar.toUpperCase();
	var ret = '';
	switch(currChar) {
	case '1':
	    ret = accentMap1[prevCharUpper];
	    break;
	case '2':
	    ret = accentMap2[prevCharUpper];
	    break;
	case '3':
	    ret = accentMap3[prevCharUpper];
	    break;
	case '4':
	    ret = accentMap4[prevCharUpper];
	    break;
	case '5':
	    ret = accentMap5[prevCharUpper];
	    break;
	}
	if(ret !== '' && (typeof ret !== 'undefined') && prevCharUpper !== prevChar) // change back to lowercase if needed
	    ret = ret.toLowerCase();
	if(typeof ret === 'undefined')
		return '';
	return ret;
}

function translate_string(input) {
    if(input.length <= 1) 
        return input;
    
    var ret = "";
    for(i = 2; i <= input.length; i++){ // go through all characters translating as needed and build up result
        var newChar = translate_chars(input.charAt(i-2), input.charAt(i-1));
        if(newChar === '') {
            ret += input.charAt(i-2);
        
            if(i === (input.length)) { //when there is no translation at the end of string, include the last character without translation
                ret += input.charAt(i-1);
            }
        }else {
            ret += newChar;
            i++;
            if(i === (input.length)) { //when there is no translation at the end of string, include the last character without translation
                ret += input.charAt(i-1);
            }
        }
    }
    return ret;
}