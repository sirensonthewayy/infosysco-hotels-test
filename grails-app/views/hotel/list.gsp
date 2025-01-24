<!DOCTYPE html>
<html>
    <head>
        <title>Отели</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="${resource(dir: '', file: 'styles.css')}" type="text/css">

        <script>
            function showEditForm(id, name, countryId, stars, website) {
                document.getElementById("editForm").style.display = "block";
                document.getElementById("addForm").style.display = "none";
                document.getElementById("editHotelId").value = id;
                document.getElementById("editHotelName").value = name;
                document.getElementById("editCountryId").value = countryId;
                document.getElementById("editHotelStars").value = stars;
                document.getElementById("editHotelWebsite").value = website || '';
            }

            function showAddForm() {
                document.getElementById("editForm").style.display = "none";
                document.getElementById("addForm").style.display = "block";
            }

            function resetPageNumber() {
                const pageNumberField = document.querySelector('input[name="pageNumber"]');
                if (pageNumberField) {
                    pageNumberField.value = 1;
                }
            }

            document.addEventListener('DOMContentLoaded', function () {
                const message = "${message ?: ''}";
                if (message) {
                    alert(message);
                }
            });
        </script>

    </head>
    <body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

        <div class="container">

            <nav class="navbar navbar-light bg-light">
                <div class="container-fluid">
                    <div class="d-flex">
                        <a class="nav-link active me-5" aria-current="page" href="/hotel/list">Отели</a>
                        <a class="nav-link" href="/country/list">Страны</a>
                    </div>
                </div>
            </nav>

            <div id="addForm" style="display: block;">
                <h2>Добавить новый отель</h2>
                <g:form controller="hotel" action="save" method="post">
                    <input type="text" name="name" placeholder="Название отеля" required />
                    <select name="countryId" required>
                        <option value="" disabled selected>Выберите страну</option>
                        <g:each in="${countries}" var="country">
                            <option value="${country.id}">${country.name}</option>
                        </g:each>
                    </select>
                    <input type="number" name="stars" placeholder="Число звезд" min="1" max="5" required />
                    <input type="url" name="website" placeholder="Ссылка на сайт (опционально)" />
                    <button type="submit">Добавить</button>
                </g:form>
            </div>

            <div id="editForm" style="display: none;">
                <h2>Редактировать отель</h2>
                <g:form controller="hotel" action="update" method="PUT">
                    <input type="hidden" name="id" id="editHotelId" />
                    <g:textField type="text" name="name" id="editHotelName" value="${editHotel?.name}" placeholder="Название отеля" required="required"/>
                    <select name="countryId" id="editCountryId" value="${editHotel?.country?.id}" required>
                        <option value="-1" disabled>Выберите страну</option>
                        <g:each in="${countries}" var="country">
                            <option value="${country?.id}">${country?.name}</option>
                        </g:each>
                    </select>
                    <g:textField type="number" id="editHotelStars" name="stars" value="${editHotel?.stars}" placeholder="Число звезд" min="1" max="5" required="required" />
                    <g:textField type="url" name="website" id="editHotelWebsite" value="${editHotel?.website}" placeholder="Ссылка на сайт (опционально)" />
                    <button type="submit">Сохранить изменения</button>
                    <button type="button" onclick="showAddForm()">Отмена</button>
                </g:form>
            </div>

            <h2>Поиск отелей</h2>
            <g:form id="searchForm" controller="hotel" action="list" method="get">
                <input type="text" name="hotelName" placeholder="Введите название отеля" value="${params.hotelName ?: ''}" oninput="resetPageNumber()" />
                <select type="text" name="countryId" onchange="resetPageNumber()">
                    <option value=-1 ${params.countryId == -1 ? 'selected="selected"' : ''}>Любая страна</option>
                    <g:each in="${countries}" var="country">
                        <option value="${country.id}" ${params.countryId?.toLong() == country.id ? 'selected="selected"' : ''}>${country.name}</option>
                    </g:each>
                </select>
                <button type="submit">Найти</button>
                <div id="searchResults">
                    <table>
                        <thead>
                        <tr>
                            <th scope="col">Название отеля</th>
                            <th scope="col">Страна</th>
                            <th scope="col">Число звезд</th>
                            <th scope="col">Ссылка на сайт</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${hotels}" var="hotel">
                            <tr>
                                <td>${hotel.name}</td>
                                <td>${hotel.country.name}</td>
                                <td>${hotel.stars}</td>
                                <td><g:if test="${hotel.website}"><a href="${hotel.website}">Перейти</a></g:if>
                                    <g:else>Нет</g:else>
                                <td>
                                    <div class="options">
                                        <button type="button" onclick="showEditForm('${hotel.id}', '${hotel.name}', '${hotel.country.id}', '${hotel.stars}', '${hotel.website ?: ''}')">Редактировать</button>
                                        <g:form controller="hotel" action="delete" method="delete" style="display: inline;">
                                            <input type="hidden" name="id" value="${hotel.id}" />
                                            <button type="submit" onclick="return confirm('Вы уверены, что хотите удалить этот отель?')">Удалить</button>
                                        </g:form>
                                    </div>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
                <g:if test="${hotelCount > 10}">
                    <div class="pageInfo">
                        Номер страницы:
                        <input type="number" name="pageNumber" placeholder="Номер страницы" value="${params.pageNumber ?: 1}" min="1" max="${Math.ceil(hotelCount * 0.1)}" />
                        из ${(Integer) Math.ceil(hotelCount * 0.1)}
                    </div>

                    <button type="submit">Перейти</button>
                </g:if>
                <p>Всего найдено ${hotelCount} отелей</p>

            </g:form>

        </div>

    </body>
</html>