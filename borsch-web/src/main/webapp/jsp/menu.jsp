<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="menu">
    <div class="menu-nav">
    <div id="menuWeekChooser" class="rounded-black-border brown-background brown-shadows pleasant-opacity">
        <div></div>
        <button value=-1>&#8656there</button>
        <button value=1>here&#8658</button>
    </div>

    <div id="daysMenu" class="rounded-black-border brown-background brown-shadows pleasant-opacity">
        <ul class="listWithoutDots">
            <li value="0">Monday</li>
            <li value="1">Tuesday</li>
            <li value="2">Wednesday</li>
            <li value="3">Thursday</li>
            <li value="4">Friday</li>
            <li value="5">Saturday</li>
            <li value="6">Sunday</li>
        </ul>
    </div>
    <div class="rounded-black-border brown-background brown-shadows pleasant-opacity">
        <button>Save</button>
        <button>Publish</button>
    </div>
    </div>
    <div id="menuItems" class="pleasant-opacity">
        <h2 class="rounded-black-border brown-background brown-shadows pleasant-opacity">Menu</h2>
        <div id="daySkeleton" class="rounded-black-border brown-shadows pleasant-opacity">
            <h3></h3>
            <ul class="listWithoutDots">
            </ul>
            <div class="rounded-black-border brown-background pleasant-opacity">
                <form name="addDish">
                    <input type="text" placeholder="dish name">
                    <input type="text" placeholder="price">
                    <button>Add dish</button>
                </form>
                <strong>Price must be a number!</strong>
            </div>
        </div>
    </div>
</div>

