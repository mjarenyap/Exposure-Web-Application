<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Upload a photo - Exposure</title>
        <link rel="stylesheet" href="css/theme.css" type="text/css" />
        <link rel="stylesheet" href="css/pseudo.css" type="text/css" />
        <link rel="stylesheet" href="css/transition.css" type="text/css" />
        <script src="js/uploadHandler.js" type="text/javascript"></script>
        <script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
    </head>
    <body>
    	<script type="text/javascript">
    	function getFilePath(){
    		$('input[type=file]').change(function(){
    			console.log(this.URL);
    		});
    	}
    	</script>
        <nav class="flex-row-between">
            <a href="feed"><img src="svg/logo.svg" id="site-logo" /></a>
            <ul class="flex-row-between" style="align-items: center;">
                <li style="padding: 0;">
                	<a href="upload.jsp">
	                	<input type="button" value="UPLOAD" class="upload-button" />
	                </a>
                </li>
                <li style="padding: 0;">
                    <a href="profile">
	                    <div class="profile-nav row-start">
	                        <div class="profile-photo"></div>
	                        <span>${sessionScope.username}</span>
	                    </div>
                    </a>
                </li>
                <li style="padding: 0;">
                    <a href="logout"><span class="logout-button">LOG OUT</span></a>
                </li>
            </ul>
        </nav>
        <section class="upload-feed">
        	<form id="upload-field" runat="server">
        		<p>Upload your photos here</p>
                <span>(.JPG, .PNG or .TIFF)</span>
                <br/><br/>
		        <input type='file' id="imgInp" onchange="readURL(this)" accept="image/*" />
		        <br/>
		        <img id="blah" src="#" alt="your image" style="display:none;" />
		    </form>
		    <form action="upload" method="post">
			    <div class="upload-wrapper" style="display: none;">
	                <div class="upload-image" id="preview"></div>
	                <div class="upload-details">
	                    <div>
	                        <h4>Title</h4>
	                        <input type="text" placeholder="ex. The most amazing photo ever" name="title" class="input-field" />
	                    </div>
	                    <div>
	                        <h4>A short descripion</h4>
	                        <textarea class="input-field" name="description" placeholder="Tell us something about this photo"></textarea>
	                    </div>
	                    <div class="tag-feed"></div>
	                    <div style="display:flex;">
	                    	<input type="text" placeholder="Add a tag..." id="tagAdd" class="input-field" />
	                    	<input type="button" value="+" class="plus-button" onclick="addTag()" />
	                    </div>
	                    <div>
	                    	<input type="checkbox" name="privacy" id="remember" value="1" onclick="showSharing()" />
	                    	<label for="remember">Private</label>
	                    </div>
	                    <div class="user-feed"></div>
	                    <div style="display:none;" id="addUserField">
	                    	<input type="text" placeholder="Share this photo with friends" id="userAdd" class="input-field" />
	                    	<input type="button" value="+" class="plus-button" onclick="addUser()" />
	                    </div>
	                    <br/>
	                    <input type="submit" value="Upload" class="submit-button" />
	                </div>
	            </div>
		    </form>
         <!--
            <div id="upload-field">
                <p>Upload your photos here</p>
                <span>(.JPG, .PNG or .TIFF)</span>
                <br/><br/>
                <input type="file" accept="image/*" />
            </div>
        -->
        </section>
        <footer>
            &copy; Copyright Exposure 2017. All rights reserved.
        </footer>
    </body>
</html>