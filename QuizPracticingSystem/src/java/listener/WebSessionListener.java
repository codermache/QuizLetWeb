
package listener;

import bean.CustomerQuiz;
import bean.QuizQuizHandle;
import bean.User;
import dao.CustomerQuizDAO;
import dao.QuizQuizHandleDAO;
import dao.ViewDAO;
import dao.impl.CustomerQuizDAOImpl;
import dao.impl.QuizQuizHandleDAOImpl;
import dao.impl.ViewDAOImpl;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

public class WebSessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    public static Timer timer;

    /**
     * Class này có mục đích override method run của TimerTask để thực hiện
     * remove session.
     *
     * @author ADMN
     */
    class RemindTask extends TimerTask {

        private HttpSessionBindingEvent se;

        public RemindTask(HttpSessionBindingEvent se) {
            this.se = se;
        }

        @Override
        public void run() {
            se.getSession().removeAttribute("doingQuiz");
            System.out.println("Quiz Expired!");
            timer.cancel(); //Terminate the timer thread
        }
    }

    public void Time(int seconds, HttpSessionBindingEvent se) {
        timer = new Timer();
        timer.schedule(new RemindTask(se), seconds *1000); // schedule the task
    }

    /**
     * set a timer for auto submit quiz
     *
     * @param se the session that be changed
     * <code>HttpSessionBindingEvent</code> object
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        if (se.getName().equalsIgnoreCase("doingQuiz")) {
            int defaultPracticeQuizTime = 7200;
            int timeOut = defaultPracticeQuizTime;
            QuizQuizHandle doingQuiz = (QuizQuizHandle) se.getValue();
            if (doingQuiz.getQuiz().getTestTypeId() != 3) {
                timeOut = doingQuiz.getQuiz().getQuizDuration() + 3;
            }
            Time(timeOut, se);
        }
    }

    /**
     * set a timer for auto submit quiz
     *
     * @param se the session that be changed
     * <code>HttpSessionBindingEvent</code> object
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        //quiz submit
        if (se.getName().equalsIgnoreCase("doingQuiz")) {
            try {
                QuizQuizHandle doingQuiz = (QuizQuizHandle) se.getValue();
                QuizQuizHandleDAO quizQHInterface = new QuizQuizHandleDAOImpl();
                int quizId = doingQuiz.getQuiz().getQuizId();
                User user = doingQuiz.getUser();
                //Score of this quiz    
                double score = quizQHInterface.calculateScore(doingQuiz);
                //Date of this quiz
                int time = doingQuiz.getTime();
                if (time < 0) {
                    time = 0;
                }
                if (doingQuiz.getQuiz().getTestTypeId() == 1) {
                    time = doingQuiz.getQuiz().getQuizDuration() - time;
                }
                long millis = System.currentTimeMillis();
                Timestamp dateSql = new Timestamp(millis);
                //Insert into CustomerQuiz table in database
                CustomerQuiz customerQuiz = new CustomerQuiz(0, quizId, user.getUserId(), (int) score, time, dateSql, true);
                CustomerQuizDAO customerQuizInterface = new CustomerQuizDAOImpl();
                customerQuizInterface.addCustomerQuiz(customerQuiz);
                //Insert into TakeAnswer table in database;
                customerQuizInterface.addTakeAnswer(doingQuiz);
                timer.cancel();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ViewDAO IView = new ViewDAOImpl();
        if (se.getSession().isNew()) {
            try {
                System.out.println("########################### +1 View");
                IView.updateView();
            } catch (Exception e) {

            }
        }

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("########################### -1 View");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
