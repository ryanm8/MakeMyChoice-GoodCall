package com.group4.jsfclassespackage;

import com.group4.entitypackage.Comments;
import com.group4.entitypackage.Question;
import com.group4.entitypackage.User;
import com.group4.jsfclassespackage.util.JsfUtil;
import com.group4.jsfclassespackage.util.JsfUtil.PersistAction;
import com.group4.sessionbeanpackage.CommentsFacade;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named("commentsController")
@SessionScoped
public class CommentsController implements Serializable {

    @EJB
    private com.group4.sessionbeanpackage.CommentsFacade ejbFacade;
    private List<Comments> items = null;
    private Comments selected;
    
    @PersistenceContext 
    private EntityManager em;

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
    
    public List<Comments> getById() {
        String questionId = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("qid");
        //I know this looks hacky. It's because PrimeFaces has different
        //parameter settings at different times depending on if this is the
        //first time the page is navigated to or not.
        if (questionId == null) {
            questionId = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("id");
        }
        return getFacade().findByQueryOneParam("SELECT a FROM Comments a WHERE a.questionID LIKE :ID ORDER BY a.timeStamp DESC", "ID", Integer.parseInt(questionId));
    }
    
    public String postComment(int userId) {
        String questionId = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("qid");
        //I know this looks hacky. It's because PrimeFaces has different
        //parameter settings at different times depending on if this is the
        //first time the page is navigated to or not.
        if (questionId == null) {
            questionId = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("id");
        }
        selected.setId(0);
        selected.setPosterID(userId);
        selected.setQuestionID(Integer.parseInt(questionId));
        selected.setTimeStamp(new Timestamp((new Date()).getTime()));
        //System.out.println("This is the selected thing " + selected.getCommentText() + selected.getTimeStamp());
        create();
        Question q = (Question) em.createNamedQuery("Question.natfindById").setParameter(1, questionId).getSingleResult();
        User user = (User) em.createNamedQuery("user.findById").setParameter(1, q.getAskerID()).getSingleResult();
        User commenter = (User) em.createNamedQuery("user.findById").setParameter(1, userId).getSingleResult();
        sendEmail(user.getFirstName(), q.getTitle(), user.getEmail(), selected.getCommentText(), commenter.getPid());
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
    /**
     * Sends email from reminder.
     * @param name - Who the email is being sent to
     * @param subject - The subject of the method
     * @param email - The user's email
     * @param body - The reminder to be sent.
     */
    public void sendEmail(String name, String subject, String email, String body, String commenter) {
        final String username = "planneroftheapes@gmail.com";
		final String password = "monkeymonkey123";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("planneroftheapes@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject("New Comment on your Make My Coice Question: " + subject);
			message.setText("Hello " + name + ","
				+ "\n\n Make My Choice just wanted to let you know you that:\n"
                                + "\n\t" + commenter + " commented on your question: \""+ body +"\"");
                        //sendEmail(selected.getCommentText(), commenter.getPid());
			Transport.send(message);
 
			System.out.println("Email Sent to " + email);
 
		} catch (MessagingException e) {
			System.out.println("Email Not Sent - Failure");
		}
    }

}
