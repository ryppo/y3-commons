package org.y3.commons.application;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.y3.commons.lang.PropertiesHelper;

/** 
 * <p>Title: org.y3.commons.application - IApplication</p>
 * <p>Description: </p>
 * <p>Copyright: 2014</p>
 * <p>Organisation: IT-Happens.de</p>
 * @author Christian.Rybotycky
*/
public abstract class IApplication {
    
    private static Properties userProperties;
    private static Logger logger;
    private static ResourceBundle resourceBundle;
    
    public IApplication() {
        init();
    }
    
    public abstract void run();
    
    public abstract String getUserPropertiesLocation();
    
    public abstract String getLoggerPropertiesLocation();
    
    public abstract String getResourceBundleLocation();
    
    public abstract String getApplicationName();
    
    public static Logger LOG() {
        return logger;
    }
    
    public static Properties UP() {
        return userProperties;
    }
    
    public static ResourceBundle RB() {
        return resourceBundle;
    }

    private void init() {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            // OS X menu bar
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", getApplicationName());
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            //setup logging
            PropertyConfigurator.configure(getClass().getResourceAsStream(getLoggerPropertiesLocation()));
            logger = Logger.getLogger(getClass());
            //setup user properties
            File userPropertiesFile = new File(getUserPropertiesLocation());
            if (!userPropertiesFile.exists()) {
                LOG().debug("User properties file does not exist at: " + getUserPropertiesLocation());
                userProperties = new Properties();
                PropertiesHelper.saveProperties(userProperties, getUserPropertiesLocation(), true);
            }
            userProperties = PropertiesHelper.loadProperties(getUserPropertiesLocation(), true);
            //setup resource bundle
            resourceBundle = ResourceBundle.getBundle(getResourceBundleLocation());
        } catch (IOException ex) {
            LOG().error("Save new properties file to " + getUserPropertiesLocation() + " failed", ex);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOG().error(ex);
        }
        prepare();
    }
    
    public abstract void prepare();
    
    public abstract void beforeShutDown();

    /**
     * Receive shut down listener object which manages the application shut down operations
     * @return shut down listener object
     */
    public WindowListener getShutDownListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    //prepare shutdown
                    beforeShutDown();
                    //save properties
                    PropertiesHelper.saveProperties(UP(), getUserPropertiesLocation(), true);
                } catch (IOException ex) {
                    LOG().debug(ex);
                }
                LOG().debug("System exit by window closing");
                System.exit(0);
            }
        };
    }

}
