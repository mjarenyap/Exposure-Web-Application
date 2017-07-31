function showUploads(){
	var uploads = document.getElementById("uploads");
	var shared = document.getElementById("shared");
	
	$(uploads).addClass("view-active");
	$(shared).removeClass("view-active");
	
	document.getElementsByClassName("photo-feed")[0].style.display="flex";
	document.getElementsByClassName("shared-feed")[0].style.display="none";
}

function showShared(){
	var uploads = document.getElementById("uploads");
	var shared = document.getElementById("shared");
	
	$(shared).addClass("view-active");
	$(uploads).removeClass("view-active");
	
	document.getElementsByClassName("shared-feed")[0].style.display="flex";
	document.getElementsByClassName("photo-feed")[0].style.display="none";
}