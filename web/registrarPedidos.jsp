<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <script>
            function mostrarFormularioPedido() {
                document.getElementById('formularioPedido').style.display = 'block';
            }
        </script>

        <title>Pedidos</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="Vista/Css/usuarios.css" type="text/css"/>
    </head>

    <body style="zoom: 80%;">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="#"></a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item"><a class="nav-link active" href="#">CLIENTES</a></li>
                        <li class="nav-item"><a class="nav-link active" href="#">PEDIDOS</a></li>
                        <li class="nav-item"><a class="nav-link active" href="#">REPORTES</a></li>
                        <li class="nav-item"><a class="nav-link active" href="home.jsp">INICIO</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container-fluid my-4">
            <h1 class="text-center p-3"></h1>

            <!-- Formulario para buscar cliente -->
            <div class="container-fluid my-4">
                <h2 class="text-center mb-4">Registro de pedidos</h2>
                <form action="PedidosControl" method="POST" autocomplete="off">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="mb-3">
                                <label for="numeroIdentificacion" class="form-label">Buscar cliente:</label>
                                <div class="input-group">
                                    <input type="text" class="form-control" id="numeroIdentificacion" name="numeroIdentificacion" placeholder="Ingrese identificación" required>
                                    <button class="btn btn-primary" type="submit" name="action" value="buscarCliente">Buscar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <!-- Mostrar datos del cliente si existe -->
            <c:if test="${not empty persona}">
                <div class="container-fluid my-4">
                    <h2 class="text-center mb-4">Detalles del cliente</h2>

                    <!-- Información del cliente (solo lectura) -->
                    <div class="row">
                        <div class="col-md-2 mb-3">
                            <label for="nombres" class="form-label">Nombres:</label>
                            <input type="text" class="form-control" id="nombres" value="${persona.nombres}" readonly>
                        </div>
                        <div class="col-md-2 mb-3">
                            <label for="apellidos" class="form-label">Apellidos:</label>
                            <input type="text" class="form-control" id="apellidos" value="${persona.apellidos}" readonly>
                        </div>
                        <div class="col-md-2 mb-3">
                            <label for="numeroIdentificacion" class="form-label">Número de Identificación:</label>
                            <input type="text" class="form-control" id="numeroIdentificacion" value="${persona.numeroIdentificacion}" readonly>
                        </div>
                        <div class="col-md-2 mb-3">
                            <label for="telefono" class="form-label">Teléfono:</label>
                            <input type="tel" class="form-control" id="telefono" value="${persona.telefono}" readonly>
                        </div>
                        <div class="col-md-2 mb-3">
                            <label for="correo" class="form-label">Correo:</label>
                            <input type="email" class="form-control" id="correo" value="${persona.correo}" readonly>
                        </div>
                        <div class="col-md-2 mb-3">
                            <label for="direccion" class="form-label">Dirección:</label>
                            <input type="text" class="form-control" id="direccion" value="${persona.direccion}" readonly>
                        </div>
                    </div>

                    <!-- Botón para mostrar el formulario de pedido -->
                    <div class="text-center mb-4">
                        <button type="button" class="btn btn-success" onclick="mostrarFormularioPedido()">Registrar Pedido</button>
                    </div>

                    <!-- Formulario de pedido (oculto inicialmente) -->
                    <div id="formularioPedido" style="display: none; margin-top: 20px;">
                        <form action="PedidosControl" method="POST">
                            <input type="hidden" name="action" value="registrarPedido">
                            <input type="hidden" name="idPersona" value="${persona.idPersona}" />

                            <div class="row g-3">
                                <div class="col-md-4">
                                    <label for="productoSelect" class="form-label">Producto:</label>
                                    <select class="form-control" name="producto" id="productoSelect" required>
                                        <option value="">Seleccione un producto</option>
                                        <c:forEach var="producto" items="${productos}">
                                            <option value="${producto.idProductos}" data-descripcion="${producto.descripcion}">
                                                ${producto.nombre}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-md-4">
                                    <label for="descripcionProducto" class="form-label">Descripción:</label>
                                    <input type="text" class="form-control" id="descripcionProducto" readonly>
                                </div>

                                <div class="col-md-4">
                                    <label for="unidadesSelect" class="form-label">Unidades:</label>
                                    <select class="form-control" name="unidades" id="unidadesSelect" required>
                                        <option value="">Seleccione una unidad</option>
                                        <c:forEach var="unidades" items="${unidades}">
                                            <option value="${unidades.idUnidades}" data-descripcion="${producto.descripcion}">
                                                ${unidades.descripcionUnidades}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-md-4">
                                    <label for="cantidad" class="form-label">Cantidad:</label>
                                    <input type="number" class="form-control" name="cantidad" min="1" required>
                                </div>

                                <div class="col-md-4">
                                    <label for="valorUnitario" class="form-label">Valor Unitario:</label>
                                    <input type="number" class="form-control" name="valorUnitario" min="0" required>
                                </div>

                                <div class="col-md-4">
                                    <label for="valorTotal" class="form-label">Valor Total:</label>
                                    <input type="text" class="form-control" id="valorTotal" name="valorTotal" readonly>
                                </div>
                            </div>

                            <div class="text-center mt-4">
                                <button type="submit" class="btn btn-primary">Guardar Pedido</button>
                            </div>
                        </form>
                    </div>
                </div>
            </c:if>

            <!-- Mensaje de error si aplica -->
            <c:if test="${not empty mensaje}">
                <div class="alert alert-danger">
                    <p>${mensaje}</p>
                </div>
            </c:if>

            <!-- Footer -->
            <footer class="bg-light text-center text-lg-start">
                <div class="text-center p-3">
                    © 2024 Avicontrol. Todos los derechos reservados.
                </div>
            </footer>

            <!-- Scripts -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
            <script>
                            document.getElementById("productoSelect").addEventListener("change", function () {
                                const selectedOption = this.options[this.selectedIndex];
                                const descripcion = selectedOption.getAttribute("data-descripcion");
                                document.getElementById("descripcionProducto").value = descripcion ? descripcion : "";
                            });

                            const cantidadInput = document.querySelector('input[name="cantidad"]');
                            const valorUnitarioInput = document.querySelector('input[name="valorUnitario"]');
                            const valorTotalInput = document.getElementById('valorTotal');

                            function calcularValorTotal() {
                                const cantidad = parseFloat(cantidadInput.value);
                                const valorUnitario = parseFloat(valorUnitarioInput.value);

                                if (!isNaN(cantidad) && !isNaN(valorUnitario)) {
                                    const total = cantidad * valorUnitario;
                                    valorTotalInput.value = total.toLocaleString('es-CO', {
                                        minimumFractionDigits: 2,
                                        maximumFractionDigits: 2
                                    });
                                } else {
                                    valorTotalInput.value = '';
                                }
                            }

                            cantidadInput.addEventListener('input', calcularValorTotal);
                            valorUnitarioInput.addEventListener('input', calcularValorTotal);
            </script>
        </div>
    </body>
</html>
