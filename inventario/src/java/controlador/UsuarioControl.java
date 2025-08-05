package controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.dao.UsuarioDao;
import modelo.entidad.Usuarios;

@ManagedBean
@ViewScoped
public class UsuarioControl implements Serializable {

    private List<Usuarios> listaUsuarios;
    private Usuarios usuario;

    public UsuarioControl() {
        usuario = new Usuarios();
    }

    public List<Usuarios> getListaUsuarios() {
        UsuarioDao dao = new UsuarioDao();
        listaUsuarios = dao.listarUsuarios();
        return listaUsuarios;
    }

    public List<Usuarios> getUsuarioLogueado() {
        List<Usuarios> usuarioLogueadoList = new ArrayList<>();
        Usuarios usuarioLogueado = (Usuarios) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("usuario");
        if (usuarioLogueado != null) {
            usuarioLogueadoList.add(usuarioLogueado);
        }
        return usuarioLogueadoList;
    }

    public void setListaUsuarios(List<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public void limpiarUsuario() {
        usuario = new Usuarios();
    }

    public void agregarUsuario() {
        try {
            UsuarioDao dao = new UsuarioDao();
            dao.agregarUsuario(usuario);
            limpiarUsuario();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarUsuario() {
    try {
        UsuarioDao dao = new UsuarioDao();
        // Limpiar la clave antes de actualizar para evitar hashear
        String claveTemp = usuario.getClave();
        usuario.setClave(null); // No actualizar la clave en modificación general
        dao.actualizarUsuario(usuario);
        usuario.setClave(claveTemp); // Restaurar por si acaso
        limpiarUsuario();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void cambiarClave() {
    try {
        UsuarioDao dao = new UsuarioDao();
        dao.cambiarClave(usuario.getIdUsuarios(), usuario.getClave());
        limpiarUsuario();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void eliminarUsuario() {
        try {
            UsuarioDao dao = new UsuarioDao();
            dao.eliminarUsuario(usuario);
            limpiarUsuario();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
