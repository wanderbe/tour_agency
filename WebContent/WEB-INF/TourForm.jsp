			<%@ include file="Login.jsp" %>
	
			<br>
			<h3>
				<c:out value="${message}">
				</c:out>
			</h3>
			<form action="${action}" method="post">
				<input type="hidden" name="newTourId" value="${newTour.id}"/><br/>
				
		        <label for="newTourName"><fmt:message key="PageTourForm.Name.Tour" />:</label>
		        <input type="text" name="newTourName" value="${newTour.name}"/><br/>
		        
		        <label for="newTourDescription"><fmt:message key="PageTourForm.Description" />:</label>
		        <textarea rows= "5" cols= "45" name= "newTourDescription">${newTour.description}</textarea><br>
		        
		        <label for="newTourRestTypeId"><fmt:message key="PageTourForm.Rest.type" />:</label>
		        <select name="newTourRestTypeId">
					<c:forEach items="${restTypeList}" var="item">
							<c:if test="${item.id == newTour.restType.id}">
			        			<c:set var="selected1" value="selected=&#34;selected&#34;"/>
			        		</c:if>
			        		<c:if test="${item.id != newTour.restType.id}">
			        			<c:set var="selected1" value=""/>
			        		</c:if>
						<option value="${item.id}" ${selected1}>
							${item.name}
						</option>
					</c:forEach>
				</select>
		        
		        <br>
		        <label for="newTourPlaces"><fmt:message key="PageTourForm.Places" />:</label>
		        <input type="number" name = "newTourPlaces" min="1" value="${newTour.places}"/><br/>
		        
		        <label for="newTourHotelId"><fmt:message key="PageTourForm.Hotel" />:</label>
		        <select name="newTourHotelId">
					<c:forEach items="${hotelList}" var="item">
						<c:if test="${item.id == newTour.hotel.id}">
		        			<c:set var="selected2" value="selected=&#34;selected&#34;"/>
		        		</c:if>
		        		<c:if test="${item.id != newTour.hotel.id}">
		        			<c:set var="selected2" value=""/>
		        		</c:if>
						<option value="${item.id}" ${selected2}>
							${item.name}
						</option>
					</c:forEach>
				</select>
		        
		        <br>
		        <label for="newTourPrice"><fmt:message key="PageTourForm.Price" />:</label>
		        <input type="number" name = "newTourPrice" min="0" value="${newTour.price}"/><br/>
		        
		        <label for="newTourHot"><fmt:message key="PageTourForm.Is.it.HOT" />:</label>
		        <fmt:message key="PageTourForm.YES" /><input type="radio" name="newTourHot" value="true" <c:if test="${newTour.itHot}">checked="checked"</c:if>>
	  			<fmt:message key="PageTourForm.NOT" /><input type="radio" name="newTourHot" value="false" <c:if test="${!newTour.itHot}">checked="checked"</c:if>>
		        <br>
		        <input type = "reset" value = "<fmt:message key="PageTourForm.RESET" />"/>
		        <input type = "submit" value = "<fmt:message key="PageTourForm.CONFIRM" />"/>
		    </form>	
	   
	   	</main>
   	</div>

</body>
</html>