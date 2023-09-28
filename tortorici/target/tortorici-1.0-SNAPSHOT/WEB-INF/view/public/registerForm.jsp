<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container my-5 text-center border border-1 rounded-3 shadow-sm" id="container_principale">

    <ul class="nav nav-tabs my-3">

        <li class="nav-item">
            <a class="nav-link" id="login-form" href=#>Accedi</a>
        </li>

        <li class="nav-item">
            <a class="nav-link active" aria-current="page">Registrati</a>
        </li>

    </ul>

    <div class="row mx-auto my-4" id="riga_immagine">

        <img src="${pageContext.request.contextPath}/img/cliente/register.svg" class="card-img-top">

    </div>

    <div class="row mx-auto my-3 justify-content-center">

        <h1>Registrati</h1>

    </div>

    <form class="form-subscribe" id="form">

        <div class="row mb-1 justify-content-center">

            <div class="col-md-10">

                <div class="form-floating mb-3">

                    <input type="text" name="nome" id="nome" class="form-control mb-3"
                           placeholder="Nome" required>
                    <label for="nome">Nome</label>

                </div>

            </div>

        </div>

        <div class="row mb-1 justify-content-center">

            <div class="col-md-10">

                <div class="form-floating mb-3">

                    <input type="text" name="cognome" id="cognome" class="form-control mb-3"
                           placeholder="Cognome" required>
                    <label for="cognome">Cognome</label>

                </div>

            </div>

        </div>

        <div class="row mb-1 justify-content-center">

            <div class="col-md-10">

                <div class="form-floating mb-3">

                    <input type="date" name="dataNascita" id="dataNascita" class="form-control mb-3"
                           placeholder="Data di nascita" max = "" min = "" required>
                    <label for="dataNascita">Data di nascita</label>

                </div>

            </div>

        </div>

        <div class="row mb-1 justify-content-center">

            <div class="col-md-10">

                <div class="form-floating mb-3">

                    <input type="email" name="email" id="email" class="form-control mb-3"
                           pattern="^[A-z0-9\.\+_-]+@[A-z0-9\._-]+\.[A-z]{2,6}$"
                           onkeyup="this.value = this.value.toLowerCase()" placeholder="E-mail" required>
                    <label for="email">Email</label>

                </div>

            </div>

        </div>

        <div class="row mb-1 justify-content-center">

            <div class="col-md-10">

                <div class="form-floating mb-3">

                    <input type="password" name="password" id="password" class="form-control mb-3"
                           minlength="8" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$"
                           placeholder="Password" required>
                    <label for="password">Password</label>

                </div>

            </div>

        </div>

        <div class="row my-3 justify-content-center mb-5">

            <div class="col-md-5">

                <button type="submit" class="btn btn-outline-primary btn-block w-100 mb-1">registrati</button>

            </div>

            <div class="col-md-5">

                <button type="reset" class="btn btn-outline-danger btn-block w-100">annulla</button>

            </div>

        </div>

    </form>

</div>

<script type="text/javascript">

    $(document).ready(() =>{

        let today = new Date()

        let anno = today.getFullYear() - 100
        let giorno = today.getDay()
        let mese = today.getMonth() + 1

        if (giorno < 10) {
            giorno = '0' + giorno;
        }

        if (mese < 10) {
            mese = '0' + mese;
        }

        let minValue = anno +"-" +mese +"-" +giorno

        document.getElementById("dataNascita").setAttribute("max", today.toISOString().slice(0, 10))
        document.getElementById("dataNascita").setAttribute("min", minValue)

        $("#login-form").click((event) => {
            linkClick(event, $("#content"), "loginForm.jsp")
        })

        $("#form").submit((event) => {

            let serializedData = $("#form").serialize()

            event.preventDefault()
            event.stopPropagation()

            $.ajax({
                type: 'POST',
                async: true,
                url: '${pageContext.request.contextPath}/registra-cliente',
                data: serializedData,
                success: function(res) {

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

    })

</script>