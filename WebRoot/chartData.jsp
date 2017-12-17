<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
[
<c:forEach var="obj" items="${list}" varStatus="status" > 
	["${obj[1]}"<c:if test="${!status.last}">,</c:if>  ]
</c:forEach>
]