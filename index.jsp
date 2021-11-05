<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inicia Sesión</title>
</head>
<body>
<%@ page import = "miguelm.contadorVisitas.RecepcionJSP" %>
<%@ page import="jakarta.servlet.http.Cookie" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<jsp:useBean id="inicioSesion" class="miguelm.contadorVisitas.RecepcionJSP" scope="session" />
<jsp:setProperty name="inicioSesion" property="*" />

<% if (request.getParameter("usuario")!=null && request.getParameter("clave")!=null) {
        if (inicioSesion.validarUsuario(request.getParameter("usuario"), request.getParameter("clave")) == true) { %>
            <form method="get" action="index.jsp">
                <h1>Bienvenido <%out.println(request.getParameter("usuario"));%></h1>
                <label for="alfarero">Alfarero</label><input type="radio" id="alfarero" name="clase" value="A">
                <label for="brujo">Brujo</label><input type="radio" id="brujo" name="clase" value="B">
                <label for="curtidor">Curtidor</label><input type="radio" id="curtidor" name="clase" value="C">
                <a href="index.jsp"><input type="submit"  value="Desconectar"/></a>
                <h3>Número de visitas: <%

                    Cookie unaCookie;
                    String nombreCookie = request.getParameter("usuario");
                    String contenidoCookie="usuario=" + request.getParameter("usuario") + "&clave=" + request.getParameter("clave");

                    try {
                        unaCookie = new Cookie(nombreCookie, contenidoCookie);
                        response.addCookie(unaCookie);

                        Cookie cookie = inicioSesion.getCookie(request, "contador" + nombreCookie);

                        Cookie clase = new Cookie("clase" + nombreCookie, request.getParameter("clase"));
                        response.addCookie(clase);

                        if (cookie != null) {
                            int cont = Integer.parseInt(cookie.getValue());
                            cont++;

                            cookie.setValue( "" + cont);
                        } else {
                            cookie = new Cookie("contador" + nombreCookie, "1");
                        }
                        out.println(cookie.getValue());
                        response.addCookie(cookie);
                    }
                    catch (Exception e){
                        out.println("Se produce una excepción: ");
                        out.println(e.getMessage());
                    }

                %></h3>
            </form>
<%      } else  { %>
            <h1>Usuario o Contraseña incorrectos</h1>
<%      }
    } else { %>
        <form method="get" action="index.jsp">
            <label>Usuario <input type="text" name="usuario" /></label> <br />
            <label>Clave <input type="password" name="clave" /></label> <br />
            <input type="submit" value="Autenticar" />
        </form>
<%  } %>
</body>
</html>
