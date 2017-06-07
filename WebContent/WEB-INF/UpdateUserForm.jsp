	
			<%@ include file="Login.jsp" %>
	
			<h3>
				<c:out value="${message}">
					<fmt:message key="PageUserUpdateForm.Please.enter.your.new.data" />:
				</c:out>
			</h3>
		
			<form action="updateUser" method="post">
		        <label for="name"><fmt:message key="PageUserUpdateForm.Name.User" />:</label>
		        <input type="text" name="updatedUserName" value="${user.name}"/><ct:validateName name="${name}"/><br/>
		        
		        <label for="updatedBirthday"><fmt:message key="PageUserUpdateForm.Birthday" />:</label>
		        <input type="date" name = "updatedBirthday" value="${user.birthday}"/><br/>
		        
		        <label for="updatedEmail"><fmt:message key="PageUserUpdateForm.Email" />:</label>
		        <input type="email" name = "updatedEmail" value="${user.email}"/><ct:validateEmail email="${email}"/><br/>
		        
		        <label for="updatedPhone"><fmt:message key="PageUserUpdateForm.Phone" />:</label>
		        <input type="phone" name = "updatedPhone" value="${user.phone}"/><br/>
		        
		        <label for="updatedLogin"><fmt:message key="PageUserUpdateForm.Login" />:</label>
		        <input type="text" name = "updatedLogin" value="${user.login}"/><ct:validateLogin login="${login}"/><br/>
		        
		        <label for="updatedPassword"><fmt:message key="PageUserUpdateForm.New.password" />:</label>
		        <input type="password" name = "updatedPassword" value=""/><br/>
		        
		        <label for="oldPassword"><fmt:message key="PageUserUpdateForm.Confirm.update.by.your.password" />:</label>
		        <input type="password" name = "oldPassword" value=""/><br/>
		        
		        <input type = "reset" value = "<fmt:message key="PageUserUpdateForm.RESET" />"/>
		        <input type = "submit" value = "<fmt:message key="PageUserUpdateForm.UPDATE" />"/>
		    </form>	
		    
	   	</main>
   	</div>

</body>
</html>