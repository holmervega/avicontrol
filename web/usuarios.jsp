<%-- 
    Document   : usuarios
    Created on : 11/03/2025, 12:21:11 p. m.
    Author     : holmer
--%>
<% 
String mensaje = (String) session.getAttribute("mensaje");
if (mensaje != null) { 
    session.removeAttribute("mensaje");
%>
    <script>
        window.onload = function() {
            alert("<%= mensaje %>");
        };
    </script>
<% } %>



<%@ page import="Modelo.Persona" %>
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
    <div class="container-fluid my-4">
        <h1 class="text-center p-3"></h1>

        <!-- Tabla de Usuarios -->


        <div class="container-fluid my-4">
            <h2 class="text-center mb-4">Usuarios Registrados</h2>
            <div class="table-responsive">
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
                            <th>Rol</th>
                            <th>Usuario</th>
                            <th>Contraseña</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="usuarios">
                        <c:forEach var="usuario" items="${usuarios}">
                            <tr class="tabla-fondo-personalizado">

                                <td>${usuario.persona.descripcionTipoIdentificacion}</td>
                                <td>${usuario.persona.numeroIdentificacion}</td>
                                <td>${usuario.persona.nombres}</td>
                                <td>${usuario.persona.apellidos}</td>
                                <td>${usuario.persona.telefono}</td>
                                <td>${usuario.persona.correo}</td>
                                <td>${usuario.persona.direccion}</td>
                                <td>${usuario.persona.descripcionRol}</td>
                                <td>${usuario.nombreUsuario}</td>
                                <td>${usuario.contrasenaUsuario}</td>
                                <td class="text-center">
                                    <button type="button" class="btn btn-success editarUsuario" data-Id="${usuario.persona.idPersona}">Editar</button>


                                    <button type="button" class="btn btn-sm btn-danger eliminarUsuario" data-id="${usuario.persona.idPersona}">Eliminar</button>


                                </td>
                            </tr>
                        </c:forEach>


                    </tbody>
                </table>
            </div>
        </div>
      


        <div class="row">
         
            <div class="col-12 text-center mb-4">
                <a   class="btn btn-primary" href="UsuariosControl?action=registrarUsuario">Registrar Usuario</a>

                <button type="button" name="home" class="btn btn-primary" onclick="location.href = 'home.jsp'">INICIO</button>
            </div>
        </div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    document.body.addEventListener("click", function (event) {
        let botonEliminar = event.target.closest(".eliminarUsuario");
        if (!botonEliminar) return;

        let idPersona = botonEliminar.getAttribute("data-id")?.trim();

        if (!idPersona) {
            console.error("🚨 Error: ID vacío o no válido.");
            alert("⚠ Error: No se capturó el ID.");
            return;
        }

        console.log("🗑️ ID de persona a eliminar:", idPersona);

        // Confirmación antes de eliminar
        let confirmar = confirm("¿Estás seguro de que quieres eliminar este usuario?");
        console.log("✅ Confirmación recibida:", confirmar);

        if (!confirmar) {
            console.log("❌ Eliminación cancelada.");
            return;
        }

        let urlDestino = "UsuariosControl?action=eliminarUsuario&idPersona=" + idPersona;
        console.log("🔗 Redirigiendo a:", urlDestino);

        // Intenta con `href` en lugar de `assign`
        window.location.href = urlDestino;
    });
});

</script>


        <script>
document.addEventListener("DOMContentLoaded", function () {
  document.body.addEventListener("click", function (event) {
      let boton = event.target.closest(".editarUsuario");
      if (!boton) return;

      let idPersona = boton.getAttribute("data-id")?.trim(); // Captura y limpia el ID

      if (!idPersona) {
          console.error("🚨 Error: ID vacío o no válido.");
          alert("⚠ Error: No se capturó el ID.");
          return;
      }

      let urlDestino = "UsuariosControl?action=editarUsuario&idPersona=" + idPersona;

      console.log("🔗 Redirigiendo a:", urlDestino);
      window.location.assign(urlDestino);
  });
});

        </script>

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
