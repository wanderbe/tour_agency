				<%@ include file="Login.jsp" %>
				
				<c:set var="lastLocation" scope="session" value="tours"></c:set>
				
				<br><br>
				
				<c:if test="${adminRights}">
					<form action="newTour" method="get">				
						<input type="submit" value="<fmt:message key="PageTour.New.Tour" />">
					</form>
				</c:if>
				
				<fmt:message key="PageTour.Find.tours" />:
				
				<form action="tours" method="get">
				
			        <label for="lookingRestTypeId"><fmt:message key="PageTour.Rest.Type" /></label>
			        <select name="lookingRestTypeId">
			        
			   		    <c:if test="${0 == lookingRestTypeId}">
		        			<c:set var="selected1" value="selected=&#34;selected&#34;"/>
		        		</c:if>
		        		<c:if test="${0 != lookingRestTypeId}">
		        			<c:set var="selected1" value=""/>
		        		</c:if>
		        		
		        		
			        	<option value="-1" ${selected1}><fmt:message key="PageTour.All" /></option>
						<c:forEach items="${restTypeList}" var="item">
							<c:if test="${item.id == lookingRestTypeId}">
			        			<c:set var="selected2" value="selected=&#34;selected&#34;"/>
			        		</c:if>
			        		<c:if test="${item.id != lookingRestTypeId}">
			        			<c:set var="selected2" value=""/>
			        		</c:if>
							<option value="${item.id}" ${selected2}>
								${item.name}
							</option>
						</c:forEach>
					</select>
					
			        <label for="lookingStartPrice"><fmt:message key="PageTour.Price.start" /></label>
			        <input type="number" name ="lookingStartPrice" value="${lookingStartPrice}" step="100" min="0"/>
			        
			        <label for="lookingFinishPrice"><fmt:message key="PageTour.Price.finish" /></label>
			        <input type="number" name ="lookingFinishPrice" value="${lookingFinishPrice}" step="100" min="100" value="100000"/>
			        
			        <label for="lookingPersonsAmount"><fmt:message key="PageTour.For.persons" /></label>
			        <input type="number" name ="lookingPersonsAmount" value="${lookingPersonsAmount}" step="1" min="1"/>
			        
			        <label for="lookingHotelTypeId"><fmt:message key="PageTour.Hotel.type" /></label>
			        <select name="lookingHotelTypeId">
			        	<option value="-1" <c:if test="${0 == lookingHotelTypeId}">selected="selected"</c:if>><fmt:message key="PageTour.All" /></option>
						<c:forEach items="${hotelTypeList}" var="item">
							<c:if test="${item.id == lookingHotelTypeId}">
			        			<c:set var="selected3" value="selected=&#34;selected&#34;"/>
			        		</c:if>
			        		<c:if test="${item.id != lookingHotelTypeId}">
			        			<c:set var="selected3" value=""/>
			        		</c:if>
							<option value="${item.id}" ${selected3}>
								${item.hotelClass}
							</option>
						</c:forEach>
					</select>
					
			        <input type = "submit" value = "<fmt:message key="PageTour.Search" />"/>
			    </form>
				
				<br>
				<table class="datatable">
					<tr class="tablehead">
						<th>
						</th>
						<th>
							<fmt:message key="PageTour.Name.of.tour" />
						</th>
						<th>
							<fmt:message key="PageTour.Description" />
						</th>
						<th>
							<fmt:message key="PageTour.Rest.Type" />
						</th>
						<th>
							<fmt:message key="PageTour.Places" />
						</th>
						<th>
							<fmt:message key="PageTour.Hotel.Name" />
						</th>
						<th>
							<fmt:message key="PageTour.Hotel.type" />
						</th>
						<th>
							<fmt:message key="PageTour.Cost" />
						</th>
						<th>
						</th>
						<th>
						</th>
						<th>
						</th>
						<th>
						</th>
					<tr>
					
					<c:forEach var="item" items="${tourList}">
					
						<c:if test = "${item.itHot}">
				         <c:set var="hot" value="hot" scope="page"/>
				        </c:if>
				        
				        <c:if test = "${!item.itHot}">
				         <c:set var="hot" value="" scope="page"/>
				        </c:if>
				        
						<tr class="${hot}">
							<td>
								<c:if test = "${item.itHot}">
				        			 <c:out value="HOT"/>
				       		 	</c:if>
							</td>
							<td>
								${item.name}
							</td>
							<td>
								${item.description}
							</td>
							<td>
								${item.restType.name}
							</td>
							<td>
								${item.places}
							</td>
							<td>
								<a href="hotel?id=${item.hotel.id}">${item.hotel.name}</a>
							</td>
							<td>
								${item.hotel.hotelType.hotelClass}
							</td>
							<td>
								${item.price}
							</td>
							<td>
								<c:if test="${!blockedUser}">
									<form action="bascet" method="post">
										<input name="idTour" type="hidden" value="${item.id}">
										<input type="submit" value="<fmt:message key="PageTour.Order" />">
									</form>
								</c:if>
							</td>
							<td>
								<c:if test="${managerRights}">
									<form action="tours" method="post">
										<input name="idTour" type="hidden" value="${item.id}">
										<c:if test="${item.itHot}">
											<input name="setHot" type="hidden" value="false">
										</c:if>
										<c:if test="${!item.itHot}">
											<input name="setHot" type="hidden" value="true">
										</c:if>						
										<input type="submit" value="<fmt:message key="PageTour.Change.Hot" />">
									</form>
								</c:if>
							</td>
							<td>
								<c:if test="${adminRights}">
									<form action="updateTour" method="get">
										<input name="idTour" type="hidden" value="${item.id}">
										<input type="submit" value="<fmt:message key="PageTour.Update.Tour" />">
									</form>
								</c:if>
							</td>
							<td>
								<c:if test="${adminRights}">
									<form action="deleteTour" method="post">
										<input name="idTour" type="hidden" value="${item.id}">
										<input type="submit" value="<fmt:message key="PageTour.Delete.Tour" />">
									</form>
								</c:if>
							</td>
						<tr>
					</c:forEach>
				</table>
		
			</main>
		</div>

</body>
</html>