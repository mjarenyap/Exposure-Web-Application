/* Handles upload mechanisms */

function addTag(){
    var addedTag = document.getElementById("tagAdd").value;
    
    if(addedTag != ""){
        var feed = document.getElementsByClassName("tag-feed")[0];
        var newTag = document.createElement("div");
        var inputTag = document.createElement("input");
        
        $(inputTag).attr("type", "text");
        $(inputTag).attr("value", addedTag);
        $(inputTag).attr("name", "addedTag");
        $(inputTag).attr("style", "display:none;");
        $(newTag).append(addedTag);
        $(feed).append(newTag);
        $(feed).append(inputTag);
        document.getElementById("tagAdd").value = "";
        document.getElementById("tagAdd").setAttribute("placeholder", "Add more tags...");
    }
}

function addUser(){
    var addedUser = document.getElementById("userAdd").value;
    
    if(addedUser != ""){
        var feed = document.getElementsByClassName("user-feed")[0];
        var newUser = document.createElement("div");
        var inputUser = document.createElement("input");
        
        $(inputUser).attr("type", "text");
        $(inputUser).attr("value", addedUser);
        $(inputUser).attr("name", "addedUser");
        $(inputUser).attr("style", "display:none;");
        $(newUser).append(addedUser);
        $(feed).append(newUser);
        $(feed).append(inputUser);
        document.getElementById("userAdd").value = "";
        document.getElementById("userAdd").setAttribute("placeholder", "Add more people...");
    }
}

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        var imageURL;
        var urlInput = document.createElement("input");
        
        reader.onload = function (e) {
        	var base64result = reader.result.split(',')[1];
            $('#blah').attr('src', e.target.result);
            $(urlInput).attr("type", "text");
            $(urlInput).attr("value", base64result);
            $(urlInput).attr("name", "imageURL");
            $(urlInput).attr("style", "display:none;");
            
            imageURL = document.getElementById("blah").getAttribute("src");
            var uploadImage = document.getElementsByClassName("upload-image")[0];
            $(uploadImage).attr("style", "background-image: url(\"" + imageURL + "\");");
        }
        
        reader.readAsDataURL(input.files[0]);
        
        var uploadWrapper = document.getElementsByClassName("upload-wrapper")[0];
        var uploadDetails = document.getElementsByClassName("upload-details")[0];
        $(uploadDetails).append(urlInput);
        $(uploadWrapper).removeAttr("style");
        
        $("#upload-field").attr("style", "display:none;");
    }
}

function showSharing(){
	if(document.getElementById("remember").checked){
		document.getElementById("addUserField").style.display = "flex";
	}
	
	else document.getElementById("addUserField").style.display = "none";
}

/*
$("#imgInp").change(function(){
    readURL(this);
});
*/