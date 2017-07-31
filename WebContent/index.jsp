<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Welcome to Exposure</title>
        <link rel="stylesheet" href="css/theme.css" type="text/css" />
        <link rel="stylesheet" href="css/pseudo.css" type="text/css" />
        <link rel="stylesheet" href="css/transition.css" type="text/css" />
        <script src="js/photoHandler.js" type="text/javascript"></script>
        <script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
    </head>
    <body>
        <div id="popup-container">
            <img src="images/close.png" id="close" onclick="hidePopup()" />
            <div id="popup-wrapper">
            </div>
        </div>
        <nav class="flex-row-between">
            <a href="relog"><img src="svg/logo.svg" id="site-logo" /></a>
            <ul>
                <li><a href="login.html"><input type="button" value="LOGIN" class="login-button" /></a></li>
                <li><a href="signup.html"><input type="button" value="SIGN UP FOR FREE" class="signup-button" /></a></li>
            </ul>
        </nav>
        <header class="row-end">
            <div class="form-wrapper">
                <div class="headline">The world's fine photos</div>
                <div class="tagline">Login to Exposure</div>
                <br/>
                <input type="text" style="display:none;" name="targetLoginPage" value="1" />
                <form action="login" method="post">
                    <div>
                        <h5>Username</h5>
                        <input type="text" placeholder="ex. JoseRizal" name="username" class="full-width-field input-field" />
                    </div>
                    <div>
                        <h5>Password</h5>
                        <input type="password" placeholder="Type your password" name="password" class="full-width-field input-field" />
                    </div>
                    <div>
                    	<input type="checkbox" name="remember" id="remember" value="1" />
                    	<label for="remember">Remember me</label>
                    </div>
                    <div class="flex-row-between flex-center-align">
                        <input type="submit" value="Login" class="submit-button"/>
                        <span>Don't have an account yet? <a href="signup.html">Sign up here</a>.</span>
                    </div>
                </form>
            </div>
        </header>
        <section class="search-container flex-row-between">
            <ul>
                <li><b>Popular tags:</b></li>
                <li class="popular-tag">
                	<form action="result" method="get">
                		<input type="submit" value="computer" name="tagSearch" class="popular-tag" />
                	</form>
                </li>
                <li class="popular-tag">
                	<form action="result" method="get">
                		<input type="submit" value="webapde" name="tagSearch" class="popular-tag" />
                	</form>
                </li>
                <li class="popular-tag">
                	<form action="result" method="get">
                		<input type="submit" value="webdesign" name="tagSearch" class="popular-tag" />
                	</form>
                </li>
            </ul>
            <form action="result" method="post" id="search-container">
            	<input type="text" placeholder="Search Exposure" id="search-bar" name="search" />
            </form>
        </section>
        <section class="photo-feed">
            <!-- Scriptlet generates the photo feed -->
            <c:forEach items="${photoList}" var="photo" varStatus="status">
	            <div class="photo-wrapper" onclick="showPopup(${status.index}, '${photo.url}')">
                    <h5 class="phototitle"><c:out value="${photo.title}"/></h5>
                    <div class="owner">@<c:out value="${usernameList[status.index]}"/></div>
	                <div class="thumbnail-container" style="background-image:url(<c:out value="${photo.url}"/>)"></div>
	                <p class="photo-description"><c:out value="${photo.description}" /></p>
            		<div class="tags-container">
	            		<c:forEach items="${photoList[status.index].tags}" var="tag">
	            			<span><c:out value="${tag}" /></span>
	            		</c:forEach>
            		</div>
	            </div>
            </c:forEach>
        </section>
        <footer>
            &copy; Copyright Exposure 2017. All rights reserved.
        </footer>
    </body>
</html>