<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<label for="credito" class="my-2">Inserisci Monete</label>

<div class="input-group w-50 mx-auto">

    <button class="btn btn-outline-primary" type="button" id="aggiungi_credito">
        aggiungi
    </button>

    <select id="credito" class="form-select" name="importoRicarica" required>

        <option selected value="0.10">0.10</option>
        <option value="0.20">0.20</option>
        <option value="0.50">0.50</option>
        <option value="1.00">1.00</option>
        <option value="2.00">2.00</option>

    </select>

    <span class="input-group-text">Credito: </span>
    <span id="credit" class="input-group-text"></span>

</div>