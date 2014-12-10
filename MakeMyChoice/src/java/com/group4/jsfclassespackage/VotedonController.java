package com.group4.jsfclassespackage;

import com.group4.entitypackage.Votedon;
import com.group4.jsfclassespackage.util.JsfUtil;
import com.group4.jsfclassespackage.util.JsfUtil.PersistAction;
import com.group4.sessionbeanpackage.VotedonFacade;

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

@Named("votedonController")
@SessionScoped
public class VotedonController implements Serializable {

    @EJB
    private com.group4.sessionbeanpackage.VotedonFacade ejbFacade;
    private List<Votedon> items = null;
    private Votedon selected;

    public VotedonController() {
    }

    public Votedon getSelected() {
        return selected;
    }

    public void setSelected(Votedon selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private VotedonFacade getFacade() {
        return ejbFacade;
    }

    public Votedon prepareCreate() {
        selected = new Votedon();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("VotedonCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void addVote(int userId, int questionId, String leftRight) {
        Votedon votedon = new Votedon();
        votedon.setQuestionID(questionId);
        votedon.setUserID(userId);
        votedon.setLeftRight(leftRight);
        getFacade().create(votedon);
    }
    
    public boolean hasUserVoted(int userId, int questionId) {
        return !(getVotes(userId, questionId)).isEmpty();
    }
    
    public List<Votedon> getVotes(int userId, int questionId) {
        return getFacade().findByQueryTwoParams("SELECT a FROM Votedon a WHERE a.userID LIKE :USERID AND a.questionID LIKE :QUESTIONID", "USERID", userId, "QUESTIONID", questionId);
    }
    
    public String getStatusString(int userId, int questionId) {
        if (hasUserVoted(userId, questionId)) {
            return "You have voted on this question.";
        } else {
            return "Choose one of the options below to Vote:";
        }
    }
    
    public String getCheckImage(int userId, String leftRight) {
        System.out.println("Here it is! " + FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap());
        String questionIdString = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("qid");
        //I know this looks hacky. It's because PrimeFaces has different
        //parameter settings at different times depending on if this is the
        //first time the page is navigated to or not.
        if (questionIdString == null) {
            questionIdString = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("id");
        }
        int questionId = Integer.parseInt(questionIdString);
        List<Votedon> votes = getVotes(userId, questionId);
        if (votes.size() > 0 && votes.get(0).getLeftRight().equals(leftRight)) {
            return "images:checkedbox.gif";
        }
        return "images:uncheckedbox.gif";
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("VotedonUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("VotedonDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Votedon> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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

    public Votedon getVotedon(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Votedon> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Votedon> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Votedon.class)
    public static class VotedonControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VotedonController controller = (VotedonController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "votedonController");
            return controller.getVotedon(getKey(value));
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
            if (object instanceof Votedon) {
                Votedon o = (Votedon) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Votedon.class.getName()});
                return null;
            }
        }

    }

}
