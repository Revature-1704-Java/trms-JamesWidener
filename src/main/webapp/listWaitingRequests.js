function listWaitingRequestsFunction() {
  document.getElementById("list_waiting_requests_output").innerHTML = "JS running";
  let xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function() {
	document.getElementById("list_waiting_requests_output").innerHTML = "ReadyState: " + this.readyState + " Status: " + this.status;
    if (this.readyState == 4 && this.status == 200) {
     if (this.responseText == "")
    	 document.getElementById("list_waiting_requests_output").innerHTML = "There aren't any requests awaiting your approval";
     else
    	 document.getElementById("list_waiting_requests_output").innerHTML = this.responseText;
    }
  };
  email = document.getElementById('currentEmployeeEmail').value;
  password = document.getElementById('currentEmployeePassword').value;
  xhr.open("GET", "listwaitingrequests?email=" + email + "&password=" + password, true);
  xhr.send();
}