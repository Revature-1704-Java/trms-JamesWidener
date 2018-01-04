function adminLoginFunction() {
  let xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById("admin_login_output").innerHTML = this.responseText;
    }
  };
  let username = document.getElementById('adminUsername').value;
  let password = document.getElementById('adminPassword').value;
  xhr.open("GET", "adminlogin?username=" + username + "&password=" + password, true);
  xhr.send();
}