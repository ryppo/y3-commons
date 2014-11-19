package org.y3.commons.swing;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import net.java.dev.designgridlayout.DesignGridLayout;
import org.y3.commons.lang.MessageBundle;

/** 
 * Title: com.sebn.gsd.common.swing - ApplicationInfoDialog
 * Description: Dialog to show application information
 * Copyright: 2014
 * <p>Organisation: IT-Happens.de</p>
* @author christian.rybotycky
*/
public class ApplicationInfoDialog extends JDialog {
    
    private String applicationName = null;
    private String applicationVersion = null;
    private String[] developers = null;
    private HashMap<String, String> additionalContentRows = null;
    private Image appIcon = null;
    private Icon appLogo = null;
    
    /**
     * Constructs the application info dialog with application standard information
     * @param owner dialog owner component
     * @param _applicationName name of application
     * @param _applicationVersion version of application
     * @param _developers names of the developers
     */
    public ApplicationInfoDialog(Frame owner, String _applicationName, String _applicationVersion, String[] _developers, Image _appIcon, Icon _appLogo) {
        super(owner, true);
        applicationName = _applicationName;
        applicationVersion = _applicationVersion;
        developers = _developers;
        appIcon = _appIcon;
        appLogo = _appLogo;
        buildUI();
        setLocationRelativeTo(owner);
    }
    
    /**
     * Constructs the application info dialog with application standard information
     * @param owner dialog owner component
     * @param _applicationName name of application
     * @param _applicationVersion version of application
     * @param _developers names of the developers
     * @param _additionalContentRows additional rows of content. each pair represents the row's label and text
     * @param _appIcon application icon
     * @param _appLogo application logo
     */
    public ApplicationInfoDialog(Frame owner, String _applicationName, String _applicationVersion, String[] _developers, HashMap<String, String> _additionalContentRows, Image _appIcon, Icon _appLogo) {
        super(owner, true);
        applicationName = _applicationName;
        applicationVersion = _applicationVersion;
        developers = _developers;
        additionalContentRows = _additionalContentRows;
        appIcon = _appIcon;
        appLogo = _appLogo;
        buildUI();
        setLocationRelativeTo(owner);
    }

    private void buildUI() {
        ResourceBundle RES_BUNDLE = ResourceBundle.getBundle(MessageBundle.BUNDLE_Y3_COMMONS);
        setIconImage(appIcon);
        setTitle(applicationName);
        getContentPane().setBackground(Color.WHITE);
        DesignGridLayout layout = new DesignGridLayout(getContentPane());
        layout.row().center().add(new JLabel(appLogo));
        layout.row().grid(new JLabel(RES_BUNDLE.getString("VERSION"))).add(new JLabel(applicationVersion));
        layout.row().grid(new JLabel(RES_BUNDLE.getString("JAVA_VERSION"))).add(new JLabel(System.getProperty("java.version")));
        layout.row().grid(new JLabel(RES_BUNDLE.getString("JAVA_LOCATION"))).add(new JLabel(System.getProperty("java.home")));
        layout.emptyRow();
        if (additionalContentRows != null && !additionalContentRows.isEmpty()) {
            Set<String> labels = additionalContentRows.keySet();
            for (String label : labels) {
                String text = additionalContentRows.get(label);
                layout.row().grid(new JLabel(label)).add(new JLabel(text));
            }
        }
        
        layout.row().grid(new JLabel(RES_BUNDLE.getString("PROVIDED_BY")))
                .add(new JLabel(RES_BUNDLE.getString("IT_HAPPENS_DE")));
        if (developers != null && developers.length > 0) {
            String developedByLabel = RES_BUNDLE.getString("DEVELOPED_BY");
            for (String developer : developers) {
                layout.row().grid(new JLabel(developedByLabel)).add(new JLabel(developer));
            }
        }
        
        pack();
    }

}
