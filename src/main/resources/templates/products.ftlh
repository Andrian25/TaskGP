<!DOCTYPE html>
<html>
<head>
    <title> HOTEL RESERVATION </title>
</head>
<body>
<H1> CLON BOOCING</H1><hr>
<H4> Гостиницы Минска </H4>
<#list hotels as hotel>
    <div>
        <p><b>${hotel.title}</b>${hotel.id} | <a href="/hotel/${hotel.id}"> Подробнее.... </a> </p>
    </div>
</#list>
<hr>
<h3> Создать новый отель</h3>
<form action="/product/create" method="post">
    Название отель: <input type="text" name="name"/><br><br>
    Страна: <input type="text" name="country"/><br><br>
    Город: <input type="text" name="city"/><br><br>
    Улица: <input type="text" name="street"/><br><br>
    Номер дома(здания): <input type="text" name="houseNumber"/><br><br>
    Индекс: <input type="text" name="postCode"/><br><br>
    Номер телефона: <input type="text" name="number"/><br><br>
    Майл: <input type="text" name="mail"/><br><br>
<input type="submit" value="Добавить отель"/>
</form>
</body>
</html>