package hu.mak.devizavaltas;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;

/**
 * Adott honlapon elérhető online struktúrált adatok felhasználásával (xml)
 * készített devizaváltó alkalmazás. Letölthető, kiválasztható az xml a formon.
 * Egy legördülő listából lehet választani valutát. A bekért váltandó összeg,
 * átváltás esemény után forintban jelenik meg.
 */
public class DevizaValtas extends javax.swing.JFrame {

    private static float forintEuroArfolyam;
    //public java.util.List<String> arfolyamok = new ArrayList<>();
    private java.util.List<String> arfolyamok = new ArrayList<>();

    public DevizaValtas() {
        initComponents();
        jFileChooser1.setVisible(false);
    }

    /**
     * A kivalasztott xml file valutatípus(currency) elemeivel feltölti a
     * legördülő listát. A forint euro árfolyama egy változóba kerül.
     *
     * @return devizaarfolyamokat tartalmazo lista
     */
    private java.util.List<String> xmlToList() {
        java.util.List<String> arfolyamLista = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(jFileChooser1.getSelectedFile());
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Cube");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);
                if (elem.getAttribute("currency") != null && !elem.getAttribute("currency").isEmpty()) {
                    jComboBox1.addItem(elem.getAttribute("currency"));
                    arfolyamLista.add(elem.getAttribute("rate"));
                    if ("HUF".equalsIgnoreCase(elem.getAttribute("currency"))) {
                        forintEuroArfolyam = Float.valueOf(elem.getAttribute("rate"));
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return arfolyamLista;
        //return;
    }

    /**
     * Egy kiválasztott könyvtárba, devizaarfolyamok.xml néven letölti a
     * honlapon elérhető xml-t.
     */
    
    private void devizaArfolyamXmlLetolt() {
        URL url;
        ReadableByteChannel readableByteChannel = null;
        FileOutputStream fileOutputStream = null;

        new CertificatValidator();

        try {
            url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
            readableByteChannel = Channels.newChannel(url.openStream());
            fileOutputStream = new FileOutputStream(jFileChooser1.getSelectedFile().getPath() + "\\devizaarfolyamok.xml");
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            JOptionPane.showMessageDialog(rootPane, "Sikeres letöltés.");
        } catch (MalformedURLException ex) {
            Logger.getLogger(DevizaValtas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DevizaValtas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (readableByteChannel != null) {
                    readableByteChannel.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(DevizaValtas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jFileChooser1 = new javax.swing.JFileChooser();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Deviza átváltása Ft-ra");

        jTextField1.setEditable(false);
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabel1.setText("Összege:");

        jLabel2.setText("Valuta típusa");

        jButton1.setText("Átváltás forintra");
        jButton1.setToolTipText("");
        jButton1.setActionCommand("forintravalt");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Ft");
        jLabel3.setToolTipText("");

        jTextField2.setEditable(false);

        jTextField3.setEditable(false);
        jTextField3.setToolTipText("");

        jFileChooser1.setCurrentDirectory(new java.io.File("D:\\"));
            jFileChooser1.setDialogTitle("");
            jFileChooser1.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
            jFileChooser1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jFileChooser1ActionPerformed(evt);
                }
            });

            jLabel4.setText("XML kiválasztása");
            jLabel4.setToolTipText("");

            jButton2.setText("...");
            jButton2.setToolTipText("");
            jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });

            jButton3.setText("XML letöltése");
            jButton3.setToolTipText("");
            jButton3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton3ActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(41, 41, 41)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton2)
                            .addGap(61, 61, 61)
                            .addComponent(jButton3)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel3))
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                            .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(56, 56, 56))))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jButton2)
                        .addComponent(jButton3))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(59, 59, 59)
                            .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(15, 15, 15)
                            .addComponent(jButton1)
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(43, Short.MAX_VALUE))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    /**
     * Átváltás és az eredmény forint érték megjelenítése 5 Ft-ra kerekítve.
     * Beírt összeg osztva a választott valuta árfolyamával szorozva a forint
     * euroárfolyamával. A beírt összeg ellenőrzése, ha nem pozitív egész, akkor
     * hibaüzenet jelenik meg.
     *
     * @param evt Áváltás forintra gomb megnyomása
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (jTextField1.getText() != null && !jTextField1.getText().isEmpty()) {
            try {
                long number = Long.parseLong(jTextField1.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Az összeg csak egész szám lehet");
                jTextField1.setText("");
                return;
            }

            float valutaOsszege;
            float valasztottArfolyam;
            long Forint;

            valutaOsszege = Float.valueOf(jTextField1.getText());
            valasztottArfolyam = Float.valueOf(arfolyamok.get(jComboBox1.getSelectedIndex()));
            Forint = (long)Math.round(((valutaOsszege / valasztottArfolyam) * forintEuroArfolyam) / 5.) * 5;
            jTextField2.setText(String.valueOf(Forint));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        jFileChooser1.setVisible(false);

    }//GEN-LAST:event_jFileChooser1ActionPerformed

    /**
     * Fájlválasztó ablak megjelenítése xml fájlokra szűrve, kiválasztott fájl
     * kiírása a képernyőre útvonallal, valuta típusok lenyíló lista és
     * arfolyamok lista feltöltése xmlToList meghívásával. Összeg mező
     * kitölthetőségének engedélyezése. Nem létező fájl esetén hibaüzenet
     * megjelenítése.
     *
     * @param evt A letöltött Xml kiválasztása gomb megnyomása
     */

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jFileChooser1.setVisible(true);
        jFileChooser1.setFileSelectionMode(jFileChooser1.FILES_ONLY);
        jFileChooser1.setAcceptAllFileFilterUsed(false);
        jFileChooser1.setFileFilter(new FileNameExtensionFilter("XML fájlok(.xml)", "xml"));
        int returnValue = jFileChooser1.showOpenDialog(this);
        if (returnValue == jFileChooser1.APPROVE_OPTION) {
            if (jFileChooser1.getSelectedFile().exists()) {
                jTextField3.setText(jFileChooser1.getSelectedFile().getAbsolutePath());
                arfolyamok = xmlToList();
                jTextField1.setEditable(true);
            } else {
                JOptionPane.showMessageDialog(rootPane, "A fájl nem létezik.");
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * Könyvtár választó ablak megjelenítése. Ha létezik a könyvtár,
     * devizaArfolyamXmlLetolt meghívása.
     *
     * @param evt Xml letöltése gomb megnyomása
     */

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jFileChooser1.setVisible(true);
        jFileChooser1.setFileSelectionMode(jFileChooser1.DIRECTORIES_ONLY);
        jFileChooser1.setFileFilter(null);
        jFileChooser1.setAcceptAllFileFilterUsed(true);
        int returnValue = jFileChooser1.showSaveDialog(this);
        if (returnValue == jFileChooser1.APPROVE_OPTION) {
            if (jFileChooser1.getSelectedFile().exists()) {
                jTextField3.setText(jFileChooser1.getSelectedFile().getPath());
                devizaArfolyamXmlLetolt();
            } else {
                JOptionPane.showMessageDialog(rootPane, "A könyvtár nem létezik.");
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * Az Xml file letöltéséhez szükséges érvényes tanusítvány.
     */
    
    public class CertificatValidator {

        public CertificatValidator() {

            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
            };

            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
            }
        }
    }

    /**
     * Devizaváltás form létrehozása és megjelenítése.
     *
     * @param args
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DevizaValtas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DevizaValtas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DevizaValtas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DevizaValtas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DevizaValtas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
