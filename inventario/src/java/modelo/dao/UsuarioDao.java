package modelo.dao;

import java.util.List;
import modelo.entidad.Usuarios;
import modelo.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDao {

    public List<Usuarios> listarUsuarios() {
        List<Usuarios> lista = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction t = sesion.beginTransaction();

        String hql = "FROM Usuarios";
        try {
            lista = sesion.createQuery(hql).list();
            t.commit();
            sesion.close();
        } catch (Exception e) {
            t.rollback();
        }
        return lista;
    }
    
    
    
    public Usuarios login(Usuarios usuarios) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Usuarios u WHERE u.usuario = :usuario";
        Query q = sesion.createQuery(hql);
        q.setParameter("usuario", usuarios.getUsuario());
        Usuarios usuario = (Usuarios) q.uniqueResult();
        sesion.close();
        return usuario;
    }

   public void agregarUsuario(Usuarios usuario) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction t = sesion.beginTransaction();
        try {
            usuario.setClave(BCrypt.hashpw(usuario.getClave(), BCrypt.gensalt()));
            sesion.save(usuario);
            t.commit();
            sesion.close();
        } catch (Exception e) {
            t.rollback();
        }
    }

      public void actualizarUsuario(Usuarios usuario) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    Transaction t = null;
    
    try {
        t = sesion.beginTransaction();
        Usuarios usuarioExistente = (Usuarios) sesion.get(Usuarios.class, usuario.getIdUsuarios());
        
        if (usuarioExistente != null) {
            // Actualiza todos los campos excepto la clave
            usuarioExistente.setUsuario(usuario.getUsuario());
            usuarioExistente.setRol(usuario.getRol());
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setTelefono(usuario.getTelefono()); 
            usuarioExistente.setCorreo(usuario.getCorreo());
            
            // Solo actualiza la clave si se proporcionó una nueva
            if (usuario.getClave() != null && !usuario.getClave().trim().isEmpty()) {
                String nuevaClave = BCrypt.hashpw(usuario.getClave(), BCrypt.gensalt());
                usuarioExistente.setClave(nuevaClave);
            }
            
            sesion.update(usuarioExistente);
            t.commit();
        }
    } catch (Exception e) {
        if (t != null) {
            t.rollback();
        }
        e.printStackTrace();
    } finally {
        if (sesion != null) {
            sesion.close();
        }
    }
}

    public Usuarios leerUsuario(int idUsuarios) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Usuarios usuario = null;
        try {
            usuario = (Usuarios) sesion.get(Usuarios.class, idUsuarios);
            sesion.close();
        } catch (Exception e) {
            sesion.close();
        }
        return usuario;
    }

    public void eliminarUsuario(Usuarios usuario) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction t = sesion.beginTransaction();
        try {
            sesion.delete(usuario);
            t.commit();
            sesion.close();
        } catch (Exception e) {
            t.rollback();
        }
    }
    
    
    
    public Usuarios obtenerUsuarioPorId(Integer id) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Usuarios usuario = null;
        try {
            usuario = (Usuarios) sesion.get(Usuarios.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sesion.close();
        }
        return usuario;
    }
    
    
    public void cambiarClave(int idUsuario, String nuevaClave) {
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    Transaction t = null;
    
    try {
        t = sesion.beginTransaction();
        Usuarios usuarioExistente = (Usuarios) sesion.get(Usuarios.class, idUsuario);
        
        if (usuarioExistente != null && nuevaClave != null && !nuevaClave.trim().isEmpty()) {
            String claveHasheada = BCrypt.hashpw(nuevaClave, BCrypt.gensalt());
            usuarioExistente.setClave(claveHasheada);
            sesion.update(usuarioExistente);
            t.commit();
        }
    } catch (Exception e) {
        if (t != null) {
            t.rollback();
        }
        e.printStackTrace();
    } finally {
        if (sesion != null) {
            sesion.close();
        }
    }
}
}
