<div class="container-fluid">
	<div class="row">
		<div class="col-sm-3 col-md-2 sidebar">
			<ul class="nav nav-sidebar">
				<li class="active"><h3>${sessionScope.employee.getFirstName()}
						${sessionScope.employee.getLastName()}<span class="sr-only">(current)</span>
					</h3></li>
				<div>
					<li><font size="3"><b>Customer Management</b></font></li>
					<li><a href="listallcustomer.do">All Customer List</a></li>
					<li><a href="createcustomeracc.do">Create New Customer</a></li> </br>
				</div>
				<div>
					<li><font size="3"><b>Employee Management</b></font></li>
					<li><a href="createemployeeacc.do">Create Employee Account</a></li>
					<li><a href="employeechangepassword.do">Change My Password</a></li> 
					<li><a href="listallemployee.do">All Employee List</a></li> </br>
				</div>
				<div>
					<li><font size="3"><b>Fund Management</b></font></li>
					<li><a href="createfund.do">Create Fund</a></li>
					<li><a href="transition.do">Transition Day</a></li> </br>
				</div>
			</ul>
		</div>