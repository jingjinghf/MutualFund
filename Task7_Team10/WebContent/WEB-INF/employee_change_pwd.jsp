<%--
  Created by IntelliJ IDEA.
  User: Bobfeng
  Date: 16/1/20
  Time: 下午8:48
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="top_employee.jsp" />
<jsp:include page="left_employee.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Change Password</h1>

	<div class="table-responsive">
		<form class="form-signin" method="post" action="employeechangepassword.do">
			<table class="table table-striped">
				<tbody>
					<tr style='text-align: center;'>
						<td>Old Password:</td>
						<td><input type="password" name="originalPassword" value="" /></td>
					</tr>
					<tr style='text-align: center;'>
						<td>New Password:</td>
						<td><input type="password" name="newPassword" value="" /></td>
					</tr>
					<tr style='text-align: center;'>
						<td>Confirm New Password:</td>
						<td><input type="password" name="confirmNewPassword" value="" /></td>
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