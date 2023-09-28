<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container my-5 text-center border border-1 rounded-3 shadow-sm"  id="container_principale">

    <ul class="nav nav-tabs my-3">

        <li class="nav-item">
            <a class="nav-link active" aria-current="page">Accedi</a>
        </li>

        <li class="nav-item">
            <a class="nav-link" href=# id="register-form">Registrati</a>
        </li>

    </ul>

    <div class="row mx-auto my-4" id="riga_immagine">

        <img src="${pageContext.request.contextPath}/img/cliente/person.svg" class="card-img-top">

    </div>

    <div class="row mx-auto my-3 justify-content-center">

        <h1>Accedi</h1>

    </div>

    <form class="form-signin" id="form">

        <div class="row mb-1 justify-content-center">

            <div class="col-md-10 my-2">

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
                           placeholder="Password" required>
                    <label for="password">Password</label>

                </div>

            </div>

        </div>

        <div class="row my-3 justify-content-center mb-5">

            <div class="col-md-5">

                <button type="submit" class="btn btn-outline-primary btn-block w-100 mb-1">Login</button>

            </div>

            <div class="col-md-5">

                <button type="reset" class="btn btn-outline-danger btn-block w-100">Annulla</button>

            </div>

        </div>

    </form>

</div>

<script type="text/javascript">

    $(document).ready(() => {

        $("#register-form").click((event) => {
            linkClick(event, $("#content"), "registerForm.jsp")
        })

        $("#form").submit((event) => {

            let serializedData = $("#form").serialize()

            event.preventDefault()
            event.stopPropagation()

            $.ajax({
                type: 'POST',
                async: true,
                url: '${pageContext.request.contextPath}/login',
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