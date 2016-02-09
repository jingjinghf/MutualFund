<%-- Te-Hao Huang
	 ID: tehaoh
	 Task #7 --%>

<jsp:include page="top_customer.jsp" />
<jsp:include page="left_customer.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Buy Fund</h1>
	<div class="table-responsive">
		<form class="form-signin" method="post" action="buyfund.do">

			<table class="table table-striped">
				<tbody>
					<tr style='text-align: center;'>
						<td><div align="left">Fund Symbol:</div></td>
						<td>
						    <div align="left">
								<input type="text" name="symbol" readonly
									value="${sessionScope.modifyFund.symbol}" />
							</div>
						</td>
					</tr>
					<tr style='text-align: center;'>
						<td><div align="left">Investment Amount: $</div></td>
						<td><div align="left">
								<input type="text" name="amount" value="${buyfundform.amounts}" />
							</div></td>
					</tr>
					<tr style='text-align: center;'>
						<td colspan="2" align="center"><button type="submit"
								class="btn btn-primary" name="buybutton" value="Place Order">Place
								Order</button></td>
					</tr>
					<jsp:include page="error_list.jsp" />
				</tbody>
			</table>
		</form>
	</div>
</div>
<jsp:include page="bottom.jsp" />