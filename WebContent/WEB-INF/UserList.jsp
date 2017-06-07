		
			<%@ include file="Login.jsp" %>
			
			<c:set var="lastLocation" scope="session" value="users"></c:set>
		
		
			<h3><fmt:message key="PageUserList.Users" />:</h3>
		
			<table class="datatable">
				<tr class="tablehead">
					<th>
						<fmt:message key="PageUserList.Name.User" />
					</th>
					<th>
						<fmt:message key="PageUserList.Birthday" />
					</th>
					<th>
						<fmt:message key="PageUserList.Email" />
					</th>
					<th>
						<fmt:message key="PageUserList.Phone" />
					</th>
					<th>
						<fmt:message key="PageUserList.Login" />
					</th>
					<th>
						<fmt:message key="PageUserList.Role" />
					</th>
					<th>
					</th>
					<th>
					</th>
				</tr>
				
				<c:forEach var="item" items="${userList}">
				        
					<tr>
						<td>
							<a href="cabinet?user_id=${item.id}">${item.name}</a>
						</td>
						<td>
							${item.birthday}
						</td>
						<td>
							${item.email}
						</td>
						<td>
							${item.phone}
						</td>
						<td>
							${item.login}
						</td>
						<td>
							${item.role.name}
						</td>
						<td>
							<c:if test="${adminRights}">
								<ct:checkBlocked idUser="${item.id}"/>
							</c:if>
						</td>
						<td>
							<c:if test="${adminRights}">
								<form action="users" method="post">
									<input name="idUser" type="hidden" value="${item.id}">
									<c:if test="true">
										<input name="setHot" type="hidden" value="false">
									</c:if>
									<c:if test="false">
										<input name="setHot" type="hidden" value="true">
									</c:if>						
									<input type="submit" value="<fmt:message key="PageUserList.Lock.Unlock.user" />">
								</form>
								<form action="cabinet" method="get">
									<input name="user_id" type="hidden" value="${item.id}">		
									<input type="submit" value="<fmt:message key="PageUserList.Send.message" />">
								</form>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		
		</main>
	</div>

</body>
</html>