<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
[
<c:forEach var="u" items="${dataList}" varStatus="status" > 
	{"id":"${u.pkCustomer}", "orgName":"${u.customerName}","orgNum":"${u.customerNumber}","orgContactPhone":"${u.contactPhone}","orgCustomerContact":"${u.customerContact}" ,"orgWhetherNagtive":"${u.whetherNagtive}"} <c:if test="${!status.last}">,</c:if> 
</c:forEach>
]