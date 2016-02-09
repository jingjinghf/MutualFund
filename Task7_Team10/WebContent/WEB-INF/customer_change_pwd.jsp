<%-- Te-Hao Huang
	 ID: tehaoh
	 Task #7 --%>

<jsp:include page="top_customer.jsp" />
<jsp:include page="left_customer.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Change Customer Password</h1>
	<div class="table-responsive">
		<form class="form-signin" method="post" action="customerchangepassword.do">
			<table class="table table-striped">
				<tbody>
					<tr style='text-align: center;'>
						<td><div align="left">Old Password:</div></td>
						<td><div align="left">
								<input type="password" name="originalPassword" value="" />
							</div></td>
					</tr>
					<tr style='text-align: center;'>
						<td><div align="left">New Password:</div></td>
						<td><div align="left">
								<input type="password" name="newPassword" value="" />
							</div></td>
					</tr>
					<tr style='text-align: center;'>
						<td><div align="left">New Password Again:</div></td>
						<td><div align="left">
								<input type="password" name="confirmNewPassword" value="" />
							</div></td>
					</tr>
					<tr style='text-align: center;'>
						<td colspan="2" align="center"><button type="submit"
							name="button" value="Submit" class="btn btn-primary">Submit</button></td>
					</tr>
					<jsp:include page="error_list.jsp" />
				</tbody>
			</table>
		</form>
	</div>
</div>
<jsp:include page="bottom.jsp" />
