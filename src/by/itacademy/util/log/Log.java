package by.itacademy.util.log;

import java.io.IOException;
import java.util.logging.*;

public class Log {

    public static Logger logger = Logger.getGlobal();

    public static void createNewFilehandlerForUser(String login) {
        try {
            FileHandler fh = new FileHandler("%h/IdeaProjects/task_final_maksim_perekladov/log_files/" +
                    login + ".log");
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);
        } catch (IOException e) {
            Log.logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
