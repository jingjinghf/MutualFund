<%--
  Created by IntelliJ IDEA.
  User: Bobfeng
  Date: 16/1/20
  Time: 下午8:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 

<jsp:include page="top_employee.jsp" />
<jsp:include page="left_employee.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h2 class="page-header">View Customers Account</h2>
	<div class="row placeholders">
		<form class="form-signin" method="post" action="searchcustomeracc.do">
			<table class="table table-striped">
				<tbody>
					<tr style='text-align: center;'>
						<td>Customer Name:</td>
						<td>${sessionScope.modifyCustomer.firstName}
							${sessionScope.modifyCustomer.lastName }</td>
					</tr>
					<tr style='text-align: center;'>
						<td>Address:</td>
						<td>${sessionScope.modifyCustomer.addrLine1}
							${sessionScope.modifyCustomer.addrLine2 }
							${sessionScope.modifyCustomer.city }
							${sessionScope.modifyCustomer.state }
							${sessionScope.modifyCustomer.zip }</td>
					</tr>
					<tr style='text-align: center;'>
						<td>Cash Balance:</td>
						<td>
						<fmt:setLocale value="en_US" />
						<fmt:formatNumber value="${sessionScope.modifyCustomer.readCashBalance()}" type="currency" />
						</td>
					</tr>
					
					<tr style='text-align: center;'>
						<td>Cash Available:</td>
						<td>
						<fmt:setLocale value="en_US" />
						<fmt:formatNumber value="${sessionScope.modifyCustomer.readCashAvailable()}" type="currency" />
						</td>
					</tr>
					<tr style='text-align: center;'>
						<td>Last Trading Day:</td>
						<td><fmt:formatDate value="${lasttradingday }" pattern="MM/dd/yyyy" /></td>
					</tr>
				</tbody>
			</table>
		</form>


		<table class="table table-striped">
			<tbody>
				<tr style='text-align: center;'>
					<td>
						<form action="employeeviewtransactionhistory.do" method="POST">
							<button type="submit" value="Transaction History"
								class="btn btn-primary">Transaction History</button>
						</form>
					</td>
					<td>
						<form action="depositcheck.do" method="POST">
							<button type="submit" value="Deposit Check"
								class="btn btn-primary">Deposit Check</button>
						</form>
					</td>
					<td>
						<form action="resetpassword.do" method="POST">
							<button type="submit" value="Reset Password"
								class="btn btn-primary">Reset Password</button>
						</form>
					</td>
				</tr>
			</tbody>
		</table>

		<h3>${sessionScope.modifyCustomer.firstName} ${sessionScope.modifyCustomer.lastName }'s Fund Portfolio </h3>
							

		<table class="table table-striped"  border="1">
			<thead>
				<tr>
					<th><div align="center">Fund Name</div></th>
					<th><div align="center">Fund Symbol</div></th>
					<th><div align="center">Shares</div></th>
					<th><div align="center">Price</div></th>
					<th><div align="center">Value of Current Position</div></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="record" items="${customerFundList}">
					<tr>
						<td><div align="center">${record.fundName}</div></td>
						<td><div align="center">${record.fundSymbol}</div></td>
						
						<td><div align="left">
						<c:choose>
									<c:when test="${record.readShares() == 0}">
                                      --
                                    </c:when>
									<c:otherwise>
                                    <fmt:formatNumber type="number" minFractionDigits="3"
									value="${record.readShares()}" />
                                    </c:otherwise>
						</c:choose>  		
						</div></td>
						
						<td><div align="left">
						<c:choose>
									<c:when test="${record.price == 0}">
                                      --
                                    </c:when>
									<c:otherwise>
                                   <fmt:setLocale value="en_US" />
						            <fmt:formatNumber value="${record.price}" type="currency" />
                                    </c:otherwise>
						</c:choose>  
						</div></td>
						
						<td><div align="left">		
						<c:choose>
									<c:when test="${record.readAmount() == 0}">
                                      --
                                    </c:when>
									<c:otherwise>
                                    <fmt:setLocale value="en_US" />
						            <fmt:formatNumber value="${record.readAmount()}" type="currency" />
                                    </c:otherwise>
						</c:choose> 
						</div></td>
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<jsp:include page="bottom.jsp" />