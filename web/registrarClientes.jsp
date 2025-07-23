<%-- 
    Document   : RegistrarClientes
    Created on : 6/04/2025, 11:31:32 a. m.
    Author     : holmer
--%>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Registrar Cliente</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="Vista/Css/usuarios.css" type="text/css"/>
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
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="PedidosControl?action=listarPedidos">PEDIDOS</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="reportes.jsp">REPORTES</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="home.jsp">INICIO</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container my-4">
        <h2 class="text-center mb-4">Registrar Cliente</h2>
        <form action="ClientesControl" method="POST">
            <input type="hidden" name="action" value="registrarCliente">

            <div class="row">
                <div class="col-md-6">
                    <div class="mb-3">
                        <label for="tipoIdentificacion" class="form-label">Tipo de Identificación</label>
                        <select class="form-select" id="tipoIdentificacion" name="tipoIdentificacion" required>
                            <option value="">Seleccione el tipo de identificación</option>
                            <c:forEach var="tipo" items="${listaTiposIdentificacion}">
                                <option value="${tipo.idTipoIdentificacion}">
                                    ${tipo.descripcionTipoIdentificacion}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="numeroIdentificacion" class="form-label">Número de Identificación</label>
                        <input type="text" class="form-control" id="numeroIdentificacion" name="numeroIdentificacion" required>
                    </div>

                    <div class="mb-3">
                        <label for="nombres" class="form-label">Nombres</label>
                        <input type="text" class="form-control" id="nombres" name="nombres" required>
                    </div>

                    <div class="mb-3">
                        <label for="apellidos" class="form-label">Apellidos</label>
                        <input type="text" class="form-control" id="apellidos" name="apellidos" required>
                    </div>

                    
                </div>

                <div class="col-md-6">
                    
                    <div class="mb-3">
                        <label for="telefono" class="form-label">Teléfono</label>
                        <input type="tel" class="form-control" id="telefono" name="telefono" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="correo" class="form-label">Correo</label>
                        <input type="email" class="form-control" id="correo" name="correo" required>
                    </div>

                    <div class="mb-3">
                        <label for="direccion" class="form-label">Dirección</label>
                        <input type="text" class="form-control" id="direccion" name="direccion" required>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-12 text-center mb-4">
                    <button type="submit" class="btn btn-primary">Guardar Cliente</button>
                    <button type="button" class="btn btn-secondary" onclick="location.href='ClientesControl?action=listarClientes'">Cancelar</button>
                </div>
            </div>
        </form>
    </div>

    <footer class="bg-light text-center text-lg-start">
        <div class="text-center p-3">
            © 2024 Avicontrol. Todos los derechos reservados.
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
