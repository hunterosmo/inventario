package controlador;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import modelo.dao.UsuarioDao;
import modelo.entidad.Usuarios;
import org.mindrot.jbcrypt.BCrypt;

@ManagedBean
@SessionScoped
public class LoginControl implements Serializable {

    private Usuarios usuario;

    public LoginControl() {
        this.usuario = new Usuarios();
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String login() {
        FacesMessage mensaje;
        UsuarioDao usuarioDao = new UsuarioDao();

        try {
            String passwordIngresada = usuario.getClave();
            Usuarios usuarioDB = usuarioDao.login(usuario);

            if (usuarioDB != null) {
                if (BCrypt.checkpw(passwordIngresada, usuarioDB.getClave())) {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .getSessionMap().put("usuario", usuarioDB);

                    String rol = usuarioDB.getRol();

                    if ("visitante".equalsIgnoreCase(rol)) {
                        return "/visitante/dashboard.xhtml?faces-redirect=true";
                    } else if ("admin".equalsIgnoreCase(rol)) {
                        return "/admin/dashboard.xhtml?faces-redirect=true";
                    } else {
                        mensaje = new FacesMessage(FacesMessage.SEVERITY_WARN, "Rol inválido", "Acceso denegado.");
                        FacesContext.getCurrentInstance().addMessage(null, mensaje);
                        return "/index.xhtml?faces-redirect=true";
                    }
                } else {
                    mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Contraseña incorrecta.");
                    FacesContext.getCurrentInstance().addMessage(null, mensaje);
                    return "";
                }
            } else {
                mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario no encontrado.");
                FacesContext.getCurrentInstance().addMessage(null, mensaje);
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error del sistema", "Intente más tarde.");
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
    }

    public String cerrarSession() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
        }
        return "/index.xhtml?faces-redirect=true";
    }

    public Usuarios getUsuarioAutenticado() {
        return (Usuarios) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("usuario");
    }

    public String getUsuarioInicial() {
        try {
            if (usuario != null && usuario.getUsuario() != null && !usuario.getUsuario().isEmpty()) {
                return usuario.getUsuario().substring(0, 1).toUpperCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
