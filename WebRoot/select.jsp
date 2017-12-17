<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<select class="combox" name="${param.inputName}">
	<option value="${pro.sellPrice }">${pro.sellPrice }</option>
	<option value="<c:out value="${pro.sellPrice2 }"></c:out>">${pro.sellPrice2 }</option>
	<option value="<c:out value="${pro.sellPrice3 }"></c:out>">${pro.sellPrice3 }</option>
</select>
