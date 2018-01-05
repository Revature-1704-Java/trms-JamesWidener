function employeeLoginFunction() {
	  document.getElementById("employee_login_output").innerHTML = "JS running";
	  let xhr = new XMLHttpRequest();
	  xhr.onreadystatechange = function() {
		document.getElementById("employee_login_output").innerHTML = "ReadyState: " + this.readyState + " Status: " + this.status;
	    if (this.readyState == 4 && this.status == 200) {
	     if (this.responseText == "")
	    	 document.getElementById("employee_login_output").innerHTML = "No response";
	     else
	    	 document.getElementById("employee_login_output").innerHTML = this.responseText;
	    }
	  };
	  let email = document.getElementById('employeeEmail').value;
	  let password = document.getElementById('employeePassword').value;
	  document.getElementById('currentEmployeeEmail').value=email;
	  document.getElementById('currentEmployeePassword').value=password;
	  xhr.open("GET", "employeelogin?email=" + email + "&password=" + password, true);
	  xhr.send();
	}