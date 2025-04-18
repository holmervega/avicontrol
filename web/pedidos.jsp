<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Pedidos</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="Vista/Css/usuarios.css" type="text/css"/>

    </head>


</body>

<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#"></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">

                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="ClientesControl?action=listarClientes">CLIENTES</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">PEDIDOS</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">REPORTES</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="home.jsp">INICIO</a>
                    </li>

                </ul>
            </div>
        </div>
    </nav>
    <div class="container-fluid my-4">
        <h1 class="text-center p-3"></h1>

        <!-- Tabla pedidos -->
        <div class="container-fluid my-4">
            <h2 class="text-center mb-4">Pedidos Registrados</h2>
            <div class="table-responsive">
                <table class="table table-striped table-bordered w-100" style="width: 100%; min-width: 1000px;">
                    <thead class="table-primary">
                        <tr>
                            <th>Numero<br>Pedido</th>
                            <th>Fecha<br>Pedido</th>
                            <th>Cliente </th>
                            <th>Numero<br>identificación</th>
                            <th>Cantidad</th>
                            <th>Producto</th>
                            <th>Unidad</th>
                            <th>Valor<br>Unitario</th>
                            <th>Valor<br>Total</th>                          

                        </tr>
                    <tbody>
                        <c:forEach var="pedido" items="${listaPedidos}">
                            <tr class="tabla-fondo-personalizado">
                                <td>${pedido.pedidoCabecera.numeroPedido}</td>
                                <td>${pedido.pedidoCabecera.fechaPedido}</td>
                                <td>${pedido.pedidoCabecera.nombrePersona} ${pedido.pedidoCabecera.apellidoPersona}</td>
                                <td>${pedido.pedidoCabecera.numeroIdentificacion}</td>
                                <td>${pedido.cantidad}</td>
                                <td>${pedido.producto.nombre}</td>
                                <td>${pedido.unidad.descripcionUnidades}</td>
                                <td>${pedido.precioUnitario}</td>
                                <td>${pedido.valorPedido}</td>
                            </tr>
                        </c:forEach>
                    </tbody>

                    </thead>
                </table>
            </div>
        </div>



        <div class="row">

            <div class="col-12 text-center mb-4">
                <a   class="btn btn-primary" href="registrarPedidos.jsp">Nuevo Pedido</a>

                <button type="button" name="home" class="btn btn-primary" onclick="location.href = 'home.jsp'">INICIO</button>
            </div>
        </div>

</body>
</html>


</body>
<footer class="bg-light text-center text-lg-start">
    <div class="text-center p-3">
        © 2024 Avicontrol. Todos los derechos reservados.
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
