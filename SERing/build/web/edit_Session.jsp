<!DOCTYPE html>
<html>
<head>
    <title>Session</title>
    <link rel="stylesheet" type="text/css" href ="css/homepage.css">
</head>
<body>
    <div class="header">
        <a href = "index.jsp"><h1>Boston Code Camp  </h1></a>
        <nav>
            <ul>
                <!--My favorite html.-->
                <!--Back to HomePage.html-->
                <li><a href="index.jsp">Session List</a></li>
                <!--Link to Session HTML-->
               
            </ul>
        </nav>
        <h3>Black Team</h3>
    </div>
    <div class="Session">
        <h1>Session</h1>
        
        <div class="Sessioninput">
            <img src="icon/SessionID.jpg">
            <input type="text" id="SessionName" placeholder="Session Name" required>
        </div>

        <div class="Sessioninput">
            <img src="icon/userID.png">
            <input type="text" id= "FirstName" placeholder="Speaker Name" required >
        </div>

        <div class="Sessioninput">
            <img src="icon/RoomID.png">
            <input type="text" id= "RoomNumber" placeholder="Room Number" required >
        </div>
        <button id="submitBtn1">Next</button>
    </div>

    <footer>
        <span>Black Team<br>Copyright &copy; 2019-2020 </span>
    </footer>
<script >
    document.getElementById("submitBtn1").onclick = function () {
        var first = document.getElementById("FirstName").value;
        localStorage.setItem("First", first);
        console.log(first)
        location.href = "Speaker.jsp";
    };
    
    

    
</script>
</body>
</html>