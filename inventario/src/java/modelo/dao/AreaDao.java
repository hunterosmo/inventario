package modelo.dao;

import java.util.List;
import modelo.entidad.Area;
import modelo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

public class AreaDao {

    public List<Area> listarAreas() {
        List<Area> lista = null;
        Session sesion = null;
        Transaction t = null;

        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            t = sesion.beginTransaction();
            lista = sesion.createQuery("FROM Area").list();
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) sesion.close();
        }

        return lista;
    }

    public void agregar(Area area) {
        Session sesion = null;
        Transaction t = null;

        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            t = sesion.beginTransaction();
            sesion.save(area);
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) sesion.close();
        }
    }

    public void modificar(Area area) {
        Session sesion = null;
        Transaction t = null;

        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            t = sesion.beginTransaction();
            sesion.update(area);
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) sesion.close();
        }
    }

    public void eliminar(Area area) {
        Session sesion = null;
        Transaction t = null;

        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            t = sesion.beginTransaction();
            sesion.delete(area);
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) sesion.close();
        }
    }

    public Area buscarPorId(int idArea) {
        Session sesion = null;
        Area area = null;

        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            area = (Area) sesion.get(Area.class, idArea);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sesion != null && sesion.isOpen()) sesion.close();
        }

        return area;
    }
}
