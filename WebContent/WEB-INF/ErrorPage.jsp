<%@ page language="java" isErrorPage="true" import="java.io.*" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
     prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"
     prefix="fmt" %>
     
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.nure.sidorovich.i18n.bundle" />

<html>
<head>
	<title><fmt:message key="Tour.Agency.Gibraltar"/></title>
	
</head>
	<body bgcolor="white">
	
		<form>
	        <select id="language" name="language" onchange="submit()">
	            <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>English</option>
	            <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Руский</option>
	        </select>
	    </form>
			
		<c:if test = "${sessionScope.user.name == null}">
	        <fmt:message key="Hello" /> <fmt:message key="please" /> <a href="login"><fmt:message key="Login" /></a> <fmt:message key="or" /> <a href="register"><fmt:message key="Register" /></a>!
	        <br>
	        <c:out value="${messsage}"/>
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
	    
	    <ul>
			<li><a href="tours"><fmt:message key="Tours" /></a></li>
			<li><a href="hotelcatalog"><fmt:message key="Hotels" /></a></li>
			<c:if test="${managerRights}">
				<li><a href="users"><fmt:message key="Users" /></a></li>
				<li><a href="orders"><fmt:message key="Orders" /></a></li>
			</c:if>
			
		</ul>
		
		<c:if test="${managerRights}">	
			<fmt:message key="Current.Discount.System" />:
			<br>
			<fmt:message key="Registered" />:&nbsp${currentDiscount.registerTime} &nbsp&nbsp&nbsp <fmt:message key="Discount.step" />:&nbsp${currentDiscount.step}% &nbsp&nbsp&nbsp <fmt:message key="Max.discount" />:&nbsp${currentDiscount.maxPercent}%  
			<form action="newDiscountSystem" method="get">
				<input type="submit" value="<fmt:message key="NEW.DISCONT.SYSTEM" />"/>
			</form>
		</c:if>
		<a href="javascript:history.back()" title="Back" > <fmt:message key="Back" /></a>
	
		<h3>
			<fmt:message key="ErrorPage.Error"/>
		</h3>
		<p>
		<h3>
			<%=response.getStatus() %>
		</h3>
		
		<fmt:message key="ErrorPage.Message"/>:
		<c:out value="${requestScope['javax.servlet.error.message']}"/>
		
	</body>
</html>