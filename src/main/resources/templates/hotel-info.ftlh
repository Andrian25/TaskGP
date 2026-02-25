<!DOCTYPE html>
<html>
<head>
    <title> HOTEL RESERVATION </title>
</head>
<body>
<H1> HOTEL RESERVATION</H1><hr>
<H4> Подробная инфармация о отеле</H4>
    <b>Название отеля: </b>${hotels.name}<br>
    <b>Страна: </b>${hotels.country}<br>
    <b>Город: </b>${hotels.city}<br>
    <b>Улица: </b>${hotels.street}<br>
    <b>Номер здания: </b>${hotels.houseNumber}<br>
    <b>Индекс: </b>${hotels.postCode}<br>
    <b>Номер тел.: </b>${hotels.phone}<br>
    <b>Почта: </b>${hotels.email}<br>
<hr>
<h3> Создать новый отель</h3>
<form action="/hotels/delet/${hotels.id}" method="post">
    <input type="submit" value="Удалить отель"/>
</form>
</body>
</html>