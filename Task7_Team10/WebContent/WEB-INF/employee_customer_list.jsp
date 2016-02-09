<%--
  Created by IntelliJ IDEA.
  User: Bobfeng
  Date: 16/1/20
  Time: 下午8:50
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="top_employee.jsp" />
<jsp:include page="left_employee.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

	<div class="table-responsive">
		<form class="form-signin" method="post" action="createemployeeacc.do">
		
		<table class="table table-striped">
			<tbody>
				<jsp:include page="customerlist.jsp" />
			</tbody>
		</table>
		</form>
	</div>
</div>

<jsp:include page="bottom.jsp" />

