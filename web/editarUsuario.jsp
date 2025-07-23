<%-- 
    Document   : editarusuario
    Created on : 29/03/2025, 9:26:38 a. m.
    Author     : holmer
--%>


<%@ page import="Modelo.Persona" %>
<%@ page import="Modelo.Roles" %>
<%@ page import="Modelo.TipoIdentificacion" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<!DOCTYPE html>
<html>
    <head>
        <title>Usuarios</title>
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
                            <a class="nav-link active" aria-current="page" href="ClientesControl?action=listarClientes">CLIENTES</a>
                        </li>
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
            <h1 class="text-center p-3"></h1>

            <!-- formulario para modificar  ususarios -->
            <div class="container my-4">
                <h2 class="text-center mb-4">Modificar Usuario</h2>
                <form action="UsuariosControl" method="POST">
                    <input type="hidden" name="action" value="actualizarUsuario">
                    <input type="hidden" name="idPersona" value="${persona.idPersona}">
                    <input type="hidden" name="idUsuario" value="${usuario.idUsuarios}">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label for="tipoIdentificacion" class="form-label">Tipo de Identificación</label>

                                <select class="form-select" id="tipoIdentificacion" name="tipoIdentificacion" required>
                                    <!-- Opción que muestra el tipo de identificación actual -->
                                    <c:if test="${not empty descripcionTipoIdentificacion}">
                                        <option value="" disabled selected>Actual: ${descripcionTipoIdentificacion}</option>
                                    </c:if>

                                    <option value="">Seleccione el tipo de Identificación</option>
                                    <c:forEach var="tipo" items="${tiposIdentificacion}">
                                        <option value="${tipo.idTipoIdentificacion}" 
                                                ${tipo.descripcionTipoIdentificacion eq descripcionTipoIdentificacion ? 'selected' : ''}>
                                            ${tipo.descripcionTipoIdentificacion}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>




                            <div class="mb-3">
                                <label for="numeroIdentificacion">Número de Identificación</label>
                                <input type="text" class="form-control" id="numeroIdentificacion" name="numeroIdentificacion"
                                       value="${persona.numeroIdentificacion}" required>
                            </div>

                            <div class="mb-3">
                                <label for="nombres">Nombre:</label>
                                <input type="text" class="form-control" id="nombres" name="nombres" 
                                       value="${persona.nombres}" required>
                            </div>

                            <div class="mb-3">
                                <label for="apellidos">Apellidos</label>
                                <input type="text" class="form-control" id="apellidos" name="apellidos"
                                       value="${persona.apellidos }" required>
                            </div>

                            <div class="mb-3">
                                <label for="telefono">Teléfono</label>
                                <input type="tel" class="form-control" id="telefono" name="telefono" 
                                       value="${persona.telefono}" required>
                            </div>

                        </div>

                        <div class="col-md-6">

                            <div class="mb-3">
                                <label for="correo">Correo</label>
                                <input type="email" class="form-control" id="correo" name="correo" 
                                       value="${persona.correo}" required>
                            </div>

                            <div class="mb-3">
                                <label for="direccion">Dirección</label>
                                <input type="text" class="form-control" id="direccion" name="direccion" 
                                       value="${persona.direccion}" required>
                            </div>

                            <div class="mb-3">
                                <label for="rol" class="form-label">Rol</label>
                                <select class="form-select" id="rol" name="rol" required>
                                    <!-- Opción que muestra el rol actual -->
                                    <c:if test="${not empty descripcionRol}">
                                        <option value="" disabled selected>Actual: ${descripcionRol}</option>
                                    </c:if>

                                    <option value="">Seleccione el rol</option>
                                    <c:forEach var="rol" items="${roles}">
                                        <option value="${rol.idRoles}" 
                                                ${rol.descripcionRol eq descripcionRol ? 'selected' : ''}>
                                            ${rol.descripcionRol}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>


                            <div class="mb-3">
                                <label for="usuario">Usuario</label>
                                <input type="text" class="form-control" id="usuario" name="usuario" 
                                       value="${usuario.nombreUsuario}" required>
                            </div>

                            <div class="mb-3">
                                <label for="contrasena">Contraseña</label>
                                <input type="password" class="form-control" id="contrasena" name="contrasena" 
                                       value="${usuario.contrasenaUsuario}" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">

                        <div class="col-12 text-center mb-4">
                            <button type="submit" class="btn btn-primary">
                                Guardar Cambios
                            </button>

                             <button type="button" name="home" class="btn btn-primary" onclick="location.href = 'UsuariosControl?action=mostrarUsuarios'">Cancelar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <footer class="bg-light text-center text-lg-start">
            <div class="text-center p-3">
                © 2024 Avicontrol. Todos los derechos reservados.
            </div>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>