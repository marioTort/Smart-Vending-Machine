<%@ page import="it.unipa.ing.info.lm32.tortorici.mario.tortorici.db.DBQueries" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="utente" type="it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Cliente" scope="session" />

<% DBQueries dbQueries = new DBQueries();
    List<Integer> lista = dbQueries.selectIDDistributoriDisponibili();%>

<div class="container my-5 text-center border border-1 rounded-3" id="container_grande">

    <ul class="nav nav-tabs my-3 ">

        <li class="nav-item">
            <a class="nav-link" href=# id="home">Home</a>
        </li>

        <li class="nav-item">
            <a class="nav-link active" aria-current="page">Associa Distributore</a>
        </li>

    </ul>

    <h1 class="mx-auto my-5">Associa Distributore</h1>

    <%if(lista.isEmpty()) {%>

    <h2> Al momento non ci sono distributori disponibili! </h2>

    <% } else { %>

        <form class="my-5" id="form">

            <div class="row mb-1 justify-content-center">

                <div class="col-md-8 mb-5">

                    <label for="elenco" class="my-2">Lista distributori disponibili</label>
                    <select id="elenco" class="form-select" name="idDistributore" required>

                        <% for(Integer id: lista) {
                            request.setAttribute("integer", id);%>

                            <option value="${integer}">${integer}</option>

                        <% } %>

                    </select>

                </div>

            </div>

            <div class="row mb-1 justify-content-center">

                <div class="col-md-4">

                    <button type="submit" class="btn btn-outline-primary btn-block w-100">associa</button>

                </div>

                <div class="col-md-4">

                    <button type="reset" id="annulla" class="btn btn-outline-danger btn-block w-100">annulla</button>

                </div>

            </div>

        </form>

    <% } %>

</div>

<script type="text/javascript">

    $(document).ready(() => {

        $("#home").click((event) => {
            linkClick(event, $("#content"), "homeCliente.jsp")
        })

        $("#form").submit((event) => {

            let serializedData = $("#form").serialize()

            event.preventDefault()
            event.stopPropagation()

            $.ajax({
                type:'POST',
                async: true,
                url: "${pageContext.request.contextPath}/associa-distributore",
                data: serializedData,
                success: function (res) {

                    let risposta = res.split("!")

                    alert(risposta[0]+"!")

                    if(risposta[1] !== "") {
                        linkClick(event, $("#content"), risposta[1])
                    }

                },
                error: function(err) {
                    console.log(err)
                }
            })

        })

        $("#annulla").click((event) => {

            linkClick(event, $("#content"), "homeCliente.jsp")

        })

    })

</script>