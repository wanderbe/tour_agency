				<%@ include file="Login.jsp" %>
			
				<table class="datatable">
					<tr class="tablehead">
						<th>
						<fmt:message key="PageRestTypes.Name.Rest.type" />
						</th>
						<th>
						<fmt:message key="PageRestTypes.Description.Rest.type" />
						</th>
					<tr>
					
					<c:forEach var="item" items="${restTypeList}">
						<tr class="todo">
							<td>
							${item.name}
							</td>
							<td>
							${item.description}
							</td>
						<tr>
					</c:forEach>
				</table>
		
			</main>
		</div>

</body>
</html>