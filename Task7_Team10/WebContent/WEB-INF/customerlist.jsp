<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="top_employee.jsp" />
<jsp:include page="left_employee.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">All Customer List</h1>
	<jsp:include page="error_list.jsp" />
	<div class="table-responsive">
		<table class="table table-striped" border="1">
			<thead>
				<tr>
					<th width="150"><div align="center">Customer ID</div></th>
					<th><div align="center">Customer UserName</div></th>
					<th><div align="center">FirstName</div></th>
					<th><div align="center">LastName</div></th>
					<th><div align="center">Action</div></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="customer" items="${customerList}">
					<tr>
						<td><div align="center">${customer.customerId }</div></td>
						<td><div align="center">${customer.customerName }</div></td>
						<td><div align="center">${customer.firstName }</div></td>
						<td><div align="center">${customer.lastName }</div></td>
						<td><div align="center">
							<form action="employeeviewcustomeracc.do" method="POST">
								<input type="hidden" name="id" value="${customer.customerId }" />
								<button type="submit" name="button" value="View Account" class="btn btn-primary">View Account</button>
							</form>
						</div></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<jsp:include page="bottom.jsp" />