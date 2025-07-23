<!DOCTYPE html>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page session="true" %>

<%
    // La variable 'session' ya está implícitamente disponible en JSP.
    // No es necesario declararla nuevamente.
    if (session == null || session.getAttribute("usuario") == null) {
        System.out.println("Sesión no válida o no hay usuario. Redirigiendo a login.");
        // Si la sesión es nula o no hay usuario, redirigir a login
        response.sendRedirect("index.jsp?error=1");
        return;
    }

    // Obtener el rol del usuario desde la sesión
    String rol = (String) session.getAttribute("rolDescripcion");

    // Depuración: Verificar el rol del usuario
    System.out.println("Rol del usuario desde la sesión: " + rol);

    // Verificar el rol y controlar el acceso
    if ("Administrador".equalsIgnoreCase(rol)) {
        // Página para Administrador
        out.println("Bienvenido Administrador");
    } else if ("Cliente".equalsIgnoreCase(rol)) {
        // Página para Cliente
        out.println("Bienvenido Cliente");
    } else {
        // Si no tiene un rol reconocido, redirigirlo o mostrar un mensaje
        System.out.println("Rol no reconocido. Redirigiendo a página de login.");
        response.sendRedirect("index.jsp?error=2");
    }
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
 

    <head>
        <title>Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="Vista/Css/home.css" type="text/css"/>
        <link rel="stylesheet" href="Vista/Css/login.css" type="text/css">
        <link rel="stylesheet" href="Vista/Css/usuarios.css" type="text/css"/>



    </head>
    
    <body>
        <header>
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
                                <a class="nav-link active" aria-current="page" href="UsuariosControl?action=mostrarUsuarios">USUARIOS</a>
                            </li>        
                            <li class="nav-item" id="menuUsuarios">
                                <a class="nav-link active" aria-current="page" href="ClientesControl?action=listarClientes">CLIENTES</a>
                            </li>
                            <li class="nav-item">
                               <a class="nav-link active" aria-current="page" href="PedidosControl?action=listarPedidos">PEDIDOS</a>

                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="reportes.jsp">REPORTES</a>
                            </li>
                            
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="LogoutControl">SALIR</a>
                            </li>

                        </ul>
                    </div>
                </div>
            </nav>
        </header>
        <div class="container mt-4">
            <h1 class="text-center mb-4">Bienvenido a AVICONTROL</h1>
<div class="row">
    <div class="col text-center">
        <a href="ClientesControl?action=listarClientes" class="btn btn-primary">Clientes</a>
        <br>
        <a href="ClientesControl?action=listarClientes" class="img-link">
            <img src="Vista/Imagenes/clientes.png" alt="Clientes" class="img-clientes">
        </a>
    </div>
    <div class="col text-center">
        <a href="PedidosControl?action=listarPedidos" class="btn btn-primary" >Pedidos</a>
        <br>
        <a href="PedidosControl?action=listarPedidos" class="img-link">
            <img src="Vista/Imagenes/pedidos.png" alt="Pedidos" class="img-pedidos">
        </a>
    </div>
    <div class="col text-center">
        <a href="reportes.jsp" class="btn btn-primary">Reportes</a>
        <br>
        <a href="reportes.jsp" class="img-link">
            <img src="Vista/Imagenes/reportes.png" alt="Reportes" class="img-reportes">
        </a>
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

