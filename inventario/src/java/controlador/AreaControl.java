package controlador;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.dao.AreaDao;
import modelo.entidad.Area;

@ManagedBean
@ViewScoped
public class AreaControl implements Serializable {

    private List<Area> listaAreas;
    private Area area = new Area();

    public AreaControl() {
        // Inicializa lista si deseas mostrar algo de entrada
        AreaDao dao = new AreaDao();
        listaAreas = dao.listarAreas();
    }

    public List<Area> getListaAreas() {
        AreaDao dao = new AreaDao();
        listaAreas = dao.listarAreas();
        return listaAreas;
    }

    public void setListaAreas(List<Area> listaAreas) {
        this.listaAreas = listaAreas;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void limpiarArea() {
        area = new Area();
    }

    public void agregarArea() {
        try {
            AreaDao dao = new AreaDao();
            dao.agregar(area);
            limpiarArea();
        } catch (Exception e) {
            System.out.println("Error al agregar área: " + e.getMessage());
        }
    }

    public void modificarArea() {
        try {
            AreaDao dao = new AreaDao();
            dao.modificar(area);
            limpiarArea();
        } catch (Exception e) {
            System.out.println("Error al modificar área: " + e.getMessage());
        }
    }

    public void eliminarArea() {
        try {
            AreaDao dao = new AreaDao();
            dao.eliminar(area);
            limpiarArea();
        } catch (Exception e) {
            System.out.println("Error al eliminar área: " + e.getMessage());
        }
    }
}
