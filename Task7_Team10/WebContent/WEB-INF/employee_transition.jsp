<%--
  Created by IntelliJ IDEA.
  User: Bobfeng
  Date: 16/1/20
  Time: ä¸å8:51
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="top_employee.jsp" />
<jsp:include page="left_employee.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Transition Day</h1>
	<form method="post" action="transition.do">
	<h4 align="left"><jsp:include page="error_list.jsp"/></h4>
		<div class="table-responsive">
			<table class="table table-striped" border="1">
				<thead>
					<tr>
						<th><div align="center">Fund ID</div></th>
						<th><div align="center">Fund Name</div></th>
						<th><div align="center">Fund Symbol</div></th>
						<th><div align="center">Last Trading Price</div></th>
						<th><div align="center">Price Per Share</div></th>
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
							<td><div align="center"><input type="text" name="${fund.fundId}" value="" /></div></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<hr/>
		</div>
		
		<div class="row placeholders">
			<div class="col-xs-12 col-sm-12 placeholder">
				<h4 align="left">The last trading day: <fmt:formatDate value="${sessionScope.lasttradingday}" pattern="MM/dd/yyyy" /> </h4>
				<table>
					<tr>
						<td>
							<h4>Please Enter the new Trading Day (MM/dd/yyyy):</h4>
						</td>
					</tr>
				</table>

				<h4 align="left"><input type="text" name="newdate" value=""/></h4> 
				<h4 align="left"><button type="submit" name="button" value="Submit" class="btn btn-primary">Submit</button></h4>
			</div>
		</div>
	</form>
</div>
<jsp:include page="bottom.jsp" />
