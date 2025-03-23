<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    // Verificar si hay un mensaje de error en la sesión
    String error = request.getParameter("error");
%>

<html>
    <head>
        <title>Login</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
        <link href="Vista/Css/login.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col logo-col">
                    <img src="Vista/Imagenes/logo.png"  alt="logo" class="logo"/>
                </div>
                <div class="col">               
                    <h2 class="fw-bold text-center py-5">Inicio de sesión</h2>

                    <%-- Mostrar mensaje de error si existe --%>
                    <% if ("1".equals(error)) { %>
                        <div class="alert alert-danger text-center">
                            Usuario o contraseña incorrectos, inténtelo de nuevo.
                        </div>
                    <% } %>

                    <%-- Mensaje de alerta para campos vacíos --%>
                    <div id="error-message" class="alert alert-danger text-center" style="display: none;">
                        Debe ingresar usuario y contraseña.
                    </div>

                    <form id="loginForm" action="LoginControl" method="POST">
                        <div class="mb-4">
                            <label class="form-label">Ingrese su usuario</label>
                            <input type="text" class="form-control" name="usuario" id="usuario">
                        </div>
                        <div class="mb-4">
                            <label class="form-label">Ingrese su contraseña</label>
                            <input type="password" class="form-control" name="contrasena" id="contrasena">
                        </div>

                        <p class="mt-3 text-center">
                            <a href="#">¿Olvidaste tu contraseña?</a>
                        </p>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Ingresar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous">
        </script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                document.getElementById("loginForm").addEventListener("submit", function (event) {
                    let usuario = document.getElementById("usuario").value.trim();
                    let contrasena = document.getElementById("contrasena").value.trim();
                    let alerta = document.getElementById("error-message");

                    if (usuario === "" || contrasena === "") {
                        event.preventDefault(); // Evita que el formulario se envíe
                        alerta.style.display = "block"; // Muestra la alerta
                    }
                });
            });
        </script>
    </body>
</html>
