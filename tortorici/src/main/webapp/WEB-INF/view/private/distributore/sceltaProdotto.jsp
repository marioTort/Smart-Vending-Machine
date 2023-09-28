<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/WEB-INF/components/distributore/navbar.jsp" />

<div class="container my-5 text-center border border-1 rounded-3" id="container_grande">

    <div class="progress mx-auto my-5">

        <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuemin="0" aria-valuemax="100" style="width: 25%"></div>

    </div>

    <h1 class="mx-auto">Seleziona una tipologia di prodotto</h1>

    <div class="container my-5">

        <div class="row mb-2 mx-0">

            <div class="col-sm-6">

                <button id="snack" type="button" class="btn w-100 mb-1"
                        style="text-transform: none">

                    <div class="card mb-1">

                        <h3 class="card-header" style="background-color: #f7f7f7;">Snack</h3>

                        <div class="container my-4 mx-auto" style="max-width: 200px;">

                            <img src="${pageContext.request.contextPath}/img/distributore/snacks/snack.svg"
                                 class="card-img-top">

                        </div>

                    </div>

                </button>

            </div>

            <div class="col-sm-6">

                <button id="bevanda" type="button" class="btn w-100 mb-1"
                        style="text-transform: none">

                    <div class="card mb-1">

                        <h3 class="card-header" style="background-color: #f7f7f7;">Bevanda</h3>

                        <div class="container my-4 mx-auto" style="max-width: 200px">

                            <img src="${pageContext.request.contextPath}/img/distributore/bevande/bevanda.svg"
                                 class="card-img-top">

                        </div>

                    </div>

                </button>

            </div>

        </div>

        <jsp:include page="/WEB-INF/components/distributore/buttons.jsp" />

    </div>

</div>

<script type="text/javascript">

    $(document).ready(() => {

        $("#snack").click((event) => {
            linkClick(event, $("#content"), "elencoSnacks.jsp")
        })

        $("#bevanda").click((event) => {
            linkClick(event, $("#content"), "elencoBevande.jsp")
        })

        $("#indietro").click((event) => {
            linkClick(event, $("#content"), "homeDistributore.jsp")
        })

        $("#annulla").click((event) => {
            linkClick(event, $("#content"), "homeDistributore.jsp")
        })

    })

</script>