<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="utente" type="it.unipa.ing.info.lm32.tortorici.mario.tortorici.models.Cliente" scope="session"/>

<div class="container my-5 text-center border border-1 rounded-3" id="container_grande">

  <ul class="nav nav-tabs my-3 ">

    <li class="nav-item">
      <a class="nav-link active" aria-current="page">Home</a>
    </li>

  </ul>

  <h1 class="mx-auto mt-3">Ciao, ${utente.nome}</h1>

  <div class="container my-5 text-center border rounded-5" id="container_saldo">

    <h2 class="mx-auto my-5" style="color: white;">Saldo attuale</h2>

    <h2 class="mx-auto my-5" style="color: white;">${utente.saldoDisponibile} €</h2>

  </div>

  <div class="container mb-5">

    <div class="row mx-0">

      <div class="col-sm-6">

        <button id="associaDistributore" type="button" class="btn btn-outline-success w-100 mb-1"
                style="height: 100px; border-radius: 20px;">
          associa distributore
        </button>

        <button id="dissociaDistributore" type="button" class="btn btn-outline-dark w-100 mb-1"
                style="height: 100px; border-radius: 20px;">
          dissocia distributore
        </button>

      </div>

      <div class="col-sm-6">

        <div class="dropdown-center dropdown">

          <button id="account" class="btn btn-outline-primary w-100 mb-1 dropdown-toggle" type="button"
                  data-bs-toggle="dropdown" aria-expanded="false" style="height: 100px; border-radius: 20px;">
            account
          </button>

          <ul class="dropdown-menu dropdown-menu-light w-100 text-center" style="text-transform: uppercase;">

            <li><a class="dropdown-item" id="ricarica" href="#">ricarica wallet</a></li>
            <li><a class="dropdown-item" id="modifica" href="#">modifica password</a></li>
            <li><a class="dropdown-item" id="ordini" href="#">ordini effettuati</a></li>

          </ul>

        </div>

      </div>

    </div>

    <div class="row mt-3 mx-0 justify-content-center">

      <div class="col-sm-6">

        <button id="logout" type="button" class="btn btn-outline-danger w-100" style="height: 100px; border-radius: 20px;">
          logout
        </button>

      </div>

    </div>

  </div>

</div>

<script type="text/javascript">

  $(document).ready(() => {

    if(!${utente.stato}) {

      $("#associaDistributore").hide();
      $("#dissociaDistributore").show();
      $("#logout").hide();
      $("#modifica").hide();

    } else {

      $("#associaDistributore").show();
      $("#dissociaDistributore").hide();

    }

    $("#associaDistributore").click((event) => {
      linkClick(event, $("#content"), "associaDistributore.jsp")
    })

    $("#dissociaDistributore").click((event) => {

      event.preventDefault()
      event.stopPropagation()

      $.ajax({
        method: "GET",
        url: "${pageContext.request.contextPath}/dissocia-utenti",
        success: function (res) {

          alert(res)
          linkClick(event, $("#content"), "homeCliente.jsp")

        },
        error: function(err) {
          console.log(err)
        }

      })

    })

    $("#ricarica").click((event) => {
      linkClick(event, $("#content"), "ricaricaWallet.jsp")
    })

    $("#modifica").click((event) => {
      linkClick(event, $("#content"), "modificaPassword.jsp")
    })

    $("#ordini").click((event) => {
      linkClick(event, $("#content"), "ordiniEffettuati.jsp")
    })

    $("#logout").click((event) => {

      event.preventDefault()

      $.ajax({
        method: "GET",
        url: "${pageContext.request.contextPath}/logout",
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