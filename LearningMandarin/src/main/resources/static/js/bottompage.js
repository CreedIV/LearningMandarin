  

function createBottom(){

var prevButton = document.createElement("button");
prevButton.onclick=function() {  parent.location='/lesson/Previous' }
prevButton.appendChild(document.createTextNode("Previous"));

var answerBtn = document.createElement("button");
answerBtn.onclick=function() { answer(); }
answerBtn.appendChild(document.createTextNode("Show Answer"));

var nextButton = document.createElement("button");
nextButton.onclick=function() { parent.location='/lesson/Next' }
nextButton.appendChild(document.createTextNode("Next"));


var page = document.getElementById("body");
page.appendChild(document.createElement("br"))
page.appendChild(prevButton);
page.appendChild(answerBtn);
page.appendChild(nextButton);

}