<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="top_employee.jsp" />
<jsp:include page="left_employee.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">All Employee List</h1>
	<jsp:include page="error_list.jsp" />
	<div class="table-responsive">
		<table class="table table-striped" border="1">
			<thead>
				<tr>
					<th width="150"><div align="center">Employee First Name</div></th>
					<th width="150"><div align="center">Employee Last name</div></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="employee" items="${employeeList}">
					<tr>
						<td><div align="center">${employee.firstName }</div></td>
						<td><div align="center">${employee.lastName }</div></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<jsp:include page="bottom.jsp" />