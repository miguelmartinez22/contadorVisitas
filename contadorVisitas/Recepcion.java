package miguelm.contadorVisitas;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.NewCookie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/Recepcion")
public class Recepcion extends HttpServlet {
    private static final String BR = "<br />";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> arreglo_asociativo = new HashMap<String, String>();
        arreglo_asociativo.put("miguel", "miguel");
        arreglo_asociativo.put("carlos", "carlos");
        arreglo_asociativo.put("pedro", "pedro");

        String validarUsuario = request.getParameter("usuario");
        String validarContraseña = request.getParameter("clave");
        if (validarUsuario != null && validarContraseña != null) {  // el formulario no está vacio
            if (validarContraseña.equals( arreglo_asociativo.get(validarUsuario))) { // el usuario y contraseña son correctos

                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                // se inicializa un objeto Cookie
                Cookie unaCookie;

                // Recepción de parámetros
                String nombreCookie = request.getParameter("usuario");

                String contenidoCookie=request.getQueryString();

                try {
                    // se crea el objeto cookie en el servidor
                    unaCookie = new Cookie(nombreCookie, contenidoCookie);
                    // se añade a la respuesta para enviar al cliente
                    response.addCookie(unaCookie);

                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Bienvenido</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Bienvenido " + unaCookie.getName() + "</h1>");
                    out.println("<label for=\"alfarero\">Alfarero</label><input type=\"radio\" id=\"alfarero\" name=\"clase\" value=\"A\" />\n" +
                            "<label for=\"brujo\">Brujo</label><input type=\"radio\" id=\"brujo\" name=\"clase\" value=\"B\" />\n " +
                            "<label for=\"curtidor\">Curtidor</label><input type=\"radio\" id=\"curtidor\" name=\"clase\" value=\"C\" />\n " +
                            "<a href=\"/introjee/index.html\"><input type=\"submit\"  value=\"Desconectar\" />\n </a>");

                    String claseElegida = request.getParameter("clase");
                    Cookie clase = new Cookie("clase" + nombreCookie, claseElegida);
                    response.addCookie(clase);

                    Cookie cookie = getCookie(request, "contador" + nombreCookie);

                    if (cookie != null) {
                        int cont = Integer.parseInt(cookie.getValue());
                        cont++;

                        cookie.setValue( "" + cont);
                    } else {
                        cookie = new Cookie("contador" + nombreCookie, "1");
                    }

                    response.addCookie(cookie);
                    out.print("<h3>Nº de visitas: <h3>" + cookie.getValue());

                    out.println("</body>");
                    out.println("</html>");
                }
                catch (Exception e){
                    out.println("Se produce una excepción: ");
                    out.println(e.getMessage());
                    out.println(BR);
                }
            } else {
                PrintWriter out = response.getWriter();
                out.println("<h1>Usuario o contraseña incorrectos</h1>");
            }
        } else {  // no se ha recibido el parámetro, se devuelve a la página inicial
            PrintWriter out = response.getWriter();
            out.println("<h1>Por favor, rellena el formulario</h1>");
        }

    }
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }

        return null;
    }

}
