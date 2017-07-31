/* Javascript for photo handling */

function showPopup(index, url){
    document.getElementById("popup-container").style.top="0";
    var title = document.getElementsByClassName("phototitle")[index].textContent;
    var owner = document.getElementsByClassName("owner")[index].textContent;
    var desc = document.getElementsByClassName("photo-description")[index].textContent;
    
    var link = document.createElement("form");
    $(link).attr("action", "profile");
    $(link).attr("method", "post");
    
    var temp = document.createElement("input");
    $(temp).attr("name", "theusername");
    $(temp).attr("value", owner);
    $(temp).attr("style", "display:none;");
    $(temp).attr("type", "text");
    
    var ownerDiv = document.createElement("input");
    $(ownerDiv).attr("type", "submit");
    $(ownerDiv).attr("value", owner);
    $(ownerDiv).addClass("owner");
    $(link).append(ownerDiv);
    $(link).append(temp);
    
    var imageDiv = document.createElement("div");
    $(imageDiv).addClass("actual-container");
    imageDiv.style.backgroundImage = "url(\"" + url + "\")";
    
    var descP = document.createElement("p");
    $(descP).append(desc);
    $(descP).addClass("popup-description");
    
    $("#popup-wrapper").append(imageDiv);
    $("#popup-wrapper").append("<br/><h3>" + title + "</h3>");
    $("#popup-wrapper").append(link);
    $("#popup-wrapper").append(descP);
}

function showSharedPopup(index, url){
	document.getElementById("popup-container").style.top="0";
    var title = document.getElementsByClassName("sharedTitle")[index].textContent;
    var owner = document.getElementsByClassName("sharedOwner")[index].textContent;
    var desc = document.getElementsByClassName("sharedDescription")[index].textContent;
    
    var link = document.createElement("form");
    $(link).attr("action", "profile");
    $(link).attr("method", "post");
    
    var temp = document.createElement("input");
    $(temp).attr("name", "theusername");
    $(temp).attr("value", owner);
    $(temp).attr("style", "display:none;");
    $(temp).attr("type", "text");
    
    var ownerDiv = document.createElement("input");
    $(ownerDiv).attr("type", "submit");
    $(ownerDiv).attr("value", owner);
    $(ownerDiv).addClass("owner");
    $(link).append(ownerDiv);
    $(link).append(temp);
    
    var imageDiv = document.createElement("div");
    $(imageDiv).addClass("actual-container");
    imageDiv.style.backgroundImage = "url(\"" + url + "\")";
    
    var descP = document.createElement("p");
    $(descP).append(desc);
    $(descP).addClass("popup-description");
    
    $("#popup-wrapper").append(imageDiv);
    $("#popup-wrapper").append("<br/><h3>" + title + "</h3>");
    $("#popup-wrapper").append(link);
    $("#popup-wrapper").append(descP);
}

function hidePopup(){
    document.getElementById("popup-container").style.top="100%";
    $("#popup-wrapper").empty();
}