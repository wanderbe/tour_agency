			<%@ include file="Login.jsp" %>
			
			<c:set var="lastLocation" scope="session" value="orders"></c:set>
			
			<c:if test="${managerRights}">
				<form class="orderselector" action="orders" method="get">
			        <select name="idOrderStatusForFilter" onchange="submit()">
			        	<option value="-1">All</option>
			        	<c:forEach items="${orderStatusList}" var="item">
			        		<c:if test="${item.id == idOrderStatusForFilter}">
			        			<c:set var="selected" value="selected=&#34;selected&#34;"/>
			        		</c:if>
			        		<c:if test="${item.id != idOrderStatusForFilter}">
			        			<c:set var="selected" value=""/>
			        		</c:if>
			           		<option value="${item.id}" ${selected}>${item.name}</option>
			            </c:forEach>
			        </select>
			    </form>
			    <c:if test="${idOrderStatusForFilter != null}">
					Users have HOT tour<ct:amountUsersHaveHot orderStatusId="${idOrderStatusForFilter}"/>
				</c:if>
			</c:if>
			
			<br>
			
			<h2>
				<fmt:message key="PageOrders.TableName.Orders" />:
			</h2>
		
			<c:if test="${!(empty orderList)}">
				<table class="datatable">
					<tr class="tablehead">
						<th>
							<fmt:message key="PageOrders.User.Name" />
						</th>		
						<th>
							<fmt:message key="PageOrders.Was.ordered" />
						</th>
						<th>
							<fmt:message key="PageOrders.Name.Tour" />
						</th>
						<th>
							<fmt:message key="PageOrders.Rest.Type" />
						</th>
						<th>
							<fmt:message key="PageOrders.Places" />
						</th>
						<th>
							<fmt:message key="PageOrders.Hotel.Name" />
						</th>
						<th>
							<fmt:message key="PageOrders.Hotel.Class" />
						</th>
						<th>
							<fmt:message key="PageOrders.Price" />
						</th>
						<th>
							<fmt:message key="PageOrders.Cost" />
						</th>
						<th>
							<fmt:message key="PageOrders.Status" />
						</th>
						<th>
						</th>
					<tr>
					
					<c:forEach var="item" items="${orderList}">
					
						<c:if test = "${item.tour.itHot}">
				         <c:set var="hot" value="hot" scope="page"/>
				        </c:if>
				        
				        <c:if test = "${!item.tour.itHot}">
				         <c:set var="hot" value="" scope="page"/>
				        </c:if>
				        
						<tr class="${hot}">
							<td>
								<a href="cabinet?user_id=${item.user.id}">${item.user.login}</a>
							</td>
							<td>
								${item.registerTime}
							</td>
							<td>
								${item.tour.name}
							</td>
							<td>
								${item.tour.restType.name}
							</td>
							<td>
								${item.tour.places}
							</td>
							<td>
								<a href="hotel?id=${item.tour.hotel.id}">${item.tour.hotel.name}</a>
							</td>
							<td>
								${item.tour.hotel.hotelType.hotelClass}
							</td>
							<td>
								${item.tour.price}
							</td>
							
							<c:if test="${item.cost == 0}">
								<td class="red">
									<a href="cabinet?user_id=${item.user.id}&interested_order_id=${item.id}"><fmt:message key="PageOrders.more" /></a>
								</td>
							</c:if>
							<c:if test="${item.cost != 0}">
								<td>
									${item.cost}
								</td>
							</c:if>
							
							<td>
								${item.orderStatus.name}
							</td>
							<td>
								<c:if test="${item.orderStatus.name eq 'registered'}">
									<form action="orders" method="post">
										<input name="idOrder" type="hidden" value="${item.id}">
										<select name="newOrderStatusId">
											<c:forEach items="${orderStatusList}" var="item2">
												<option value="${item2.id}">
													${item2.name}
												</option>
											</c:forEach>
										</select>
										<input type="submit" value="<fmt:message key="PageOrders.Change.status" />">
									</form>
								</c:if>					
							</td>
						<tr>
					</c:forEach>
				</table>
			</c:if>
		
		</main>
	</div>

</body>
</html>