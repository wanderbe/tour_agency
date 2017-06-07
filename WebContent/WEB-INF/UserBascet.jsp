			<%@ include file="Login.jsp" %>
			
			<c:set var="lastLocation" scope="session" value="bascet"></c:set>
			
			<h3>
				<c:if test="${bascet != null}">
					<c:out value="${message}">
						<fmt:message key="PageUserBascet.You.choosed" />:
					</c:out>	
				</c:if>
				
				<c:if test="${bascet == null}">
					<fmt:message key="PageUserBascet.Your.bascet.empty.choosee" /> <a href="tours"><fmt:message key="PageUserBascet.tour" /></a>
				</c:if>
			</h3>
			
			<c:if test="${bascet != null}">
			
				<table class="datatable">
					<tr class="tablehead">
						<th>
							<fmt:message key="PageUserBascet.Name.Tour" />
						</th>
						<th>
							<fmt:message key="PageUserBascet.Description" />
						</th>
						<th>
							<fmt:message key="PageUserBascet.Rest.Type" />
						</th>
						<th>
							<fmt:message key="PageUserBascet.Places" />
						</th>
						<th>
							<fmt:message key="PageUserBascet.Hotel.Name" />
						</th>
						<th>
							<fmt:message key="PageUserBascet.Hotel.Class" />
						</th>
						<th>
							<fmt:message key="PageUserBascet.Cost" />
						</th>
						<th>
							<fmt:message key="PageUserBascet.Amount.in.order" />
						</th>
						<th>
						</th>
					<tr>
					
					<c:forEach var="item" items="${bascet}">
					
						<c:if test = "${item.key.itHot}">
				         <c:set var="hot" value="hot" scope="page"/>
				        </c:if>
				        
				        <c:if test = "${!item.key.itHot}">
				         <c:set var="hot" value="" scope="page"/>
				        </c:if>
				        
						<tr class="${hot}">
							<td>
								${item.key.name}
							</td>
							<td>
								${item.key.description}
							</td>
							<td>
								${item.key.restType.name}
							</td>
							<td>
								${item.key.places}
							</td>
							<td>
								<a href="hotel?id=${item.key.hotel.id}">${item.key.hotel.name}</a>
							</td>
							<td>
								${item.key.hotel.hotelType.hotelClass}
							</td>
							<td>
								${item.key.price}
							</td>
							<td>
								${item.value}
							</td>
							<td>
								<c:if test="${!blockedUser}">
									<form action="deleteFromBascet" method="post">
										<input name="idTour" type="hidden" value="${item.key.id}">
										<input type="submit" value="<fmt:message key="PageUserBascet.DELETE" />">
									</form>
								</c:if>
							</td>
						<tr>
					</c:forEach>
				</table>
				
				<br>
				<c:if test="${!blockedUser}">
					<form class="confirmorder" action="order" method="post">
						<input type="submit" value="<fmt:message key="PageUserBascet.CONFIRM" />">
					</form>
				</c:if>
		
			</c:if>
		
		</main>
	</div>

</body>
</html>