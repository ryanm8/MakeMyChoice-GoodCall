package com.group4.jsfclassespackage;

import com.group4.entitypackage.Comments;
import com.group4.jsfclassespackage.util.JsfUtil;
import com.group4.jsfclassespackage.util.JsfUtil.PersistAction;
import com.group4.sessionbeanpackage.CommentsFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.sql.Timestamp;
import java.util.Date;

@Named("commentsController")
@SessionScoped
public class CommentsController implements Serializable {

    @EJB
    private com.group4.sessionbeanpackage.CommentsFacade ejbFacade;
    private List<Comments> items = null;
    private Comments selected;

    public CommentsController() {
    }

    public Comments getSelected() {
        return selected;
    }

    public void setSelected(Comments selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CommentsFacade getFacade() {
        return ejbFacade;
    }

    public Comments prepareCreate() {
        selected = new Comments();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CommentsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CommentsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CommentsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Comments> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    /**
     * Uses the questionId parameter to return all comments for the given
     * question, ordered by timestamp
     *
     * @return list of questions
     */
    public List<Comments> getById() {
        String questionId = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("qid");
        //Necessary because the id will be different depending on when
        //Primefaces calls this function.
        if (questionId == null) {
            questionId = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("id");
        }
        return getFacade().findByQueryOneParam("SELECT a FROM Comments a WHERE a.questionID LIKE :ID ORDER BY a.timeStamp DESC", "ID", Integer.parseInt(questionId));
    }

    /**
     * Adds the current selected comment to the database with the given userId
     * and the questionId parameter set.
     *
     * @param userId the id of the user posting the comment
     * @return a string used to refresh the page.
     */
    public String postComment(int userId) {
        String questionId = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("qid");
        //Necessary because the id will be different depending on when
        //Primefaces calls this function.
        if (questionId == null) {
            questionId = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("id");
        }
        selected.setId(0);
        selected.setPosterID(userId);
        selected.setQuestionID(Integer.parseInt(questionId));
        selected.setTimeStamp(new Timestamp((new Date()).getTime()));
        getFacade().create(selected);
        return "question.xhtml?faces-redirect=true&" + "qid=" + questionId;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Comments getComments(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Comments> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Comments> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Comments.class)
    public static class CommentsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CommentsController controller = (CommentsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "commentsController");
            return controller.getComments(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Comments) {
                Comments o = (Comments) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Comments.class.getName()});
                return null;
            }
        }

    }

}
