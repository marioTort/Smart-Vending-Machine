<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="it">

    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Smart Vending Machine ☕</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
              crossorigin="anonymous">

        <!-- My CSS -->
        <link rel="stylesheet" href="stylesheet.css">

        <!-- Bootstrap JS scripts -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
                integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
                crossorigin="anonymous"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8"
                crossorigin="anonymous"></script>

        <!-- My JS scripts -->
        <script src="utils.js" type="text/javascript"></script>

    </head>

    <body>

        <main id="content">

            <% if (session.getAttribute("isLogged") == null) { %>
                <jsp:include page="WEB-INF/view/public/loginForm.jsp"/>
            <% } else if((boolean) session.getAttribute("isLoggedDistributore")) { %>
                <jsp:include page="WEB-INF/view/private/distributore/homeDistributore.jsp"/>
            <% } else { %>
                <jsp:include page="WEB-INF/view/private/cliente/homeCliente.jsp"/>
            <% } %>

        </main>

        <jsp:include page="WEB-INF/components/footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
                crossorigin="anonymous" />

    </body>

</html>