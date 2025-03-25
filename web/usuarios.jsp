<%-- 
    Document   : usuarios
    Created on : 11/03/2025, 12:21:11 p. m.
    Author     : holmer
--%>
<%
    String success = (String) session.getAttribute("success");
    String error = (String) session.getAttribute("error");

    // Eliminar atributos de sesión para que no se muestren después de la primera carga
    session.removeAttribute("success");
    session.removeAttribute("error");
%>

<% if ("true".equals(success)) { %>
<div class="alert alert-success alert-dismissible fade show" role="alert">
    Usuario registrado correctamente.
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>
<% } else if ("true".equals(error)) { %>
<div class="alert alert-danger alert-dismissible fade show" role="alert">
    Error al registrar el usuario. Inténtelo nuevamente.
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>
<% } %>

<%@ page import="Modelo.Usuarios" %>
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
                            <a class="nav-link active" aria-current="page" href="#">CLIENTES</a>
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
        <div class="container my-4">
            <h1 class="text-center p-3"></h1>

            <!-- Tabla de Usuarios -->
 
           
            <div class="container my-4">
                <h2 class="text-center mb-4">Usuarios Registrados</h2>
                <div class="table-responsive">
                    <table class="table table-striped table-bordered w-100">
                        <thead class="table-primary">
                            <tr>
                                <th>Tipo de Identificación</th>
                                <th>Numero identificación</th>
                                <th>Nombres</th>
                                <th>Apellidos</th>
                                <th>Teléfono</th>
                                <th>Correo</th>
                                <th>Dirección</th>
                                <th>Rol</th>
                                <th>Usuario</th>
                                <th>Contraseña</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody id="usuarios">
                            <%
                                List<Usuarios> listaUsuarios = (List<Usuarios>) request.getAttribute("listaUsuarios");
                                if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                                    for (Usuarios usuario : listaUsuarios) {
                            %>
                            <tr>
                                <td><%= usuario.getPersona().getDescripcionTipoIdentificacion() %></td>
                                <td><%= usuario.getPersona().getNumeroIdentificacion() %></td>
                                <td><%= usuario.getPersona().getNombres() %></td>
                                <td><%= usuario.getPersona().getApellidos() %></td>
                                <td><%= usuario.getPersona().getTelefono() %></td>
                                <td><%= usuario.getPersona().getCorreo() %></td>
                                <td><%= usuario.getPersona().getDireccion() %></td>
                                <td><%= usuario.getPersona().getDescripcionRol() %></td>
                                <td><%= usuario.getNombreUsuario() %></td>
                                <td><%= usuario.getContrasenaUsuario() %></td>
                                <td class="text-center">
                                    <!-- Botón para abrir el modal y pasar el idPersona -->
                                    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modificarUsuario">
                                        Editar
                                    </button>
                                    <button type="button" class="btn btn-sm btn-danger ms-2">Eliminar</button>
                                </td>
                            </tr>
                            <% 
                                    }
                                } else { 
                            %>
                            <tr>
                                <td colspan="11" class="text-center">No hay usuarios registrados</td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>



            <div class="row">
                <!-- Botón para abrir el modal -->
                <div class="col-12 text-center mb-4">
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#registroModal">
                        Registrar Usuario
                    </button>

                    <button type="button" name="home" class="btn btn-primary" onclick="location.href = 'home.jsp'">INICIO</button>
                </div>
            </div>


            <!-- Modal registro -->
            <div class="modal fade" id="registroModal" tabindex="-1" aria-labelledby="registroModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header d-flex justify-content-center">
                            <h5 class="modal-title" id="registroModalLabel">Registro de Usuarios</h5>
                        </div>
                        <div class="modal-body">
                            <form method="POST" action="UsuariosControl">
                                <input type="hidden" name="action" value="registrarUsuario"> <!-- Acción para identificar en el Servlet -->

                                <!-- Select de Tipo de Identificación -->
                                <div class="mb-3">
                                    <label for="tipo_identificacion" class="form-label">Tipo Identificación</label>
                                    <select class="form-control" name="idTipoIdentificacion" id="tipoidentificacion" required>
                                        <option value="">Seleccione...</option>
                                        <c:forEach var="tipo" items="${listaTipos}">
                                            <option value="${tipo.idTipoIdentificacion}">${tipo.descripcionTipoIdentificacion}</option>
                                        </c:forEach>
                                    </select>
                                </div>



                                <!-- Identificación -->
                                <div class="mb-3">
                                    <label for="numeroIdentificacion" class="form-label">Identificación</label>
                                    <input type="text" class="form-control" name="numeroIdentificacion" id="identificacion" required>
                                </div>

                                <!-- Nombres -->
                                <div class="mb-3">
                                    <label for="nombres" class="form-label">Nombres</label>
                                    <input type="text" class="form-control" name="nombres" id="nombres" required>
                                </div>

                                <!-- Apellidos -->
                                <div class="mb-3">
                                    <label for="apellidos" class="form-label">Apellidos</label>
                                    <input type="text" class="form-control" name="apellidos" id="apellidos" required>
                                </div>

                                <!-- Teléfono -->
                                <div class="mb-3">
                                    <label for="telefono" class="form-label">Teléfono</label>
                                    <input type="text" class="form-control" name="telefono" id="telefono" required>
                                </div>

                                <!-- Correo -->
                                <div class="mb-3">
                                    <label for="correo" class="form-label">Correo</label>
                                    <input type="email" class="form-control" name="correo" id="correo" required>
                                </div>

                                <!-- Dirección -->
                                <div class="mb-3">
                                    <label for="direccion" class="form-label">Dirección</label>
                                    <input type="text" class="form-control" name="direccion" id="direccion" required>
                                </div>

                                <!-- Usuario -->
                                <div class="mb-3">
                                    <label for="usuario" class="form-label">Usuario</label>
                                    <input type="text" class="form-control" name="nombreUsuario" id="usuario" required>
                                </div>

                                <!-- Contraseña -->
                                <div class="mb-3">
                                    <label for="contrasena" class="form-label">Contraseña</label>
                                    <input type="password" class="form-control" name="contrasenaUsuario" id="contrasena" required>
                                </div>

                                <!-- Rol -->
                                <div class="mb-3">
                                    <label for="rol" class="form-label">Rol</label>
                                    <select class="form-control" name="idRoles" id="rol" required>
                                        <option value="">Seleccione...</option>
                                        <c:forEach var="rol" items="${listaRoles}">
                                            <option value="${rol.idRoles}">${rol.descripcionRol}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Botones -->
                                <div class="d-flex justify-content-between">
                                    <button type="submit" class="btn btn-primary">Registrar</button>
                                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>





            <!-- Modal modificar -->
            
            <div class="modal fade" id="modificarUsuario" tabindex="-1" aria-labelledby="modificarUsuarioLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header d-flex justify-content-center">
                            <h5 class="modal-title" id="modificarUsuarioLabel">Editar Usuario</h5>
                        </div>
                        <div class="modal-body">
                            <form method="POST" action="UsuariosControl">
                                <input type="hidden" name="action" value="registrarUsuario"> <!-- Acción para identificar en el Servlet -->

                                <!-- Tipo de Identificación -->
                                <div class="mb-3">
                                    <label for="tipo_identificacion" class="form-label">Tipo Identificación</label>
                                    <select class="form-control" name="idTipoIdentificacion" id="tipoidentificacion" required>
                                        <option value="">Seleccione...</option>
                                        <c:forEach var="tipo" items="${listaTipos}">
                                            <option value="${tipo.idTipoIdentificacion}">${tipo.descripcionTipoIdentificacion}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Identificación -->
                                <div class="mb-3">
                                    <label for="numeroIdentificacion" class="form-label">Identificación</label>
                                    <input type="text" class="form-control" name="numeroIdentificacion" id="identificacion" required>
                                </div>

                                <!-- Nombres -->
                                <div class="mb-3">
                                    <label for="nombres" class="form-label">Nombres</label>
                                    <input type="text" class="form-control" name="nombres" id="nombres" required>
                                </div>

                                <!-- Apellidos -->
                                <div class="mb-3">
                                    <label for="apellidos" class="form-label">Apellidos</label>
                                    <input type="text" class="form-control" name="apellidos" id="apellidos" required>
                                </div>

                                <!-- Teléfono -->
                                <div class="mb-3">
                                    <label for="telefono" class="form-label">Teléfono</label>
                                    <input type="text" class="form-control" name="telefono" id="telefono" required>
                                </div>

                                <!-- Correo -->
                                <div class="mb-3">
                                    <label for="correo" class="form-label">Correo</label>
                                    <input type="email" class="form-control" name="correo" id="correo" required>
                                </div>

                                <!-- Dirección -->
                                <div class="mb-3">
                                    <label for="direccion" class="form-label">Dirección</label>
                                    <input type="text" class="form-control" name="direccion" id="direccion" required>
                                </div>

                                <!-- Usuario -->
                                <div class="mb-3">
                                    <label for="usuario" class="form-label">Usuario</label>
                                    <input type="text" class="form-control" name="nombreUsuario" id="usuario" required>
                                </div>

                                <!-- Contraseña -->
                                <div class="mb-3">
                                    <label for="contrasena" class="form-label">Contraseña</label>
                                    <input type="password" class="form-control" name="contrasenaUsuario" id="contrasena" required>
                                </div>

                                <!-- Rol -->
                                <div class="mb-3">
                                   <label for="rol" class="form-label">Rol</label>
                                    <select class="form-control" name="idRoles" id="rol" required>
                                        <option value="">Seleccione...</option>
                                        <c:forEach var="rol" items="${listaRoles}">
                                            <option value="${rol.idRoles}">${rol.descripcionRol}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Botones -->
                                <div class="d-flex justify-content-between">
                                    <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

    </body>
    <footer class="bg-light text-center text-lg-start">
        <div class="text-center p-3">
            © 2024 Avicontrol. Todos los derechos reservados.
        </div>
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
