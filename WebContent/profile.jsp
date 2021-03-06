<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Profile - Exposure</title>
        <link rel="stylesheet" href="css/theme.css" type="text/css" />
        <link rel="stylesheet" href="css/pseudo.css" type="text/css" />
        <link rel="stylesheet" href="css/transition.css" type="text/css" />
        <script src="js/photoHandler.js" type="text/javascript"></script>
        <script src="js/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="js/viewHandler.js" type="text/javascript"></script>
    </head>
    <body>
    	<div id="popup-container">
            <img src="images/close.png" id="close" onclick="hidePopup()" />
            <div id="popup-wrapper">
            </div>
        </div>
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
        <section class="profile-info-container row-start">
            <div class="profile-photo"></div>
            <div class="profile-info">
                <h1>${user.name}</h1>
                <span>@${user.username}</span>
                <p>${user.description}</p>
            </div>
        </section>
        <section class="search-container flex-row-between">
            <ul>
                <li><b>View Photos:</b></li>
                <li class="view-mode view-active" id="uploads" onclick="showUploads()">Uploads</li>
                <li class="view-mode" id="shared" onclick="showShared()">Shared with me</li>
            </ul>
            <form action="result" method="post" id="search-container">
            	<input type="text" placeholder="Search Exposure" id="search-bar" name="search" />
            </form>
        </section>
        <section class="photo-feed">
            <c:forEach items="${photoList}" var="photo" varStatus="status">
	            <div class="photo-wrapper" onclick="showPopup(${status.index}, '${photo.url}')">
                    <h5 class="phototitle"><c:out value="${photo.title}"/></h5>
                    <div class="owner">@${user.username}</div>
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
        <section class="shared-feed">
            <c:forEach items="${sharedList}" var="photo" varStatus="status">
	            <div class="photo-wrapper" onclick="showSharedPopup(${status.index}, '${photo.url}')">
                    <h5 class="phototitle sharedTitle"><c:out value="${photo.title}"/></h5>
                    <div class="owner sharedOwner">@${sharedUsernames[status.index]}</div>
	                <div class="thumbnail-container sharedThumbnail" style="background-image:url(<c:out value="${photo.url}"/>)"></div>
	                <p class="photo-description sharedDescription"><c:out value="${photo.description}" /></p>
            		<div class="tags-container">
	            		<c:forEach items="${sharedList[status.index].tags}" var="tag">
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