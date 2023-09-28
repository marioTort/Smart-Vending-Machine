<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="utente" type="it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Distributore" scope="session"/>

<jsp:include page="/WEB-INF/components/distributore/navbar.jsp" />

<div class="container my-5 text-center border border-1 rounded-3" id="container_grande">

    <main id="non_associato">

        <h1 class="mx-auto mt-5">Seleziona un metodo di acquisto</h1>

        <div class="container my-5">

            <div class="row mb-2 mx-0">

                <div class="col-sm-6">

                    <button id="contanti" type="button" class="btn w-100 mb-1" style="text-transform: none">

                        <div class="card mb-1">

                            <h3 class="card-header" style="background-color: #f7f7f7;">Acquista in Contanti</h3>

                            <div class="container my-4 mx-auto" style="max-width: 200px;">

                                <img src="${pageContext.request.contextPath}/img/distributore/cash.svg"
                                     class="card-img-top">

                            </div>

                            <div class="card-body">

                                <p class="card-text">Inserisci le monete ed ordina i prodotti</p>

                            </div>

                        </div>

                    </button>

                </div>

                <div class="col-sm-6">

                    <!-- Button trigger modal -->

                    <button id="associa" type="button" class="btn w-100 mb-1" data-bs-toggle="modal"
                            data-bs-target="#modal-associazione" style="text-transform: none;">

                        <!-- Button content -->

                        <div class="card mb-1">

                            <h3 class="card-header" style="background-color: #f7f7f7;">Acquista tramite App</h3>

                            <div class="container my-4 mx-auto" style="max-width: 200px">

                                <img src="${pageContext.request.contextPath}/img/distributore/qr.svg"
                                     class="card-img-top">

                            </div>

                            <div class="card-body">

                                <p class="card-text">Associa il distributore al tuo account ed ordina</p>

                            </div>

                        </div>

                    </button>

                    <!-- Modal -->

                    <div class="modal fade" id="modal-associazione" tabindex="-1" aria-labelledby="exampleModalLabel"
                         aria-hidden="true">

                        <div class="modal-dialog modal-dialog-centered modal-lg">

                            <!-- Modal content -->

                            <div class="modal-content">

                                <div class="modal-header justify-content-center" style="background-color: #f7f7f7;">

                                    <h1 class="modal-title fs-5">Associati al Distributore</h1>

                                </div>

                                <div class="modal-body">

                                    <p>Dal tuo dispositivo clicca sul pulsante "<mark>ASSOCIA DISTRIBUTORE</mark>"
                                        ed inserisci il codice sottostante:</p>

                                    <h1><strong>${utente.idUtente}</strong></h1>

                                </div>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </main>

    <main id="associato">

        <h1 class="mx-auto mt-5">Benvenuto! Seleziona un'azione</h1>

        <div class="container my-5">

            <div class="row mb-2 mx-0">

                <div class="col-sm-6">

                    <button id="dissocia" type="button" class="btn w-100 mb-1" style="text-transform: none">

                        <div class="card mb-1">

                            <h3 class="card-header" style="background-color: #f7f7f7;">Dissocia</h3>

                            <div class="container my-4 mx-auto" style="max-width: 200px;">

                                <img src="${pageContext.request.contextPath}/img/distributore/dissocia.svg"
                                     class="card-img-top">

                            </div>

                            <div class="card-body">

                                <p class="card-text">Dissociati dal distributore</p>

                            </div>

                        </div>

                    </button>

                </div>

                <div class="col-sm-6">

                    <button id="wallet" type="button" class="btn w-100 mb-1" style="text-transform: none">

                        <div class="card mb-1">

                            <h3 class="card-header" style="background-color: #f7f7f7;">Acquista</h3>

                            <div class="container my-4 mx-auto" style="max-width: 200px;">

                                <img src="${pageContext.request.contextPath}/img/distributore/wallet.svg"
                                     class="card-img-top">

                            </div>

                            <div class="card-body">

                                <p class="card-text">Acquista i prodotti</p>

                            </div>

                        </div>

                    </button>

                </div>

            </div>

        </div>

    </main>

</div>

<script type="text/javascript">

    $(document).ready(() => {

        if(!${utente.stato}) {

            $("#non_associato").hide();
            $("#associato").show();

        } else {

            $("#non_associato").show();
            $("#associato").hide();

        }

        $("#contanti").click((event) => {
            linkClick(event, $("#content"), "sceltaProdotto.jsp")
        })

        $("#wallet").click((event) => {
            linkClick(event, $("#content"), "sceltaProdotto.jsp")
        })

        $("#dissocia").click((event) => {

            event.preventDefault()
            event.stopPropagation()

            $.ajax({
                method: "GET",
                url: "${pageContext.request.contextPath}/dissocia-utenti",
                success: function (response) {
                    alert(response)
                },
                error: function(err) {
                    console.log(err)
                }

            })

        })

        setInterval(function () {

            $.ajax({
                method: "GET",
                url: "${pageContext.request.contextPath}/stato-utente",
                success: function (res) {

                    if(res !== "${utente.stato}") {
                        window.location.reload()
                    }

                },
                error: function(err) {
                    console.log(err)
                }
            })

        }, 2*1000)

    })

</script>