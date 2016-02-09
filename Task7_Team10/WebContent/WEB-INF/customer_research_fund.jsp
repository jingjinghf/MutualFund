<%-- Te-Hao Huang
	 ID: tehaoh
	 Task #7 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:include page="top_customer.jsp" />
<jsp:include page="left_customer.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<form method="post" action="researchfund.do">
		<h1 class="page-header">Research Fund</h1>
		<div class="row placeholders">
			<div class="col-xs-6 col-sm-3 placeholder"></div>
		</div>
		<div class="table-responsive">
			<table class="table table-striped" border="1">
				<thead>
					<tr>
						<th width="50"><div align="center">Fund ID</div></th>
						<th width="200"><div align="center">Fund Name</div></th>
						<th width="100"><div align="center">Fund Symbol</div></th>
						<th width="100"><div align="center">Last Trading Price</div></th>
						<th width="40"><div align="center">Buy</div></th>
						<th width="50"><div align="center">Detail</div></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="fund" items="${viewfundlist}">
						<tr>
							<td><div align="center">${fund.fundId}</div></td>
							<td><div align="center">${fund.fundName}</div></td>
							<td><div align="center">${fund.symbol}</div></td>

							<td><div align="left">

									<c:choose>
										<c:when test="${fund.price == 0}">
                                      --
                                    </c:when>
										<c:otherwise>
											<fmt:setLocale value="en_US" />
											<fmt:formatNumber value="${fund.price}" type="currency" />
										</c:otherwise>
									</c:choose>
								</div></td>

							<td>
								<div align="center">
									<a href="buyfund.do?symbol=${fund.symbol}">Buy</a>
								</div>
							</td>

							<td>
								<div align="center">
									<a href="funddetail.do?symbol=${fund.symbol}">Detail</a>
								</div>
							</td>



						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<jsp:include page="bottom.jsp" />