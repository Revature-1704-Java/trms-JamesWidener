function listMyRequestsFunction() {
  document.getElementById("list_my_requests_output").innerHTML = "JS running";
  let xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function() {
	document.getElementById("list_my_requests_output").innerHTML = "ReadyState: " + this.readyState + " Status: " + this.status;
    if (this.readyState == 4 && this.status == 200) {
     if (this.responseText == "")
    	 document.getElementById("list_my_requests_output").innerHTML = "You haven't made any requests";
     else
    	 document.getElementById("list_my_requests_output").innerHTML = this.responseText;
    }
  };
  email = document.getElementById('currentEmployeeEmail').value;
  password = document.getElementById('currentEmployeePassword').value;
  xhr.open("GET", "listmyrequests?email=" + email + "&password=" + password, true);
  xhr.send();
}