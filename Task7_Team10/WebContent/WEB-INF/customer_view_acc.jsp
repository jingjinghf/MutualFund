<%-- Te-Hao Huang
	 ID: tehaoh
	 Task #7 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:include page="top_customer.jsp" />
<jsp:include page="left_customer.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h2 class="page-header">View My Account &amp; Portfolio</h2>
	<table class="table table-striped" border="1">
		<div>
			<h4>
				Last Trading Day:
				<fmt:formatDate value="${lasttradingday}" pattern="MM/dd/yyyy" />
			</h4>
		</div>
		<thead>
			<tr>
				<th width="150"><div align="center">Fund Name</div></th>
				<th width="60"><div align="center">Fund Symbol</div></th>
				<th width="59"><div align="center">Number of Shares</div></th>
				<th width="94"><div align="center">Last Trading Price</div></th>
				<th width="89"><div align="center">Value of Current
						Position</div></th>
				<th width="99"><div align="center">Buy</div></th>
				<th width="84"><div align="center">Sell</div></th>
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
									<fmt:formatNumber value="${record.readAmount()}"
										type="currency" />
								</c:otherwise>
							</c:choose>
						</div></td>

					<td><div align="center">
							<a href="buyfund.do?symbol=${record.fundSymbol}">Buy</a>
						</div></td>
					<td><div align="center">
							<a
								href="sellfund.do?symbol=${record.fundSymbol}&shares=${record.readShares()}">Sell</a>
						</div></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<jsp:include page="bottom.jsp" />