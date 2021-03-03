<!DOCTYPE html>
<html>
<head>
    <title>Speaker</title>
    <link rel="stylesheet" type="text/css" href ="css/homepage.css">
</head>
<body>
    <!--Header File
    -->
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
    <div class="Speaker">
        <h1>Speaker</h1>
        <div class = "Speakerinput">
            <img src = "icon/userID.png">
            <input type = "text" id = "Name" placeholder="Full Name">           
        </div>
        <div class = "Speakerinput">
            <img src = "icon/userID.png">
            <input type = "text" id = "doc" placeholder="Day Of Contact">           

        </div>
        <div class="Speakerinput">
            <img src="icon/cellphone.png">
            <input type = "text" id = "PhoneNumber" placeholder="Phome Number">           

        </div>
        <div class="Speakerinput">
            <img src="icon/emailICON.png">
            <input type = "text" id = "Email" placeholder="Email">           

        </div>

        <button id="Next">Next</button>
    </div>
    <script >

        document.getElementById("Next").onclick = function () {
        location.href = "TimeSlot.jsp";
        };
    </script>
</body>
</html>