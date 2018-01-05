function listGradingFormatsFunction() {
  document.getElementById("list_grading_formats_output").innerHTML = "JS running";
  let xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function() {
	document.getElementById("list_grading_formats_output").innerHTML = "ReadyState: " + this.readyState + " Status: " + this.status;
    if (this.readyState == 4 && this.status == 200) {
     if (this.responseText == "")
    	 document.getElementById("list_grading_formats_output").innerHTML = "No response";
     else
    	 document.getElementById("list_grading_formats_output").innerHTML = this.responseText;
    }
  };
  xhr.open("GET", "listgradingformats", true);
  xhr.send();
}