<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/WEB-INF/components/distributore/navbar.jsp" />

<div class="container my-5 text-center border border-1 rounded-3" id="container_grande">

    <div class="progress mx-auto my-5">

        <div class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%"></div>

    </div>

    <h1 class="mx-auto my-5">Prodotto erogato!</h1>

    <h2 id="logged" class="mx-auto mb-5">
        Ritira il prodotto e attendi di essere reindirizzato alla home del distributore.
        Lì potrai scegliere se acquistare di nuovo o dissociarti
    </h2>

    <h2 id="not_logged" class="mx-auto mb-5">
        Ritira il prodotto e attendi di essere reindirizzato alla home del distributore.
    </h2>

</div>

<script type="text/javascript">

    $(document).ready(() => {

        if(!${utente.stato}) {

            $("#not_logged").hide();
            $("#logged").show();


        } else {

            $("#not_logged").show();
            $("#logged").hide();

        }

        setTimeout(function () {

            $.ajax({

                type: 'GET',
                url: '${pageContext.request.contextPath}/richiedi-pagine',
                data: {
                    content: "homeDistributore.jsp"
                },

                success: function (data) {
                    $("#content").html(data)
                }

            })

        }, 5*1000)

    })

</script>
