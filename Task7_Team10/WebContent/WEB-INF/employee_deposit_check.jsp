<%--
  Created by IntelliJ IDEA.
  User: Bobfeng
  Date: 16/1/20
  Time: 下午8:49
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="top_employee.jsp" />
<jsp:include page="left_employee.jsp" />


<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Deposit Check</h1>
	<div class="table-responsive">
<form action="depositcheck.do" method="POST">
		<table class="table table-striped">
			<tbody>
				<tr style='text-align: center;'>
					<td>Customer User Name:</td>
					<td>${ sessionScope.modifyCustomer.customerName }</td>
					<input type="hidden" name="id"
						value="${sessionScope.modifyCustomer.customerId}" />
				</tr>
				<tr style='text-align: center;'>
					<td>Amount: $</td>
					<td><input type="text" name="amount" value="${depositcheckform.inputAmount}" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td>Confirm Amount: $</td>
					<td><input type="text" name="confirmAmount" value="${depositcheckform.confirmAmount}" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td colspan="2" align="center"><button type="submit"
						name="depositbutton" value="Deposit" class="btn btn-primary">Deposit</button></td>
				</tr>
				<jsp:include page="error_list.jsp" />
			</tbody>
		</table>
		</form>
	</div>
</div>


<jsp:include page="bottom.jsp" />