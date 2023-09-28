<%@ page import="it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Prodotto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="utente" type="it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Distributore" scope="session" />

<jsp:include page="/WEB-INF/components/distributore/navbar.jsp" />

<div class="container my-5 text-center border border-1 rounded-3" id="container_grande">

    <div class="progress mx-auto my-5">

        <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuemin="0"
             aria-valuemax="100" style="width: 50%"></div>

    </div>

    <h1 class="mx-auto">Seleziona uno snack</h1>

    <div id="monetine" class="row my-3 justify-content-center">

        <jsp:include page="/WEB-INF/components/distributore/monetine.jsp" />

    </div>

    <form class="my-5" id="form">

        <div class="row mb-2 mx-0 my-5">

            <% for(Prodotto prodotto: utente.getProdottiInseriti()) {
                request.setAttribute("prodotto", prodotto); %>

            <div class="col-sm-6 mt-3">

                <% if(prodotto.getTipo() == 1) { %>

                <!-- Button trigger modal -->

                <button type="button" name="prodotto" id="${prodotto.idProdotto}" value="${prodotto.idProdotto}" class="btn w-100 mb-1" data-bs-toggle="modal" data-bs-target="#modal-associazione${prodotto.idProdotto}"
                        style="text-transform: none;">

                    <!-- Button content -->

                    <div class="card mb-1">

                        <h3 class="card-header" style="background-color: #f7f7f7;">${prodotto.nome}</h3>

                        <div class="container my-4 mx-auto" style="max-width: 200px">

                            <img src="${pageContext.request.contextPath}/img/distributore/snacks/${prodotto.idProdotto}.svg"
                                 class="card-img-top">

                        </div>

                        <div class="card-body">

                            <p class="card-text">PREZZO: <strong>${prodotto.prezzo} €</strong></p>

                        </div>

                    </div>

                </button>

                <!-- Modal -->

                <div class="modal fade" id="modal-associazione${prodotto.idProdotto}" tabindex="-1" aria-labelledby="conferma_ordine"
                     aria-hidden="true">

                    <div class="modal-dialog modal-dialog-centered modal-lg">

                        <!-- Modal content -->

                        <div class="modal-content">

                            <div class="modal-header" style="background-color: #f7f7f7;">

                                <h1 class="modal-title fs-5" id="conferma_ordine">Conferma Ordine</h1>

                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>

                            </div>

                            <div class="modal-body">

                                <p>Prodotto Selezionato:</p>

                                <div class="row my-3 justify-content-center">

                                    <div class="container my-1 text-center border border-2 rounded" id="container_prodotto">

                                        <h3><strong>${prodotto.nome}</strong></h3>

                                        <div class="container my-4 mx-auto" style="max-width: 200px">

                                            <img src="${pageContext.request.contextPath}/img/distributore/snacks/${prodotto.idProdotto}.svg"
                                                 class="card-img-top">

                                        </div>

                                        <p>PREZZO: <strong>${prodotto.prezzo} €</strong></p>

                                    </div>

                                </div>

                            </div>

                            <div class="modal-footer">

                                <button type="submit" class="btn btn-outline-primary" id="conferma${prodotto.idProdotto}"
                                        data-bs-dismiss="modal">
                                    conferma
                                </button>

                                <button type="button" class="btn btn-outline-danger" data-bs-dismiss="modal">
                                    annulla
                                </button>

                            </div>

                        </div>

                    </div>

                </div>

                <% } %>

            </div>

            <% } %>

        </div>

        <jsp:include page="/WEB-INF/components/distributore/buttons.jsp" />

    </form>

</div>

<script type="text/javascript">

    $(document).ready(() => {

        let id;
        let creditoInserito = 0;

        if(${utente.stato}) {

            $("#monetine").show()

        } else {

            $("#monetine").hide()

        }

        $("#indietro").click((event) => {
            linkClick(event, $("#content"), "sceltaProdotto.jsp")
        })

        $("#annulla").click((event) => {
            linkClick(event, $("#content"), "homeDistributore.jsp")
        })

        $("button[type=button][name=prodotto]").click(function () {

            id = $(this).val()

        })

        $("#aggiungi_credito").click(function () {

            creditoInserito += parseFloat($("#credito").val())

            document.getElementById("credit").innerHTML = creditoInserito.toPrecision(2);

        })

        $("#form").submit((event) => {

            event.preventDefault()
            event.stopPropagation()

            $.ajax({
                type: 'POST',
                async: true,
                url: '${pageContext.request.contextPath}/effettua-pagamento',
                data: {
                    idProdotto: id,
                    creditoInserito: creditoInserito
                },
                success: function(res) {

                    let risposta = res.split("!")

                    alert(risposta[0]+"!")

                    if(risposta[1] !== "") {
                        creditoInserito = 0
                        linkClick(event, $("#content"), risposta[1])
                    }
                },
                error: function(err) {
                    console.log(err)
                }
            })
        })

    })

</script>