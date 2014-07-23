<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Sign in </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="../assets/css/styles.css" rel="stylesheet">
</head>

<body>

    <div class="mainContainerLogin">
        <div class="coverSignInForm">
            <form class="formSignIn "  action="<c:url value='j_spring_security_check' />" method='POST'>
                <p class="font nameSignInForm">Please, sign in</p>
                <input type="text" class="inputText marginInputTextLogin" name='j_username'  placeholder="Login">
                <input type="password" class="inputText marginInputTextLogin" name='j_password' placeholder="Password">
                <div class="checkbox">
                    <input id="check1" type="checkbox" >
                    <label for="check1" class="label font marginCheckboxSignIn">Remember me</label>
                    <button class="button font " type="submit">Sign in</button>
                </div>
            </form>
        </div>
    </div>


<script src="http://code.jquery.com/jquery.js"></script>
<script src="../assets/js/bootstrap.min.js"></script>


</body>
</html>
