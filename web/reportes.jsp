<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reportes</title>
    <link rel="stylesheet" type="text/css" href="Vista/Css/reportes.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
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
                <li class="nav-item" id="menuUsuarios">
                    <a class="nav-link active" aria-current="page" href="ClientesControl?action=listarClientes">CLIENTES</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="PedidosControl?action=listarPedidos">PEDIDOS</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="LogoutControl">SALIR</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<h1>Reportes</h1>

<form action="ReportesControl" method="get">
    <label for="identificacion">Identificación:</label>
    <input type="text" id="identificacion" name="identificacion">

    <label for="fecha">Fecha:</label>
    <input type="date" id="fecha" name="fecha">

    <input type="submit" value="Buscar">
</form>

<h2>Resultados</h2>

<c:if test="${not empty listaPedidosFiltrados}">
    <table>
        <thead>
        <tr>
            <th>Número de Pedido</th>
            <th>Fecha</th>
            <th>Cliente</th>
            <th>Producto</th>
            <th>Unidad</th>
            <th>Cantidad</th>
            <th>Precio Unitario</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="totalGeneral" value="0" />
        <c:forEach var="p" items="${listaPedidosFiltrados}">
            <c:set var="numFilas" value="${fn:length(p.productos)}" />
            <c:set var="totalPedido" value="0" />
            <c:forEach var="i" begin="0" end="${numFilas - 1}">
                <tr>
                    <c:if test="${i == 0}">
                        <td rowspan="${numFilas}" class="rowspan-cell">${p.pedidoCabecera.numeroPedido}</td>
                        <td rowspan="${numFilas}" class="rowspan-cell">${p.pedidoCabecera.fechaPedido}</td>
                        <td rowspan="${numFilas}" class="rowspan-cell">${p.pedidoCabecera.nombrePersona} ${p.pedidoCabecera.apellidoPersona}</td>
                    </c:if>

                    <td>${p.productos[i].nombre}</td>
                    <td>${p.unidades[i].descripcionUnidades}</td>
                    <td>${p.cantidades[i]}</td>
                    <td>
                        <fmt:formatNumber value="${p.preciosUnitarios[i]}" type="number" maxFractionDigits="0" groupingUsed="true"/>
                    </td>

                    <c:set var="subtotal" value="${p.cantidades[i] * p.preciosUnitarios[i]}" />
                    <c:set var="totalPedido" value="${totalPedido + subtotal}" />
                </tr>
            </c:forEach>

            <!-- Fila de total por pedido -->
            <tr class="total-row">
                <td colspan="4"></td>
                <td colspan="2"><strong>Total pedido</strong></td>
                <td>
                    <strong>
                        <fmt:formatNumber value="${totalPedido}" type="number" maxFractionDigits="0" groupingUsed="true"/>
                    </strong>
                </td>
            </tr>

            <c:set var="totalGeneral" value="${totalGeneral + totalPedido}" />
        </c:forEach>

        <!-- Fila total general -->
        <tr class="total-general">
            <td colspan="5"></td>
            <td><strong>Total general</strong></td>
            <td>
                <strong>
                    <fmt:formatNumber value="${totalGeneral}" type="number" maxFractionDigits="0" groupingUsed="true"/>
                </strong>
            </td>
        </tr>
        </tbody>
    </table>
</c:if>

<c:if test="${empty listaPedidosFiltrados}">
    <p>No se encontraron resultados.</p>
</c:if>

</body>
</html>
