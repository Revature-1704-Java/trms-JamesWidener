function helloFunction() {
  let xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("hello_text").innerHTML = this.responseText;
    }
  };
  let nameEntered = document.getElementById('nameField').value;
  xhr.open("GET", "helloservlet?nameEntered=" + nameEntered, true);
  xhr.send();
}