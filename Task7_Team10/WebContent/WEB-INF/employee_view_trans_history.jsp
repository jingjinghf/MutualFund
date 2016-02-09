<%--
  Created by IntelliJ IDEA.
  User: Bobfeng
  Date: 16/1/20
  Time: 下午8:51
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="top_employee.jsp" />
<jsp:include page="left_employee.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">View
		${sessionScope.modifyCustomer.customerName}'s Transaction History</h1>
	<div class="row placeholders">
		<div class="col-xs-6 placeholder">
			<h2 align="left">${sessionScope.modifyCustomer.firstName}
				${sessionScope.modifyCustomer.lastName}</h2>
			<h4 align="left">${sessionScope.modifyCustomer.addrLine1}
				${sessionScope.modifyCustomer.addrLine2}</h4>
		</div>
		<div class="col-xs-6 col-sm-2 placeholder">
			<h4>Current Balance:</h4>
			<span class="text-muted">
			<fmt:setLocale value="en_US" />
			<fmt:formatNumber value="${sessionScope.modifyCustomer.readCashBalance()}" type="currency" />
			</span>
		</div>
		<div class="col-xs-6 col-sm-2 placeholder">
			<h4>Available Balance:</h4>
			<span class="text-muted"><fmt:setLocale value="en_US" />
			<fmt:formatNumber value="${sessionScope.modifyCustomer.readCashAvailable()}" type="currency" />
			</span>
		</div>
	</div>

	<div class="table-responsive">
		<table class="table table-striped" border="1">
			<thead>
				<tr>
					<th>Transaction ID</th>
					<th>Date</th>
					<th>Type</th>
					<th>Fund Name</th>
					<th>Number of Shares</th>
					<th>Price Per Share</th>
					<th>Transaction Value</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tran" items="${transactionList}">
					<tr>
						<td><div align="center">${tran.transactionId}</div></td>
						<td><div align="center">
								<c:choose>
									<c:when test="${tran.executionDate == null}">
                                      pending
                                    </c:when>
									<c:otherwise>
                                    <fmt:formatDate value="${tran.executionDate}"
									pattern="MM/dd/yyyy" />
                                    </c:otherwise>
								</c:choose>
						</div></td>
						<td><div align="center">${tran.transactionType}</div></td>
						<td><div align="center">
						
						<c:choose>
									<c:when test="${tran.fundName == null}">
                                      --
                                    </c:when>
									<c:otherwise>
                                    ${tran.fundName}
                                    </c:otherwise>
						</c:choose>
						
						</div></td>
						
						<td><div align="left">
						<c:choose>
									<c:when test="${tran.shares == 0}">
                                      --
                                    </c:when>
									<c:otherwise>
                                    <fmt:formatNumber type="number" minFractionDigits="3"
									value="${tran.shares}" />
                                    </c:otherwise>
						</c:choose>
						</div></td>

						<td><div align="left">
						<c:choose>
									<c:when test="${tran.price_perShare == 0}">
                                      --
                                    </c:when>
									<c:otherwise>
                                    <fmt:setLocale value="en_US" />
							        <fmt:formatNumber value="${tran.price_perShare}" type="currency" />
                                    </c:otherwise>
						</c:choose>
						</div></td>
						
						<td><div align="left">
						<c:choose>
									<c:when test="${tran.amount == 0}">
                                      --
                                    </c:when>
									<c:otherwise>
                                    <fmt:setLocale value="en_US" />
							        <fmt:formatNumber value="${tran.amount}" type="currency" />
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
