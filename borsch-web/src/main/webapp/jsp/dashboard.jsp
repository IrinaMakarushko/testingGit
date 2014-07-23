<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id = "messages-page" class = "font">
    <div class = "pull-left sidebar wide-bordered background-common">
        <div class = "wrapper" id = "messages-board">
            <c:forEach var="message" items="${messages}">
                <c:choose>
                    <c:when test="${message.readStatus == false}">
                        <div class="cell new-message bottom-bordered">
                            <div class="pull-left author-cell full-height">
                                <c:out value="From: ${message.senderName}" default="Author is not loaded"/>
                            </div>
                            <div class = "pull-left margined-sides message-area" onclick=
                                    'getMessage(this, ${message.id})'>
                                <c:out value="${message.shortText}" default="Message is not loaded"/>
                            </div>
                            <button onclick="setMessageRead(this, ${message.id})"
                                        title = 'Mark as read' class = "action-button"></button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="cell old-message right-bordered bottom-bordered">
                            <div class="pull-left author-cell full-height">
                                <c:out value="From: ${message.senderName}" default="Author is not loaded"/>
                            </div>
                            <div class = "pull-left margined-sides message-area" onclick=
                                    'getMessage(this, ${message.id})'>
                                <c:out value="${message.shortText}" default="Message is not loaded"/>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>

    <sec:authorize access="hasRole('ADMIN')">
    <div class="sidebar pull-right wide-bordered background-common">
        <div class = 'wrapper' id = "users">
            <div class = "bottom-bordered">
                <input id = "search" class = "thick-bordered" placeholder="Input user's name">
                <button title = 'Write message to all'
                            class = "pull-right button font new-message-to-all-button">Write to all</button>

            </div>
            <c:forEach var="contact" items="${contacts}">
                <div class="cell bottom-bordered user-cell">
                    <div class="pull-left author-cell full-height">
                        <c:out value="${contact.role}" default="UserRole is not loaded"/>
                    </div>
                    <div class="pull-left full-height user-name">
                        <c:out value="${contact.name}" default="UserName is not loaded"/>
                    </div>
                    <input type = "hidden" class = "id-input" value = "${contact.id}">
                    <button title = 'Write message'
                            class = "pull-right button font new-message-button">Write</button>
                </div>
            </c:forEach>
        </div>
    </div>
    </sec:authorize>

    <sec:authorize access="hasRole('USER')">
    <button title = 'Write message to admins'
            class = "pull-right button font new-message-to-admins-button">Write to all</button>
    </sec:authorize>

    <div id = "createMessage" class = "pull-right hide">
        <form>
            <fieldset>
                <legend>New message</legend>
                <label id = "receiverName"></label>
                <input type = "hidden" id = "receiverID">
                <textarea  id = "new-message-textarea" name = "message" title="Enter message here"
                           class = "show background-common"></textarea>
                <input type="submit" class = "pull-left button font" value="Send" id = "sendMessageButton">
                <button class = "pull-right button font" id = "cancelButton">Cancel</button>
            </fieldset>
        </form>
    </div>
</div>

