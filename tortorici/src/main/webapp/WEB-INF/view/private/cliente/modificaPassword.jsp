<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container my-5 text-center border border-1 rounded-3 " id="container_principale">

  <ul class="nav nav-tabs my-3 ">

    <li class="nav-item">
      <a class="nav-link" href=# id="home">Home</a>
    </li>

    <li class="nav-item">
      <a class="nav-link active" aria-current="page">Modifica Password</a>
    </li>

  </ul>

  <h1 class="mx-auto my-5">Modifica password</h1>

  <form class="my-5" id="form">

    <div class="row mb-1 justify-content-center">

      <div class="col-md-10 my-2">

        <input type="password" name="vecchiaPassword" id="vecchiaPassword" class="form-control mb-3" minlength="8"
               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$" placeholder="Password Precedente" required>

      </div>

    </div>

    <div class="row mb-1 justify-content-center">

      <div class="col-md-10 mb-2">

        <input type="password" name="nuovaPassword" id="nuovaPassword" class="form-control mb-3" placeholder="Nuova Password" required>

      </div>

    </div>

    <div class="row mb-1 justify-content-center">

      <div class="col-md-5">

        <button type="submit" class="btn btn-outline-primary btn-block w-100">modifica</button>

      </div>

      <div class="col-md-5">

        <button type="reset" id="annulla" class="btn btn-outline-danger btn-block w-100">annulla</button>

      </div>

    </div>

  </form>

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
        url: "${pageContext.request.contextPath}/modifica-password",
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