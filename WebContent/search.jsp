<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Search ${query} - Exposure</title>
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
        <section class="search-container flex-row-between">
            <ul>
                <li><b>Search results for </b><span class="search-label">${query}</span></li>
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