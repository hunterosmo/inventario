package controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import modelo.dao.InventarioDao;
import modelo.dao.AreaDao;
import modelo.entidad.Area;
import modelo.entidad.Inventarios;
import modelo.entidad.Usuarios;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import javax.faces.context.ExternalContext;
import java.io.OutputStream;

@ManagedBean
@ViewScoped
public class InventarioControl implements Serializable {

    private Inventarios inventario = new Inventarios();
    private List<Inventarios> listaInventarios = new ArrayList<>();
    private List<Area> listaAreas = new ArrayList<>();

    private InventarioDao inventarioDao = new InventarioDao();
    private AreaDao areaDao = new AreaDao();

    private String palabraBusqueda;
    private Date fechaBusqueda;
    private Integer idAreaBusqueda;
    private boolean buscando = false;

    @PostConstruct
    public void init() {
        listaInventarios = inventarioDao.listarInventarios();
        listaAreas = areaDao.listarAreas();
    }

    public void agregarInventario() {
        Usuarios usuario = obtenerUsuarioSesion();
        inventarioDao.guardarInventarios(inventario, usuario);
        limpiarFormulario();
        recargarInventario();
    }

    public void modificarInventario() {
        inventarioDao.actualizarInventarios(inventario, inventario.getNombreProducto(), inventario.getPrecio());
        limpiarFormulario();
        recargarInventario();
    }

    public void eliminarInventario() {
        inventarioDao.eliminarInventario(inventario);
        recargarInventario();
    }

    public Double getValorTotalInventario() {
        if (listaInventarios == null) return 0.0;
        return listaInventarios.stream()
            .filter(i -> i != null && i.getCantidad() != null && i.getPeso() != null && i.getPrecio() != null)
            .mapToDouble(i -> i.getCantidad() * i.getPeso() * i.getPrecio())
            .sum();
    }

    public Double getPesoTotalInventario() {
        if (listaInventarios == null) return 0.0;
        return listaInventarios.stream()
            .filter(i -> i != null && i.getCantidad() != null && i.getPeso() != null)
            .mapToDouble(i -> i.getCantidad() * i.getPeso())
            .sum();
    }

    public void buscarInventarios() {
        buscando = true;
        listaInventarios = inventarioDao.buscarInventarios(palabraBusqueda, fechaBusqueda, idAreaBusqueda);
    }

    public void limpiarBusqueda() {
        palabraBusqueda = null;
        fechaBusqueda = null;
        idAreaBusqueda = null;
        buscando = false;
        listaInventarios = inventarioDao.listarInventarios();
    }

    private void recargarInventario() {
        if (buscando) {
            buscarInventarios();
        } else {
            listaInventarios = inventarioDao.listarInventarios();
        }
    }

    public void limpiarFormulario() {
        inventario = new Inventarios();
        inventario.setFecha(new Date());
    }

    private Usuarios obtenerUsuarioSesion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return (Usuarios) session.getAttribute("usuario");
    }

    // ================== PDF ====================

    public void exportarPdf() {
        try {
            Document document = new Document(PageSize.A4.rotate());
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=inventory_report.pdf");

            OutputStream output = externalContext.getResponseOutputStream();
            PdfWriter.getInstance(document, output);

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph title = new Paragraph("MSQM Inventory Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);

            String[] headers = {"Date", "Product", "Area", "Quantity", "Weight", "Price", "Total Value", "Total Weight"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                table.addCell(cell);
            }

            double totalValor = 0.0;
            double totalPeso = 0.0;

            for (Inventarios inv : listaInventarios) {
                if (inv == null || inv.getArea() == null) continue;

                double valor = inv.getCantidad() * inv.getPeso() * inv.getPrecio();
                double peso = inv.getCantidad() * inv.getPeso();

                table.addCell(inv.getFecha().toString());
                table.addCell(inv.getNombreProducto());
                table.addCell(inv.getArea().getNombre());
                table.addCell(String.valueOf(inv.getCantidad()));
                table.addCell(String.valueOf(inv.getPeso()));
                table.addCell("$" + String.format("%.2f", inv.getPrecio()));
                table.addCell("$" + String.format("%.2f", valor));
                table.addCell(String.format("%.2f", peso));

                totalValor += valor;
                totalPeso += peso;
            }

            document.add(table);
            document.add(new Paragraph(" "));

            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Paragraph totalValuePara = new Paragraph("Total Inventory Value: $" + String.format("%.2f", totalValor), totalFont);
            Paragraph totalWeightPara = new Paragraph("Total Inventory Weight: " + String.format("%.2f", totalPeso) + " Lb", totalFont);
            totalValuePara.setSpacingBefore(10);
            totalWeightPara.setSpacingBefore(5);
            document.add(totalValuePara);
            document.add(totalWeightPara);

            document.close();
            facesContext.responseComplete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================== Getters y Setters ====================

    public Inventarios getInventario() { return inventario; }
    public void setInventario(Inventarios inventario) { this.inventario = inventario; }
    public List<Inventarios> getListaInventarios() { return listaInventarios; }
    public void setListaInventarios(List<Inventarios> listaInventarios) { this.listaInventarios = listaInventarios; }
    public List<Area> getListaAreas() { return listaAreas; }
    public void setListaAreas(List<Area> listaAreas) { this.listaAreas = listaAreas; }
    public String getPalabraBusqueda() { return palabraBusqueda; }
    public void setPalabraBusqueda(String palabraBusqueda) { this.palabraBusqueda = palabraBusqueda; }
    public Date getFechaBusqueda() { return fechaBusqueda; }
    public void setFechaBusqueda(Date fechaBusqueda) { this.fechaBusqueda = fechaBusqueda; }
    public Integer getIdAreaBusqueda() { return idAreaBusqueda; }
    public void setIdAreaBusqueda(Integer idAreaBusqueda) { this.idAreaBusqueda = idAreaBusqueda; }
    public boolean isBuscando() { return buscando; }
    public void setBuscando(boolean buscando) { this.buscando = buscando; }
}
