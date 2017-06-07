<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" uri="ua.nure.sidorovich.jstl.customTag" %>

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
</head>
<body>

	
	
		<form>
	        <select id="language" name="language" onchange="submit()">
	            <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>English</option>
	            <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Руский</option>
	        </select>
	    </form>
	    
	     <ul>
			<li><a href="tours"><fmt:message key="Tours" /></a></li>
			<li><a href="hotelcatalog"><fmt:message key="Hotels" /></a></li>
		</ul>
		
	<main class="main">
	
		<h3>
			<c:out value="${message}">
				<fmt:message key="PageUserRegisterForm.Input.your.data" />:
			</c:out>
		</h3>
	
		
		<form action="register" method="post">
			<table>
				<tr>
					<td>
						<fmt:message key="PageUserRegisterForm.Name.User" />
					</td>
					<td>
						<input name="name" type="text" value="${name}"/><ct:validateName name="${name}"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="PageUserRegisterForm.Birthday" />
					</td>
					<td>
						<input name="birthday" type="date" value="${date}"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="PageUserRegisterForm.Email" />
					</td>
					<td>
						<input name="email" type="email" value="${email}"/><ct:validateEmail email="${email}"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="PageUserRegisterForm.Phone" />
					</td>
					<td>
						<input name=phone type="text" value="${phone}"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="PageUserRegisterForm.Login" />
					</td>
					<td>
						<input name=login type="text" value="${login}"/><ct:validateLogin login="${login}"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="PageUserRegisterForm.Password" />
					</td>
					<td>
						<input name=password type="password"/>
					</td>
				</tr>
			</table>
			
			<input type="reset" value="<fmt:message key="PageUserRegisterForm.RESET" />">
			<input type="submit" value="<fmt:message key="PageUserRegisterForm.UPDATE" />">
		
		</form>
		
		<a href="javascript:history.back()" title="Back" > <fmt:message key="Back" /></a>
	
	</main>

</body>
</html>