<%@ page import="java.util.List" %>
<%@ page import="it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Ordine" %>
<%@ page import="it.unipa.ing.info.lm32.tortorici.mario.tortorici.db.DBQueries" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="utente" type="it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Cliente" scope="session" />

<%  DBQueries dbQueries = new DBQueries();
    List<Ordine> lista = dbQueries.selectOrdiniEffettuati(utente.getEmail());%>

<div class="container my-5 text-center border border-1 rounded-3" id="container_grande">

    <ul class="nav nav-tabs my-3 ">

        <li class="nav-item">
            <a class="nav-link" href=# id="home">Home</a>
        </li>

        <li class="nav-item">
            <a class="nav-link active" aria-current="page">Ordini Effettuati</a>
        </li>

    </ul>

    <h1 class="mx-auto my-5">Ordini Effettuati</h1>

    <div class="container my-5">

        <%if(lista.isEmpty()) {%>

                <h2> Non hai ancora effettuato ordini con questo account! </h2>

            <% } else { %>

                <table class="table table-striped">

                    <thead>

                        <tr>

                            <th scope="col">ID ORDINE</th>
                            <th scope="col">DATA ORDINE</th>
                            <th scope="col">ID DISTRIBUTORE</th>
                            <th scope="col">ID PRODOTTO</th>
                            <th scope="col">IMPORTO ORDINE</th>

                        </tr>

                    </thead>

                    <tbody>

                        <% for(Ordine ordine: lista) {
                            request.setAttribute("ordine", ordine);%>

                            <tr>

                                <th scope="row">${ordine.idOrdine}</th>
                                <td>${ordine.dataOrdine}</td>
                                <td>${ordine.idDistributore}</td>
                                <td>${ordine.idProdotto}</td>
                                <td>${ordine.prezzo}</td>

                            </tr>

                        <% } %>

                    </tbody>

                </table>

            <%}%>

    </div>

</div>

<script type="text/javascript">

    $(document).ready(() => {

        $("#home").click((event) => {
            linkClick(event, $("#content"), "homeCliente.jsp")
        })

    })

</script>