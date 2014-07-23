<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="coverHeader" style="margin-top: 0px;">
    <div class="coverHeaderInner">
        <div>
            <a href="/dashboard">
                <img src="assets/img/borsch.png" alt="borsch" class="image">
            </a>
        </div>
        <div class="menu">
            <ul>
                <sec:authorize access="hasAnyRole('ADMIN', 'USER')">
                <li>
                    <a href="/dashboard" class="font">DASHBOARD</a>
                </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ADMIN', 'USER')">
                <li>
                    <a href="/menu" class="font">MENU</a>
                </li>
                </sec:authorize>
                <sec:authorize access="hasRole('ADMIN')">
                <li>
                    <a href="/order" class="font" >ORDERS</a>
                </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('SUPER_ADMIN')">
                <li>
                    <a href="/users" class="font">PERMISSIONS</a>
                </li>
                </sec:authorize>
                <form action="/logout">
                    <input type="submit" class="button font marginLogOutButton" value="Logout">
                </form>
            </ul>
        </div>
        <div>
            <a class="borsch font"  href="/dashboard">BORSCH</a>
        </div>
    </div>
</div>