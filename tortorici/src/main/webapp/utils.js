// ELENCO DEI PRINCIPALI SCRIPT IN JS CHE MI SERVONO PER LA GESTIONE DEGLI EVENTI

function richiediPagine(element, pagina) {

    $.ajax({

        type: 'GET',
        url: './richiedi-pagine',
        data: {
            content: pagina
        },

        success: function (data) {
            element.html(data)
        }

    })

}

function linkClick(event, element, pagina) {

    event.preventDefault()
    richiediPagine(element, pagina)

}

function getToday() {

    let today = new Date();
    let dd = today.getDate();
    let mm = today.getMonth() + 1;          //January is 0!
    let yyyy = today.getFullYear();

    if (dd < 10) {
        dd = '0' + dd;
    }

    if (mm < 10) {
        mm = '0' + mm;
    }

    today = yyyy + '-' + mm + '-' + dd;

    return today;

}