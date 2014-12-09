package com.group4.jsfclassespackage;

import com.group4.entitypackage.Question;
import com.group4.jsfclassespackage.util.JsfUtil;
import com.group4.jsfclassespackage.util.JsfUtil.PersistAction;
import com.group4.sessionbeanpackage.QuestionFacade;

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



@Named("questionController")
@SessionScoped
public class QuestionController implements Serializable {

    @EJB
    private com.group4.sessionbeanpackage.QuestionFacade ejbFacade;
    private List<Question> items = null;
    private Question selected;
    

    public QuestionController() {
    }

    public Question getSelected() {
        return selected;
    }

    public void setSelected(Question selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuestionFacade getFacade() {
        return ejbFacade;
    }

    public Question prepareCreate() {
        selected = new Question();
        initializeEmbeddableKey();
        return selected;
    }

    public String navigateWithSelection(String id) {
        return "question.xhtml?faces-redirect=true&" + "qid=" + id;
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("QuestionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("QuestionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("QuestionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Question> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public Question getQuestionById(String id) {
        System.out.println("This is the id: " + id);
        return getFacade().find(Integer.parseInt(id));
    }
    
    public String voteLeftOption(int userId, VotedonController 
            votedonController) {
        String questionId = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("id");
        if (!votedonController.hasUserVoted(userId, Integer.parseInt(questionId))) {
            votedonController.addVote(userId, Integer.parseInt(questionId), "Left");
            Question question = getQuestionById(questionId);

            question.setNumberLeftVotes(question.getNumberLeftVotes() + 1);
            getFacade().edit(question);
            return navigateWithSelection(questionId);
        }
        return "";
    }
    
     public String voteRightOption(int userId, VotedonController 
            votedonController) {
        String questionId = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("id");
        if (!votedonController.hasUserVoted(userId, Integer.parseInt(questionId))) {
            votedonController.addVote(userId, Integer.parseInt(questionId), "Right");
            Question question = getQuestionById(questionId);

            question.setNumberRightVotes(question.getNumberRightVotes() + 1);
            getFacade().edit(question);
            return navigateWithSelection(questionId);
        }
        return "";
    }
     
    public String getStatusString(int userId, String questionId, 
            VotedonController votedonController) {
        if (getQuestionById(questionId).getOpenClosed().equals("Open")) {
            return votedonController.getStatusString(userId, Integer.parseInt(questionId));
        }
        return "This question is now closed.";
    }
    
    public boolean canUserVote(int userId, String questionId, 
            VotedonController votedonController) {
        if (getQuestionById(questionId).getOpenClosed().equals("Open")) {
            return !votedonController.hasUserVoted(userId, Integer.parseInt(questionId));
        }
        return false;
    }
    
    public String isVotingDisabled(int userId, 
            VotedonController votedonController) {
        System.out.println(FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap());
        String questionId = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("qid");
        //I know this looks hacky. It's because PrimeFaces has different
        //parameter settings at different times depending on if this is the
        //first time the page is navigated to or not.
        if (questionId == null) {
            questionId = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("id");
        }
        if (canUserVote(userId, questionId, votedonController)) {
            return "false";
        }
        return "true";
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

    public Question getQuestion(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Question> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Question> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    /**
     * Returns list of group Assignments for given AssigneeId
     * @param assigneeId The id for the assignee
     * @return List of Assignments
     */
    public List<Question> getItemsByOwner(int assigneeId) {
        items = getFacade().findByQueryOneParam("SELECT a FROM Question a WHERE a.askerID LIKE :ID", "ID", assigneeId);
        return items;
    }
    
    /**
     * Returns list of group Assignments for given AssigneeId
     * @param assigneeId The id for the assignee
     * @return List of Assignments
     */
    public List<Question> getallItems() {
        items = getFacade().findByQueryNoParam("SELECT a FROM Question a ORDER BY a.timeStamp DESC");
        return items;
    }
    

    @FacesConverter(forClass = Question.class)
    public static class QuestionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestionController controller = (QuestionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questionController");
            return controller.getQuestion(getKey(value));
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
            if (object instanceof Question) {
                Question o = (Question) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Question.class.getName()});
                return null;
            }
        }

    }

}
