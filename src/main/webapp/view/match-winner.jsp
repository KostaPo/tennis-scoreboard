<html>
<head>
    <meta charset="utf-8">
    <title>Скрытое поле</title>
</head>
<body>
<form action="handler.php" method="post">
    <p><b>Напишите любимое слово и нажмите кнопку Отправить
        (никакие данные не будут передаваться на сервер!):</b></p>
    <p><input size="25" name="word">
        <input type="hidden" name="name" value="Vasya">
        <input type="hidden" name="password" value="pupkin"></p>
    <p><input type="submit" value="Отправить"></p>
</form>
</body>
</html>