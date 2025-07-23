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
                        <li class="nav-item"><a class="nav-link active" href="ClientesControl?action=listarClientes">CLIENTES</a></li>
                        <li class="nav-item"><a class="nav-link active" href="reportes.jsp">REPORTES</a></li>
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
                                <!-- Número de Pedido (Solo una vez) -->
                                <div class="col-md-4">
                                    <label for="numeroPedido" class="form-label">Número de Pedido:</label>
                                    <input type="text" class="form-control" name="numeroPedido" id="numeroPedido" value="${numeroPedido}" readonly>
                                </div>

                                <!-- Fecha del Pedido (Solo una vez) -->
                                <div class="col-md-4">
                                    <label for="fechaPedido" class="form-label">Fecha del Pedido:</label>
                                    <input type="date" class="form-control" name="fechaPedido" id="fechaPedido" required>
                                </div>
                            </div>

                            <!-- Contenedor de productos -->
                            <div id="productosContainer"></div>

                            <!-- Total del pedido (Sumatoria de todos los productos) -->
                            <div class="row g-3 mt-3">
                                <div class="col-md-4">
                                    <label for="valorTotal" class="form-label">Valor Total:</label>
                                    <input type="text" class="form-control" id="valorTotal" name="valorTotal" readonly>
                                </div>
                            </div>

                            <!-- Botón para agregar productos -->
                            <div class="text-center mt-3">
                                <button type="button" class="btn btn-success" onclick="agregarProducto()">Agregar Producto</button>
                            </div>

                            <!-- Botón para registrar el pedido -->
                            <div class="text-center mt-4">
                                <button type="submit" class="btn btn-primary mt-3">Registrar Pedido</button>
                            </div>

                        </form>
                    </div>

                    <script>
                    // Función para agregar un producto al formulario
                                    function agregarProducto() {
                                    const container = document.getElementById("productosContainer");
                                    // Crear una nueva fila para el producto
                                    const row = document.createElement("div");
                                    row.classList.add("row");
                                    row.classList.add("productoRow");
                                    row.innerHTML = `
                                            <div class="col-md-4">
                                                <label for="productoSelect" class="form-label">Producto:</label>
                                                    <select class="form-control" name="idProducto[]" required onchange="actualizarDescripcion(this)">
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
                                <input type="text" class="form-control" name="descripcionProducto[]" readonly>
                                        </div>
                    
                                        <div class="col-md-4">
                                        <label for="cantidad" class="form-label">Cantidad:</label>
                                            <input type="number" class="form-control" name="cantidad[]" min="1" required onchange="calcularTotal()">
                                                </div>
                            
                                                <div class="col-md-4">
                                            <label for="precioUnitario" class="form-label">Precio Unitario:</label>
                                                <input type="number" class="form-control" name="precioUnitario[]" min="0" required onchange="calcularTotal()">
                                                </div>
                            
                                            <!-- Unidades (Dentro del script ahora) -->
                                            <div class="col-md-4">
                                                <label for="unidadesSelect" class="form-label">Unidades:</label>
                                                <select class="form-control" name="idUnidad[]" required>
                                                <option value="">Seleccione una unidad</option>
                        <c:forEach var="unidades" items="${unidades}">
                                            <option value="${unidades.idUnidades}" data-descripcion="${unidades.descripcionUnidades}">
                            ${unidades.descripcionUnidades}
                                                </option>
                        </c:forEach>
                                                </select>
                                            </div>
                        
                                            <div class="col-md-4">
                                                <label for="valorTotalProducto" class="form-label">Valor Total Producto:</label>
                                                <input type="text" class="form-control" name="valorTotalProducto[]" readonly>
                                            </div>
                            
                                                <button type="button" class="btn btn-danger" onclick="eliminarProducto(this)">Eliminar Producto</button>
                        `;

                        container.appendChild(row);
                    }

                    // Función para eliminar un producto
                    function eliminarProducto(button) {
                        const row = button.parentElement;
                        row.remove();
                        calcularTotal();
                    }

                    // Función para actualizar la descripción del producto cuando se selecciona uno
                    function actualizarDescripcion(select) {
                        const descripcionInput = select.closest(".productoRow").querySelector("input[name='descripcionProducto[]']");
                        const descripcion = select.options[select.selectedIndex].getAttribute("data-descripcion");
                        descripcionInput.value = descripcion;
                        calcularTotal();
                    }

                    // Función para calcular el valor total de todos los productos
                    function calcularTotal() {
                        const precios = document.querySelectorAll("input[name='precioUnitario[]']");
                        const cantidades = document.querySelectorAll("input[name='cantidad[]']");
                        let total = 0;

                        precios.forEach((precioInput, index) => {
                            const precio = parseFloat(precioInput.value) || 0;
                            const cantidad = parseInt(cantidades[index].value) || 0;
                            const valorTotalProducto = precio * cantidad;

                            // Actualizamos el valor total del producto en el formulario
                            const valorTotalProductoInput = precioInput.closest(".productoRow").querySelector("input[name='valorTotalProducto[]']");
                            valorTotalProductoInput.value = valorTotalProducto.toFixed(2);

                            total += valorTotalProducto;
                        });

                        // Actualizamos el valor total del pedido
                        const valorTotalInput = document.getElementById("valorTotal");
                        valorTotalInput.value = total.toFixed(2);
                    }
                    </script>


                   
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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
    // Actualiza la descripción del producto cuando se selecciona uno
        document.getElementById("productoSelect").addEventListener("change", function () {
                            const selectedOption = this.options[this.selectedIndex];
                    const descripcion = selectedOption.getAttribute("data-descripcion");
                    document.getElementById("descripcionProducto").value = descripcion ? descripcion : "";
            });
        
            // Elementos de cantidad, precio unitario y total
                const cantidadInput = document.querySelector('input[name="cantidad"]');
                    const precioUnitarioInput = document.querySelector('input[name="precioUnitario"]'); // corregido aquí
                    const valorTotalInput = document.getElementById('valorTotal');
            
                // Función para calcular el valor total
                function calcularValorTotal() {
                            const cantidad = parseFloat(cantidadInput.value);
                    const precioUnitario = parseFloat(precioUnitarioInput.value);
                    if (!isNaN(cantidad) && !isNaN(precioUnitario)) {
                    const total = cantidad * precioUnitario;
                    valorTotalInput.value = total.toLocaleString('es-CO', {
                    minimumFractionDigits: 2,
                            maximumFractionDigits: 2
                    });
                    } else {
                    valorTotalInput.value = '';
                    }
                    }
                
                    // Eventos que recalculan cuando cambia la cantidad o el precio
            cantidadInput.addEventListener('input', calcularValorTotal);
            precioUnitarioInput.addEventListener('input', calcularValorTotal);
        </script>

    </div>
</body>
</html>
