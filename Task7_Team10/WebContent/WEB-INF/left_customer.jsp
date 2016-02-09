<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-3 col-md-2 sidebar">
			<ul class="nav nav-sidebar">
				<li class="active"><h3>${sessionScope.customer.firstName }
						${sessionScope.customer.lastName } <span class="sr-only">(current)</span>
					</h3></li>
				<div>
					<li><font size="3"><b>Address:</b></font></li>
					<li>${sessionScope.customer.addrLine1},
						${sessionScope.customer.addrLine2}ù,
						${sessionScope.customer.city}ù, ${sessionScope.customer.state}ù,
						${sessionScope.customer.zip}ù</li> 
						</br>
				</div>
				<div>
					<li><font size="3"><b>Cash Balance:</b></font></li>
					<li><fmt:setLocale value="en_US" /> <fmt:formatNumber
							value="${sessionScope.customer.readCashBalance()}"
							type="currency" /></li>
				</div>
			</ul>
			<ul class="nav nav-sidebar">
				<div></div>
				<div>
					<li><font size="3"><b>Cash Available:</b></font></li>
					<li><fmt:setLocale value="en_US" /> <fmt:formatNumber
							value="${sessionScope.customer.readCashAvailable()}"
							type="currency" />
					</li>
					</br>
				</div>
				<div>
					<li><a href="customerviewacc.do">View Account (Sell)</a></li>
					<li><a href="researchfund.do">Research Fund (Buy)</a></li>
					<li><a href="requestcheck.do">Check Request</a></li>
					<li><a href="customerchangepassword.do">Change Password</a></li>
					<li><a href="customerviewtransactionhistory.do">View
							Transaction History</a></li>
				</div>
			</ul>
		</div>