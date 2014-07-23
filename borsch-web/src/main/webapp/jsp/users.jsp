<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<input  class="inputTextSearch marginInputTextSearch" placeholder="Search for...">
<div class="tableUsers">
    <div class="notFound font">
         Not found!
    </div>
    <c:forEach var="user"  items="${users}">
        <div class="user">
            <div class="font fontSize nameAdmin">
                 <c:out  value="${user.name}"/>
            </div>
            <div class="checkbox">
                <c:choose>
                    <c:when test="${user.role =='ADMIN'}">
                        <input id="${user.id}" type="checkbox" checked>
                    </c:when>
                    <c:otherwise>
                        <input id="${user.id}" type="checkbox">
                    </c:otherwise>
                </c:choose>
                <label for="${user.id}" class="font labelAdmin">Admin</label>
            </div>
        </div>
    </c:forEach>
</div>

