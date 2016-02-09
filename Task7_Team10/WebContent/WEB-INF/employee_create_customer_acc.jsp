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
	<h1 class="page-header">Create Customer Account</h1>

	<div class="table-responsive">
	<form action="createcustomeracc.do" method = "POST" >

		<table class="table table-striped">
			<tbody>
				<tr style='text-align: center;'>
					<td>User Name:</td>
					<td><input type="text" name="userName" value="${form.userName}" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td>First Name:</td>
					<td><input type="text" name="firstName" value="${form.firstName}" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td>Last Name:</td>
					<td><input type="text" name="lastName" value="${form.lastName}" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td>Address Line 1:</td>
					<td><input type="text" name="addrLine1" value="${form.addrLine1}" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td>Address Line 2:</td>
					<td><input type="text" name="addrLine2" value="${form.addrLine2 }" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td>City:</td>
					<td><input type="text" name="city" value="${form.city }" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td>State:</td>
					<td><input type="text" name="state" value="${form.state }" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td>Zip:</td>
					<td><input type="text" name="zip" value="${form.zip }" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td>Password:</td>
					<td><input type="password" name="password" value="" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td>Password Again:</td>
					<td><input type="password" name="confirmPassword" value="" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td colspan="2" align="center"><button type="submit"
						name="button" value="Create" class="btn btn-primary">Create</button></td>
				</tr>
<jsp:include page="error_list.jsp" />
			</tbody>
		</table>
		</form>
	</div>
</div>

<jsp:include page="bottom.jsp" />