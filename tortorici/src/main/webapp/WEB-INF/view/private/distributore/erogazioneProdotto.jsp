<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/WEB-INF/components/distributore/navbar.jsp" />

<div class="container my-5 text-center border border-1 rounded-3" id="container_grande">

  <div class="progress mx-auto my-5">

    <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuemin="0"
         aria-valuemax="100" style="width: 75%"></div>

  </div>

  <h1 class="mx-auto my-5">Erogazione in corso</h1>

  <div class="spinner-grow text-primary" role="status">

    <span class="visually-hidden">Loading...</span>

  </div>

  <div class="spinner-grow text-primary" role="status">

    <span class="visually-hidden">Loading...</span>

  </div>

  <div class="spinner-grow text-primary" role="status">

    <span class="visually-hidden">Loading...</span>

  </div>

  <h2 class="mx-auto my-5">A breve potrai ritirare il prodotto</h2>

</div>

<script type="text/javascript">

  $(document).ready(() => {

    setTimeout(function () {

      $.ajax({

        type: 'GET',
        url: '${pageContext.request.contextPath}/richiedi-pagine',
        data: {
          content: "prodottoErogato.jsp"
        },

        success: function (res) {
          $("#content").html(res)
        }

      })

    }, 5*1000)

  })

</script>