<%-- 
    Document   : usuarios
    Created on : 11/03/2025, 12:21:11 p. m.
    Author     : holmer
--%>
<%
    String estadoSeleccionado = (String) request.getAttribute("estadoSeleccionado");
%>


<%@ page import="Modelo.Persona" %>
<%@ page import="Modelo.Usuarios" %>
<%@ page import="java.util.List" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Clientes</title>
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
                        <a class="nav-link active" aria-current="page" href="pedidos.jsp">PEDIDOS</a>
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
    <div class="container-fluid my-4">
        <h1 class="text-center p-3"></h1>



        <!-- Tabla de Clientes -->


        <div class="container-fluid my-4">
            <h2 class="text-center mb-4">Clientes Registrados</h2>
            <div class="table-responsive">
                <form action="ClientesControl" method="get" class="mb-3">
                    <input type="hidden" name="action" value="listarClientes">
                    <select name="estado" onchange="this.form.submit()" class="form-select w-auto d-inline">
                        <option value="" <%= (estadoSeleccionado == null || estadoSeleccionado.isEmpty()) ? "selected" : "" %>>Mostrar todos clientes</option>
                        <option value="activos" <%= "activos".equals(estadoSeleccionado) ? "selected" : "" %>>Activos</option>
                        <option value="inactivos" <%= "inactivos".equals(estadoSeleccionado) ? "selected" : "" %>>Inactivos</option>
                    </select>
                </form>
                <table class="table table-striped table-bordered w-100" style="width: 100%; min-width: 1000px;">
                    <thead class="table-primary">
                        <tr>
                            <th>Tipo de<br>Identificación</th>
                            <th>Numero <br>identificación</th>
                            <th>Nombres</th>
                            <th>Apellidos</th>
                            <th>Teléfono</th>
                            <th>Correo</th>
                            <th>Dirección</th>                                                    
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="clinetes">
                        <c:forEach var="cliente" items="${clientes}">
                            <tr class="tabla-fondo-personalizado">

                                <td>${cliente.descripcionTipoIdentificacion}</td>
                                <td>${cliente.numeroIdentificacion}</td>
                                <td>${cliente.nombres}</td>
                                <td>${cliente.apellidos}</td>
                                <td>${cliente.telefono}</td>
                                <td>${cliente.correo}</td>
                                <td>${cliente.direccion}</td>    


                                <td class="text-center">
                                    <c:if test="${cliente.estado}">
                                        <!-- Botón Editar -->
                                        <form action="ClientesControl" method="get" style="display:inline;">
                                            <input type="hidden" name="action" value="editarCliente">
                                            <input type="hidden" name="id" value="${cliente.idPersona}">
                                            <button type="submit" class="btn btn-sm btn-success me-1">
                                                Editar
                                            </button>
                                        </form>

                                        <!-- Botón Eliminar -->
                                        <form action="ClientesControl?action=eliminarCliente" method="post" style="display:inline;">
                                            <input type="hidden" name="id" value="${cliente.idPersona}">
                                            <button type="submit" class="btn btn-sm btn-danger"
                                                    onclick="return confirm('¿Estás seguro de que deseas eliminar este cliente?');">
                                                Desactivar
                                            </button>
                                        </form>
                                    </c:if>

                                    <c:if test="${!cliente.estado}">
                                        <!-- Botón Activar -->
                                        <form action="ClientesControl?action=activarCliente" method="post" style="display:inline;">
                                            <input type="hidden" name="id" value="${cliente.idPersona}">
                                            <button type="submit" class="btn btn-sm btn-primary"
                                                    onclick="return confirm('¿Estás seguro de que deseas activar este cliente?');">
                                                Activar
                                            </button>
                                        </form>
                                    </c:if>
                                </td>

                            </tr>
                        </c:forEach>


                    </tbody>
                </table>
            </div>
        </div>
      


        <div class="row">
         
            <div class="col-12 text-center mb-4">
                <a   class="btn btn-primary" href="ClientesControl?action=nuevoCliente">Registrar Cliente</a>

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