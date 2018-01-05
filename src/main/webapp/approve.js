function approveFunction() {
  document.getElementById("approve_output").innerHTML = "JS running";
  let xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function() {
	document.getElementById("approve_output").innerHTML = "ReadyState: " + this.readyState + " Status: " + this.status;
    if (this.readyState == 4 && this.status == 200) {
     if (this.responseText == "")
    	 document.getElementById("approve_output").innerHTML = "There aren't any requests awaiting your approval";
     else
    	 document.getElementById("approve_output").innerHTML = this.responseText;
    }
  };
  email = document.getElementById('currentEmployeeEmail').value;
  password = document.getElementById('currentEmployeePassword').value;
  let givenRequest = document.getElementById('approveRequest').value;
  xhr.open("GET", "approve?email=" + email + "&password=" + password + "&givenRequest=" + givenRequest, true);
  xhr.send();
}