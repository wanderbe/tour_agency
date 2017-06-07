			<%@ include file="Login.jsp" %>
			
			<c:set var="lastLocation" scope="session" value="hotelcatalog"></c:set>
			
			<br><br>
			<table class="datatable">
				<tr class="tablehead">
					<th>
					<fmt:message key="PageHotel.Name.Hotel" />
					</th>
					<th>
					<fmt:message key="PageHotel.Description.Hotel" />
					</th>
					<th>
					<fmt:message key="PageHotel.Hotel.class" />
					</th>
				<tr>
				
				<c:forEach var="item" items="${hotelList}">
					<tr class="todo">
						<td>
						${item.name}
						</td>
						<td>
						${item.description}
						</td>
						<td>
						${item.hotelType.hotelClass}
						</td>
					<tr>
				</c:forEach>
			</table>
		</main>
	
	</div>

</body>
</html>