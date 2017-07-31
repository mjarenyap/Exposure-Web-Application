function checkLogin(){
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	
	if(username.equals("") || password.equals(""))
		document.getElementsByClassName("error-message")[0].style.display = "block";
	
	else window.location = "/login";
}

function checkSignup(){
	var name = document.getElementById("fullname").value;
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    
    if(username.equals("") || password.equals("") || name.equals(""))
        document.getElementsByClassName("error-message")[0].style.display = "block";
    
    else window.location = "/register";
}