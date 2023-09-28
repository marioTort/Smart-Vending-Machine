<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container my-5 text-center border border-1 rounded-3" id="container_grande">

    <ul class="nav nav-tabs my-3 ">

        <li class="nav-item">
            <a class="nav-link" href=# id="home">Home</a>
        </li>

        <li class="nav-item">
            <a class="nav-link active" aria-current="page">Ricarica Wallet</a>
        </li>

    </ul>

    <h1 class="mx-auto my-5">Ricarica Wallet</h1>

    <div class="container my-5 text-center border border-1 rounded-5" id="container_saldo">

        <h2 class="mx-auto my-5" style="color: white;">Saldo attuale</h2>

        <h2 class="mx-auto my-5" style="color: white;">${utente.saldoDisponibile} €</h2>

    </div>

    <form class="my-5" id="form">

        <div class="row mb-2 mx-0">

            <div class="col-sm-6">

                <label for="numeroCarta" class="my-2">Numero Carta</label>
                <input type="text" class="form-control" name="numeroCarta" id="numeroCarta" minlength="16" maxlength="16" placeholder="0123 4567 8910 1112" required>

            </div>

            <div class="col-sm-6">

                <label for="nome" class="my-2">Nome Intestatario</label>
                <input type="text" onkeyup="this.value = this.value.toUpperCase()" class="form-control" id="nome" placeholder="MARIO ROSSI" required>

            </div>

        </div>

        <div class="row mb-2 mx-0">

            <div class="col-sm-4 mb-5">

                <label for="scadenza" class="my-2">Data di Scadenza</label>
                <input type="month" class="form-control" id="scadenza" placeholder="Data scadenza" required>

            </div>

            <div class="col-sm-4 mb-5">

                <label for="cvv" class="my-2">CVV</label>
                <input type="text" class="form-control" name="cvv" id="cvv" minlength="3" maxlength="3" placeholder="111" required>

            </div>

            <div class="col-sm-4 mb-5">

                <label for="importo" class="my-2">Importo</label>
                <select id="importo" class="form-select" name="importoRicarica" required>

                    <option selected value="05.00">5.00</option>
                    <option value="10.00">10.00</option>
                    <option value="15.00">15.00</option>

                </select>

            </div>

        </div>

        <div class="row mb-1 justify-content-center">

            <div class="col-md-5">

                <button type="submit" class="btn btn-outline-primary btn-block w-100">ricarica</button>

            </div>

            <div class="col-md-5">

                <button type="reset" id="annulla" class="btn btn-outline-danger btn-block w-100">annulla</button>

            </div>

        </div>

    </form>

</div>

<script type="text/javascript">

    $(document).ready(() => {

        let today = new Date()

        let anno = today.getFullYear()
        let mese = today.getMonth() + 2

        if (mese < 10) {
            mese = '0' + mese;
        }

        let minValue = anno +"-" +mese

        document.getElementById("scadenza").setAttribute("min", minValue)

        $("#home").click((event) => {
            linkClick(event, $("#content"), "homeCliente.jsp")
        })

        $("#form").submit((event) => {

            let serializedData = $("#form").serialize()

            event.preventDefault()
            event.stopPropagation()

            //console.log(serializedData)

            $.ajax({
                type: 'POST',
                async: true,
                url: '${pageContext.request.contextPath}/ricarica-wallet',
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

        $("#annulla").click((event) => {
            linkClick(event, $("#content"), "homeCliente.jsp")
        })

    })

</script>