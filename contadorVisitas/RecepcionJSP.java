package miguelm.contadorVisitas;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class RecepcionJSP extends HttpServlet{

        public boolean validarUsuario(String usuario, String clave) throws IOException {
            Map<String, String> arreglo_asociativo = new HashMap<String, String>();
            arreglo_asociativo.put("miguel", "miguel");
            arreglo_asociativo.put("carlos", "carlos");
            arreglo_asociativo.put("pedro", "pedro");
            boolean login;

            if (clave.equals( arreglo_asociativo.get(usuario))) { // el usuario y contraseña son correctos
                login = true;
            } else {
                login = false;
            }
            return login;
        }
        public Cookie getCookie(HttpServletRequest request, String name) {
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