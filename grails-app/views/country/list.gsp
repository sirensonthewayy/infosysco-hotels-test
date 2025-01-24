<html>
<head>

    <title>Страны</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="${resource(dir: '', file: 'styles.css')}" type="text/css">

    <script>
        function showEditForm(id, name, capital) {
            document.getElementById("editForm").style.display = "block";
            document.getElementById("addForm").style.display = "none";
            document.getElementById("editCountryId").value = id;
            document.getElementById("editCountryName").value = name;
            document.getElementById("editCountryCapital").value = capital;
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
                    <a class="nav-link me-5" aria-current="page" href="/hotel/list">Отели</a>
                    <a class="nav-link active" href="/country/list">Страны</a>
                </div>
            </div>
        </nav>

        <div id="addForm" style="display: block;">
            <h2>Добавить новую страну</h2>
            <g:form controller="country" action="save" method="post">
                <input type="text" name="name" placeholder="Название страны" required />
                <input type="text" name="capital" placeholder="Столица" required/>
                <button type="submit">Добавить</button>
            </g:form>
        </div>

        <div id="editForm" style="display: none;">
            <h2>Редактировать страну</h2>
            <g:form controller="country" action="update" method="PUT">
                <input type="hidden" name="id" id="editCountryId" />
                <g:textField type="text" name="name" id="editCountryName" value="${editCountry?.name}" placeholder="Название" required="required"/>
                <g:textField type="text" id="editCountryCapital" name="capital" value="${editCountry?.capital}" placeholder="Столица" required="required" />
                <button type="submit">Сохранить изменения</button>
                <button type="button" onclick="showAddForm()">Отмена</button>
            </g:form>
        </div>

        <h2>Поиск стран</h2>
        <g:form id="searchForm" controller="country" action="list" method="get">
            <input type="text" name="searchQuery" placeholder="Введите название страны" value="${params.name ?: ''}" oninput="resetPageNumber()" />
            <button type="submit">Найти</button>
            <div id="searchResults">
                <table>
                    <thead>
                    <tr>
                        <th scope="col">Страна</th>
                        <th scope="col">Столица</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${countries}" var="country">
                        <tr>
                            <td>${country.name}</td>
                            <td>${country.capital}</td>
                            <td>
                                <div class="options">
                                    <button type="button" onclick="showEditForm('${country.id}', '${country.name}', '${country.capital}')">Редактировать</button>
                                    <g:form controller="country" action="delete" method="delete" style="display: inline;">
                                        <input type="hidden" name="id" value="${country.id}" />
                                        <button type="submit" onclick="return confirm('Вы уверены, что хотите удалить эту страну? Удаление страны повлечет за собой и удаление ВСЕХ отелей в этой стране.')">Удалить</button>
                                    </g:form>
                                </div>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <g:if test="${countryCount > 10}">
                <div class="pageInfo">
                    Номер страницы:
                    <input type="number" name="pageNumber" placeholder="Номер страницы" value="${params.pageNumber ?: 1}" min="1" max="${Math.ceil(countryCount * 0.1)}" />
                    из ${(Integer) Math.ceil(countryCount * 0.1)}
                </div>

                <button type="submit">Перейти</button>
            </g:if>
            <p>Всего найдено ${countryCount} стран</p>

        </g:form>
    </div>
</body>
</html>

