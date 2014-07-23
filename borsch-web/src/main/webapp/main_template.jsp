<!DOCTYPE html>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><tiles:getAsString name="title"/></title>

    <tiles:insertAttribute name="style"/>
</head>
<body>
<div class="mainContainer">
    <header>
        <tiles:insertAttribute name="header"/>
    </header>
    <div id="content">
        <tiles:insertAttribute name="content"/>
    </div>

</div>

<footer>
    <tiles:insertAttribute name="footer"/>
</footer>

    <tiles:insertAttribute name="script"/>
</body>
</html>



