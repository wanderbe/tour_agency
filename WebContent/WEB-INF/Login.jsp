<%@ taglib prefix="ct" uri="ua.nure.sidorovich.jstl.customTag" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.nure.sidorovich.i18n.bundle" />
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="Tour.Agency.Gibraltar"/></title>

<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/reset.css">
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/general.css">
</head>
<body>
	<div class="wrapper">
		<header class="header">
			
			<div class="top">
				<h1>
					<fmt:message key="Tour.Agency.Gibraltar"/>
				</h1>
				
				<form>
			        <select id="language" name="language" onchange="submit()">
			            <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>English</option>
			            <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Руский</option>
			        </select>
			    </form>
		    </div>
		
			<div class="autorization">
				<c:if test = "${sessionScope.user.name == null}">
			        <fmt:message key="Hello" /> <fmt:message key="please" /> <a href="login"><fmt:message key="Login" /></a> <fmt:message key="or" /> <a href="register"><fmt:message key="Register" /></a>!
			        <br>
			        <div class="message"><c:out value="${messsage}"/></div>
					<form action="login" method="post">
						<input name="login" type="text" value="${login}">
						<input name="password" type="password">
						<input type="submit" value="<fmt:message key="LOGIN" />">
					</form>
			    </c:if>
			    <c:if test = "${sessionScope.user.name != null}">
			    	<br>
			    	<a href="bascet"><fmt:message key="basket" />[${sessionScope.bascetSize}]</a>
			    	
			    	<c:out value="${messsage}"/>
			    	${sessionScope.user.role.name} <a href="cabinet">${sessionScope.user.name}</a> 
			    	<a href="logout"><fmt:message key="Logout" /></a>
			    </c:if>
		    </div>
		    
		    
		    <div class="menu">
			    <ul>
					<li><a href="tours"><fmt:message key="Tours" /></a></li>
					<li><a href="hotelcatalog"><fmt:message key="Hotels" /></a></li>
					<c:if test="${managerRights}">
						<li><a href="users"><fmt:message key="Users" /></a></li>
						<li><a href="orders"><fmt:message key="Orders" /></a></li>
					</c:if>
					
				</ul>
				<a class="back" href="javascript:history.back()" title="Back" > <fmt:message key="Back" /></a>
			</div>
			
			<c:if test="${managerRights}">	
			<hr>
				<form class="discountform" action="newDiscountSystem" method="get">
					<fmt:message key="Current.Discount.System" />:
					<br>
					<fmt:message key="Registered" />:&nbsp${currentDiscount.registerTime} &nbsp&nbsp&nbsp <fmt:message key="Discount.step" />:&nbsp${currentDiscount.step}% &nbsp&nbsp&nbsp <fmt:message key="Max.discount" />:&nbsp${currentDiscount.maxPercent}%  
						<br>
						<input type="submit" value="<fmt:message key="NEW.DISCONT.SYSTEM" />"/>
				</form>
			</c:if>
			
		</header>
		
			
		<main class="main">
		
		
		
		