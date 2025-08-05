package modelo.dao;

import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import modelo.entidad.Inventarios;
import modelo.entidad.Usuarios;
import modelo.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class InventarioDao {

    public List<Inventarios> listarInventarios() {
        List<Inventarios> lista = null;
        Session sesion = null;
        Transaction t = null;

        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            t = sesion.beginTransaction();
            lista = sesion.createQuery("FROM Inventarios").list();
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen())
            {
            
            }
        }

        return lista;
    }

    public void guardarInventarios(Inventarios inventarios, Usuarios usuario) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction t = sesion.beginTransaction();
        try {
            inventarios.setUsuarios(usuario);
            sesion.save(inventarios);
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) sesion.close();
        }
    }

    public void actualizarInventarios(Inventarios inventarios, String nuevoNombre, Double nuevoPrecio) {
        Session sesion = null;
        Transaction t = null;

        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            t = sesion.beginTransaction();

            Inventarios inventarioActualizado = (Inventarios) sesion.merge(inventarios);

            inventarioActualizado.setNombreProducto(nuevoNombre);
            inventarioActualizado.setPrecio(nuevoPrecio);
            inventarioActualizado.setCantidad(inventarios.getCantidad());
            inventarioActualizado.setPeso(inventarios.getPeso());
            inventarioActualizado.setFecha(inventarios.getFecha());
            inventarioActualizado.setArea(inventarios.getArea());

            Usuarios usuario = obtenerUsuarioAutenticado();
            inventarioActualizado.setUsuarios(usuario);

            sesion.update(inventarioActualizado);
            t.commit();
        } catch (Exception e) {
            if (t != null && t.isActive()) t.rollback();
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) sesion.close();
        }
    }

    public Inventarios buscarInventariosPorId(int id) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Inventarios inventario = null;
        try {
            inventario = (Inventarios) sesion.get(Inventarios.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) sesion.close();
        }
        return inventario;
    }

    public void eliminarInventario(Inventarios inventario) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction t = sesion.beginTransaction();
        try {
            Inventarios inventarioBD = (Inventarios) sesion.get(Inventarios.class, inventario.getIdInventarios());
            if (inventarioBD != null) {
                sesion.delete(inventarioBD);
                t.commit();
            }
        } catch (Exception e) {
            if (t != null) t.rollback();
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) sesion.close();
        }
    }

    public Double calcularValorTotal() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Double total = 0.0;
        try {
            String hql = "SELECT SUM(i.cantidad * i.peso * i.precio) FROM Inventarios i";
            total = (Double) sesion.createQuery(hql).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) sesion.close();
        }
        return total != null ? total : 0.0;
    }

    public Double calcularPesoTotal() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Double total = 0.0;
        try {
            String hql = "SELECT SUM(i.cantidad * i.peso) FROM Inventarios i";
            total = (Double) sesion.createQuery(hql).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) sesion.close();
        }
        return total != null ? total : 0.0;
    }

    public List<Inventarios> buscarInventarios(String producto, Date fecha, Integer areaId) {
        List<Inventarios> lista = null;
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sesion.beginTransaction();
        try {
            StringBuilder hql = new StringBuilder("SELECT DISTINCT i FROM Inventarios i ");
            hql.append("JOIN FETCH i.area a WHERE 1=1 ");
            if (producto != null && !producto.trim().isEmpty())
                hql.append("AND LOWER(i.nombreProducto) LIKE :prod ");
            if (fecha != null)
                hql.append("AND i.fecha = :fecha ");
            if (areaId != null)
                hql.append("AND a.idArea = :areaId ");

            Query q = sesion.createQuery(hql.toString());
            if (producto != null && !producto.trim().isEmpty())
                q.setParameter("prod", "%" + producto.toLowerCase() + "%");
            if (fecha != null)
                q.setParameter("fecha", fecha);
            if (areaId != null)
                q.setParameter("areaId", areaId);

            lista = q.list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) sesion.close();
        }
        return lista;
    }

    private Usuarios obtenerUsuarioAutenticado() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(false);
        return (Usuarios) httpSession.getAttribute("usuario");
    }
}
