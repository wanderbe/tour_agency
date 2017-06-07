				<%@ include file="Login.jsp" %>
		
				<h3>
					<c:out value="${message}">
						<fmt:message key="PageDiscountForm.Enter.new.discount.system" />:
					</c:out>
				</h3>
			
				<form action="newDiscountSystem" method="post">
			        <label for="discountStep"><fmt:message key="PageDiscountForm.Discount.step" />:</label>
			        <input type="number"name="discountStep"/>&nbsp %<br/>
			        <label for="maxDiscount"><fmt:message key="PageDiscountForm.Max.discount" />:</label>
			        <input type="number" name = "maxDiscount"/>&nbsp %<br/>
			        <input type = "submit" value = "<fmt:message key="PageDiscountForm.SET.NEW.DISCONT.SYSTEM" />"/>
			    </form>
		 	</main>
	 	</div>
	
</body>
</html>