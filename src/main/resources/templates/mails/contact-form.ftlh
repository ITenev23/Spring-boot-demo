<!DOCTYPE>
<html>
<head>
    <title>Your identity has been verified - uHunt.Me</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0 " />
    <meta http-equiv="Content-Type"  content="text/html charset=UTF-8" />

    <link href="https://fonts.googleapis.com/css?family=Nunito&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Lora&display=swap" rel="stylesheet">

    <style>
        * {
            padding: 0;
            margin: 0;
            font-family: Lora;
        }
        body {
            font-size: 16px;
            background-color: #F4F7FA;
        }
        .wrapper {
            background-color: #F4F7FA;
            display: table;
            width: 100%;
            align-items: center;
            padding: 20px;
            text-align: center;
        }
        .wrapper .logo img{
            width: 200px;
            margin: 10px auto;
        }
        .verified-box {
            background-color: white;
            padding: 50px;
            margin: 20px auto;
            min-height: 200px;
        }
        .verified-box .icon-box img {
            width: 50px;
            padding-bottom: 20px;
            margin: 0 20px;
        }
        .verified-box .main-text h2 {
            font-family: Nunito;
            margin: 0 20px;
            font-size: 1.5em;
            color: black;
        }
        .verified-box p {
            font-size: 1em;
            color: black;
        }
        hr {
            border: 0;
            border-bottom: 1px solid rgb(218, 225, 233);
            margin: 20px 0;
        }
        .footer {
            margin: 15px 0;
            font-size: 12px;
            color: #AAA;
        }
        .button-box a {
            display: block;
            padding: 1rem 2rem;
            background-color: #B61E4E;
            border-radius: 3px;
            text-decoration: none;
            margin-top: 20px;
            color: #FFF;
            font-family: Nunito;
        }
    </style>
</head>
<body>
<table class="wrapper">
    <tbody>
    <tr>
        <td class="logo"><img src="https://uhunt.me/img/u-hunt-me-logo-horizontal.png" alt="logo" /></td>
    </tr>
    <tr>
        <td>
            <table class="verified-box">
                <tbody>
                <tr>
                    <td class="main-text"><h2>Получихте запитване от ${username}</h2></td>
                </tr>
                <tr>
                    <td><hr /></td>
                </tr>
                <tr>
                    <td><p>${question}</p></td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
    <tr>
        <td class="footer"><p>&copy; uHunt.Me 2020</p></td>
    </tr>
    </tbody>
</table>
</body>
</html>
