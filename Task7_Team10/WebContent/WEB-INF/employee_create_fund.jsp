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
	<h1 class="page-header">Create Fund</h1>

	<div class="table-responsive">
		<form class="form-signin" method="post" action="createfund.do">
		
		<table class="table table-striped">
			<tbody>
				<tr style='text-align: center;'>
					<td>Fund Name:</td>
					<td><input type="text" name="fundName" value="${form.fundName }" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td>Symbol:</td>
					<td><input type="text" name="symbol" value="${form.symbol }" /></td>
				</tr>
				<tr style='text-align: center;'>
					<td colspan="2" align="center"><button type="submit"
						name="createfundbutton" value="Create" class="btn btn-primary">Create</button></td>	
				</tr>
				<jsp:include page="error_list.jsp" />
			</tbody>
		</table>
		</form>
	</div>
</div>

<jsp:include page="bottom.jsp" />