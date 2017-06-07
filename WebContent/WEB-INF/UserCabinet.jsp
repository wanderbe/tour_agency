			<%@ include file="Login.jsp" %>
			
			<c:set var="lastLocation" scope="session" value="cabinet?user_id=${user.id}"></c:set>
			
			
			<table class="usercabinetdatamail">
			  <tr>
			    <td>
			    
			    	<table class="userdata">
						<tr>
							<td>
								<fmt:message key="PageUserCabinet.Name.User" />:
							</td>
							<td>
								${user.name}
							</td>
						</tr>
						<tr>
							<td>
								<fmt:message key="PageUserCabinet.Birthday" />:
							</td>
							<td>
								${user.birthday}
							</td>
						</tr>
						<tr>
							<td>
								<fmt:message key="PageUserCabinet.Email" />:
							</td>
							<td>
								${user.email}
							</td>
						</tr>
						<tr>
							<td>
								<fmt:message key="PageUserCabinet.Phone" />:
							</td>
							<td>
								${user.phone}
							</td>
						</tr>
						<tr>
							<td>
								<fmt:message key="PageUserCabinet.Login" />:
							</td>
							<td>
								${user.login}
							</td>
						</tr>
						<tr>
							<td>
								<fmt:message key="PageUserCabinet.Role" />:
							</td>
							<td>
								${user.role.name}
							</td>
						</tr>
					</table>
			 
			 		<c:if test="${!blockedUser}">
					<c:if test="${user.id == sessionScope.user.id}">
						<form action="updateUser" method="get">
							<input type="submit" value="<fmt:message key="PageUserCabinet.Update.your.Data" />">
						</form>
					</c:if>
				</c:if>
				
				
			 
			    </td>
			    <td>
			    
				    <c:if test="${managerRights}">
				    	<c:if test="${user.id != sessionScope.user.id}">
							<form action="sendMail" method="post">
								<input type="hidden" name="user_id_for_message" value="${user.id}"/>
						        <label for="subject"><fmt:message key="PageUserCabinet.subject" />:</label><br>
						        <input type="text" name = "subject" value="<fmt:message key="PageUserCabinet.Message.from.Tour.Agency" />"/><br/>
						        <label for="message"><fmt:message key="PageUserCabinet.message" />:</label><br>
						        <textarea rows= "5" cols= "45" name= "message"><fmt:message key="PageUserCabinet.Dear" /> ${user.name}!!!</textarea><br/>
						        <input type = "submit" value = "<fmt:message key="PageUserCabinet.Send.mail" />"/>
						    </form>
						 </c:if>
				    </c:if>
			    
			    </td>
			    <td class="userdiscount">
			    	<fmt:message key="PageUserCabinet.Paid.orders" />: ${paidOrders}
					<br>
					<fmt:message key="PageUserCabinet.Discount" />:
					<c:if test="${(paidOrders * currentDiscount.step) > currentDiscount.maxPercent}">
						${currentDiscount.maxPercent} % <fmt:message key="PageUserCabinet.MAX.DISCOUNT" />
						<c:set var="currentUserDiscount" value="${currentDiscount.maxPercent}" scope="page"/>
					</c:if>
					<c:if test="${(paidOrders * currentDiscount.step) <= currentDiscount.maxPercent}">
						${paidOrders * currentDiscount.step} %
						<c:set var="currentUserDiscount" value="${paidOrders * currentDiscount.step}" scope="page"/>
					</c:if>
			    </td>
			  </tr>
			</table>
			
					<h3>
						<fmt:message key="PageUserCabinet.Orders" />:
					</h3>
					
				<table class="datatable">
					<tr class="tablehead">
						<th>
							<fmt:message key="PageUserCabinet.Was.ordered" />
						</th>
						<th>
							<fmt:message key="PageUserCabinet.Name.Tour" />
						</th>
						<th>
						</th>
						<th>
							<fmt:message key="PageUserCabinet.Description" />
						</th>
						<th>
							<fmt:message key="PageUserCabinet.Rest.Type" />
						</th>
						<th>
							<fmt:message key="PageUserCabinet.Places" />
						</th>
						<th>
							<fmt:message key="PageUserCabinet.Hotel.Name" />
						</th>
						<th>
							<fmt:message key="PageUserCabinet.Hotel.Class" />
						</th>
						<th>
							<fmt:message key="PageUserCabinet.Price" />
						</th>
						<th>
							<fmt:message key="PageUserCabinet.Cost" />
						</th>
						<th>
							<fmt:message key="PageUserCabinet.Status" />
						</th>
						<th>
						</th>
					</tr>
					<c:forEach var="item" items="${orderList}">
					
						<c:if test = "${item.tour.itHot}">
				         <c:set var="hot" value="hot" scope="page"/>
				        </c:if>
				        
				        <c:if test = "${!item.tour.itHot}">
				         <c:set var="hot" value="" scope="page"/>
				        </c:if>
				        
				        <c:if test = "${interested_order_id == item.id}">
				         <c:set var="hot" value="${hot.concat(' bold')}" scope="page"/>
				        </c:if>
				        	
								<tr class="${hot}" >
									<td>
										${item.registerTime}
									</td>
									<td>
										${item.tour.name}
									</td>
									<td>
										<c:if test = "${item.tour.itHot}">
						        			 <c:out value="HOT"/>
						       		 	</c:if>
									</td>
									<td>
										${item.tour.description}
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
									
									<c:if test="${item.cost == 0 && item.orderStatus.name eq 'registered'}">
										<td class="red">
											${item.tour.price * (1 - currentUserDiscount / 100)}
										</td>
									</c:if>
									<c:if test="${item.cost != 0 || !(item.orderStatus.name eq 'registered')}">
										<td>
											${item.cost}
										</td>
									</c:if>
									
									<td>
										${item.orderStatus.name}
									</td>
									<td>
										<c:if test="${managerRights}">
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
													<input type="submit" value="<fmt:message key="PageUserCabinet.Change.status" />">
												</form>
											</c:if>	
										</c:if>	
									</td>
								</tr>
							
					</c:forEach>
				</table>
		
		</main>
	</div>

</body>
</html>