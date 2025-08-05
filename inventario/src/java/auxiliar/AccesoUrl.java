package auxiliar;

import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class AccesoUrl implements PhaseListener {
    
    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext fc = event.getFacesContext();
        
        // Manejar excepción de vista expirada
        if (fc.getExternalContext().getRequest() instanceof javax.servlet.http.HttpServletRequest) {
            Exception exception = (Exception) fc.getExternalContext().getRequestMap().get("javax.servlet.error.exception");
            if (exception instanceof ViewExpiredException) {
                redirectToLogin(fc);
                return;
            }
        }
        
        // Obtener la página actual
        String currentPage = fc.getViewRoot().getViewId();
        boolean esPaginaLogin = currentPage != null && currentPage.contains("index.xhtml");
        
        // Verificar la sesión del usuario
        HttpSession sesion = (HttpSession) fc.getExternalContext().getSession(false);
        Object usuario = null;
        
        if (sesion != null) {
            try {
                usuario = sesion.getAttribute("usuario");
            } catch (IllegalStateException e) {
                // Sesión invalidada
                sesion = null;
            }
        }
        
        // Si no es la página de login y el usuario no está logueado, redirige
        if (!esPaginaLogin && usuario == null) {
            redirectToLogin(fc);
        }
    }
    
    private void redirectToLogin(FacesContext fc) {
        try {
            NavigationHandler nh = fc.getApplication().getNavigationHandler();
            nh.handleNavigation(fc, null, "/index.xhtml?faces-redirect=true");
        } catch (Exception e) {
            // Si falla la navegación, redirigir directamente
            try {
                fc.getExternalContext().redirect(fc.getExternalContext().getRequestContextPath() + "/faces/index.xhtml");
            } catch (Exception ex) {
                // Último recurso
                ex.printStackTrace();
            }
        }
    }
    
    @Override
    public void beforePhase(PhaseEvent event) {
        // No se necesita lógica previa
    }
    
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}