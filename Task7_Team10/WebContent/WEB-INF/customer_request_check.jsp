<%-- Te-Hao Huang
	 ID: tehaoh
	 Task #7 --%>

<jsp:include page="top_customer.jsp" />
<jsp:include page="left_customer.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">
		<h1 class="page-header">Check Request</h1>
		<div class="table-responsive">
			<form action="requestcheck.do" method="POST">
				<table class="table table-striped">
					<tbody>
						<tr style='text-align: center;'>
							<td><div align="left">Request Amount: $</div></td>
							<td><div align="left">
									<input type="text" name="amount" value="${check}" />
								</div></td>
						</tr>
						<tr style='text-align: center;'>
							<td colspan="2" td align="center"><button type="submit" name="requestbutton"
								value="Request" class="btn btn-primary">Submit</button></td>
								
						</tr>
						<jsp:include page="error_list.jsp" />
					</tbody>
				</table>
			</form>
		</div>
</div>

<jsp:include page="bottom.jsp" />
