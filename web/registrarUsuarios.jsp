<%-- 
    Document   : RegistrarUsuarios
    Created on : 27/03/2025, 2:18:51 p. m.
    Author     : holmer
--%>

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

          <!-- formulario para crear ususarios -->
            <div class="container my-4">
                <h2 class="text-center mb-4">Registrar Usuario</h2>
                <form action="UsuariosControl" method="POST">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label for="tipoIdentificacion" class="form-label">Tipo de Identificacion</label>
                                <select class="form-select" id="tipoIdentificacion" name="tipoIdentificacion" required>
                                    <option value="">Seleccione el tipo de Identificacion</option>
                                    <c:forEach var="tipo" items="${tiposIdentificacion}">
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

                            <div class="mb-3">
                                <label for="telefono" class="form-label">Teléfono</label>
                                <input type="tel" class="form-control" id="telefono" name="telefono" required>
                            </div>




                        </div>

                        <div class="col-md-6">


                            <div class="mb-3">
                                <label for="correo" class="form-label">Correo</label>
                                <input type="email" class="form-control" id="correo" name="correo" required>
                            </div>

                            <div class="mb-3">
                                <label for="direccion" class="form-label">Dirección</label>
                                <input type="text" class="form-control" id="direccion" name="direccion" required>
                            </div>

                            <div class="mb-3">


                                <label for="rol" class="form-label">Rol</label>
                                <select class="form-select" id="rol" name="rol" required>
                                    <option value="">Seleccione el rol</option>
                                    <!-- Iteramos sobre la lista de roles para crear las opciones dinámicamente -->
                                    <c:forEach var="rol" items="${roles}">
                                        <option value="${rol.idRoles}">${rol.descripcionRol}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="usuario" class="form-label">Usuario</label>
                                <input type="text" class="form-control" id="usuario" name="usuario" required>
                            </div>

                            <div class="mb-3">
                                <label for="contrasena" class="form-label">Contraseña</label>
                                <input type="password" class="form-control" id="contrasena" name="contrasena" required>
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


