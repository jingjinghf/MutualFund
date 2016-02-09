<%-- Te-Hao Huang
	 ID: tehaoh
	 Task #7 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:include page="top_customer.jsp" />
<jsp:include page="left_customer.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<form method="post" action="viewtransactionhistory.do">
		<h2 class="page-header">View Transaction History</h2>
		<table class="table table-striped" border="1">
			<thead>
				<tr>
					<th width="65"><div align="center">Transaction ID</div></th>
					<th width="60"><div align="center">Date</div></th>
					<th width="60"><div align="center">Type</div></th>
					<th width="200"><div align="center">Fund Name</div></th>
					<th width="50"><div align="center">Number of Shares</div></th>
					<th width="80"><div align="center">Price Per Share</div></th>
					<th width="80"><div align="center">Transaction Value</div></th>

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
										<fmt:formatNumber value="${tran.price_perShare}"
											type="currency" />
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
		<jsp:include page="bottom.jsp" />