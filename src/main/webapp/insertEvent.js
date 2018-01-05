function insertEventFunction() {
	  document.getElementById("insert_event_output").innerHTML = "JS running";
	  let xhr = new XMLHttpRequest();
	  xhr.onreadystatechange = function() {
		document.getElementById("insert_event_output").innerHTML = "ReadyState: " + this.readyState + " Status: " + this.status;
	    if (this.readyState == 4 && this.status == 200) {
	     if (this.responseText == "")
	    	 document.getElementById("insert_event_output").innerHTML = "No response";
	     else
	    	 document.getElementById("insert_event_output").innerHTML = this.responseText;
	    }
	  };
	  
	  email = document.getElementById('currentEmployeeEmail').value;
	  password = document.getElementById('currentEmployeePassword').value;
	  
	  let location = document.getElementById('eventLocation').value;
	  let description = document.getElementById('eventDescription').value;
	  let justification = document.getElementById('justification').value;
	  
	  location = location.split(' ').join('_');
	  description = description.split(' ').join('_');
	  justification = justification.split(' ').join('_');

	  
	  let startYear = document.getElementById('startYear').value;
	  let startMonth = document.getElementById('startMonth').value;
	  let startDay = document.getElementById('startDay').value;
	  let endYear = document.getElementById('endYear').value;
	  let endMonth = document.getElementById('endMonth').value;
	  let endDay = document.getElementById('endDay').value;
	  let hoursMissed = document.getElementById('hoursMissed').value;
	  let amountRequested = document.getElementById('amountRequested').value;
	  let eventType = document.getElementById('eventType').value;
	  let gradingFormat = document.getElementById('gradingFormat').value;
	  let passingGrade = document.getElementById('passingGrade').value;
	  
	  let parameters = "";
	  parameters += "insertevent?email=" + email;
	  parameters += "&password=" + password;
	  parameters += "&location=" + location;
	  parameters += "&description=" + description;
	  parameters += "&startYear=" + startYear;
	  parameters += "&startMonth=" + startMonth;
	  parameters += "&startDay=" + startDay;
	  parameters += "&endYear=" + endYear;
	  parameters += "&endMonth=" + endMonth;
	  parameters += "&endDay=" + endDay;
	  parameters += "&hoursMissed=" + hoursMissed;
	  parameters += "&amountRequested=" + amountRequested;
	  parameters += "&eventType=" + eventType;
	  parameters += "&gradingFormat=" + gradingFormat;
	  parameters += "&passingGrade=" + passingGrade;
	  parameters += "&justification=" + justification;
	  
	  xhr.open("GET",  parameters, true);
	  xhr.send();
	}