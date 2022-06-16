package demoGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Clase Game. Simula el juego concido como Dino Chrome.
 * <p>
 * Hereda de la clase JFrame e implementa la interface KeyListener.
 * </p>
 * <p>
 * Contiene todas las variables y métodos del juego.
 * </p>
 * @author: Leonardo D. Vergara Márquez
 * @version: 1.0
 * @see java.awt.event
 * @see javax.swing
 */
public class Game extends javax.swing.JFrame implements KeyListener {
    /**
     * Acción de saltar.
     */
    ActionListener saltar;
    /**
     * Acción de correr mediante el cambio de íconos. Maneja los marcadores de puntaje y récord.
     */
    ActionListener correr;
    /**
     * Acción de desplazamiento de obstáculos.
     */
    ActionListener obstaculizar;
    /**
     * Acción de verificar posible colisión.
     */
    ActionListener lose;
    /**
     * Íconos que simulan movimento.
     */
    Icon icon0, icon1;
    /**
     * Identificador del ícono actual.
     */
    int icono;
    /**
     * Identificador de la etapa actual del juego.
     */
    int etapa;
    /**
     * Puntos obtenidos por el jugador en el intento actual.
     */
    int puntos;
    /**
     * Récord del jugador.
     */
    int record;
    /**
     * Indicador del estado de pausa del juego.
     */
    boolean pause;
    /**
     * Indicador del estado de ejecución del juego.
     */
    boolean fin;
    /**
     * Indicador del estado de vida del jugador.
     */
    boolean vivo;
    
    /**
     * Método Constructor.
     */
    public Game(){
        initComponents();
        panel1.addKeyListener(this);
        panel1.setFocusable(true);
        icon0 = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/trex1.png"));
        icon1 = new javax.swing.ImageIcon(getClass().getResource("/Imagenes/trex2.png"));
        set0();
    }
    /**
     * Ubica los componentes e inicializa las varibales de la pantalla inicial.
     */
    public void set0(){
        record=0;
        fin=false;
        icono=0;
        piso.setVisible(false);
        titleB.setVisible(true); bigDino.setVisible(true);
        menuB.setVisible(false);
        pauseB.setVisible(false);
        scoreB.setVisible(false); scoreL.setVisible(false);
        recordL.setVisible(false); recordB.setVisible(false);
        trex.setVisible(false);
        cactus1.setVisible(false);
        startB.setVisible(true); creditsB.setVisible(true); howtoplayB.setVisible(true); bye.setVisible(true);
        titleB.setVisible(true);
        etapa=0; puntos=0;
        trex.setIcon(icon1);
        pause=false;
        againB.setVisible(false);
    }
    /**
     * Ubica los componentes e inicializa las varibales de la pantalla de juego.
     */
    public void set1(){
        pause=false;
        etapa=1;
        vivo=true;
        piso.setVisible(true);
        titleB.setVisible(false); bigDino.setVisible(false);
        startB.setVisible(false); againB.setVisible(false);
        creditsB.setVisible(false); howtoplayB.setVisible(false);
        menuB.setVisible(true);
        pauseB.setVisible(true);
        scoreL.setVisible(true); scoreB.setVisible(true); scoreB.setText(String.valueOf(puntos));
        recordL.setVisible(true); recordB.setVisible(true);
        trex.setVisible(true);
        trex.setLocation(20, 350);
        cactus1.setVisible(true);
        cactus1.setLocation(570,390);
    }
    /**
     * Ejecuta el salto del dinosaurio.
     * @param t Simula el tiempo de salto
     * @param mayus Si es false, el salto es normal. Si es true, el salto es más bajo
     * @param parte Indica la etapa del salto: 0 para el sacenso ó 1 para el descenso
     */
    public void salto(double t, boolean mayus, int parte){
        int y;
        if(mayus){
            y = (int)(-0.09*t*t);
        }else{
            y = (int)(-0.06*t*t);
        }
        switch(parte){
            case 0:
                trex.setLocation(trex.getX(), trex.getY()+y);
                break;
            case 1:
                trex.setLocation(trex.getX(), trex.getY()-y);
        }
    }
    /**
     * Por medio del ActionListener correr, cambia el ícono del dinosaurio. Realiza el aumento del puntaje y la actualización del récord.
     */
    public void mov(){
        correr = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evento){
                if(etapa!=1 || pause || fin || !vivo){
                    ((Timer)evento.getSource()).stop();
                }
                int coef = Integer.parseInt(scoreB.getText())/100;
                int p=Integer.parseInt(scoreB.getText())+ (1+coef);
                scoreB.setText(String.valueOf( Integer.parseInt(scoreB.getText())+ (1+coef) ));
                if(Integer.parseInt(scoreB.getText()) > record){
                    recordB.setText(scoreB.getText());
                }
                if(icono==0){
                    icono=1;
                    trex.setIcon(icon1);
                }else{
                    icono=0;
                    trex.setIcon(icon0);
                }
            }
        };new Timer(200, correr).start();
    }
    /**
     * Por medio del ActionListener obstaculizar, desplaza los obstáculos hacia la izquierda con una velocidad en aumento.
     */
    public void obst(){
        obstaculizar = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evento){                
                if(etapa!=1 || pause || fin || !vivo){
                    ((Timer)evento.getSource()).stop();
                }else{
                    int puntosh=Integer.parseInt(scoreB.getText());
                    int coef = 0;
                    if(puntosh<100){
                        coef = puntosh/10;
                    }
                    if(puntosh>=100 && puntosh<200){
                        coef=10;
                    }else if(puntosh>=200 && puntosh<500){
                        coef=15;
                    }else if(puntosh>=500 && puntosh<2000){
                        coef=20;
                    }else if(puntosh>2000){
                        coef=25;
                    }
                    if(cactus1.getX()<0){
                        cactus1.setLocation(570, cactus1.getY());
                    }
                    cactus1.setLocation(cactus1.getX()-(5+coef), cactus1.getY());
                }
            }
        }; new Timer(50, obstaculizar).start();
    }
    /**
     * ActionListener lose, simula la colisión del dinosaurio con algún obstáculo.
     */
    public void kill(){
        lose = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evento){
                if(trex.getX()>=cactus1.getX()-50 && trex.getX()<=cactus1.getX()+10 && trex.getY()>300){
                    vivo=false;
                    record=Integer.parseInt(recordB.getText());
                    againB.setVisible(true);
                    trex.setLocation(20, 350);
                }
                if(fin || etapa==0 || pause || !vivo){
                    ((Timer)evento.getSource()).stop();
                }
            }
        };new Timer(5, lose).start();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new javax.swing.JPanel();
        trex = new javax.swing.JLabel();
        piso = new javax.swing.JLabel();
        startB = new javax.swing.JButton();
        howtoplayB = new javax.swing.JButton();
        cactus1 = new javax.swing.JLabel();
        bye = new javax.swing.JButton();
        recordL = new javax.swing.JLabel();
        recordB = new javax.swing.JTextField();
        pauseB = new javax.swing.JButton();
        scoreB = new javax.swing.JTextField();
        scoreL = new javax.swing.JLabel();
        menuB = new javax.swing.JButton();
        titleB = new javax.swing.JLabel();
        againB = new javax.swing.JButton();
        creditsB = new javax.swing.JButton();
        bigDino = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        trex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/trex1.png"))); // NOI18N
        panel1.add(trex, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 80, 100));

        piso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/suelo.png"))); // NOI18N
        panel1.add(piso, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 600, 50));

        startB.setBackground(new java.awt.Color(204, 0, 0));
        startB.setFont(new java.awt.Font("Candara Light", 0, 18)); // NOI18N
        startB.setForeground(new java.awt.Color(255, 255, 255));
        startB.setText("Start");
        startB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startBActionPerformed(evt);
            }
        });
        panel1.add(startB, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, -1, -1));

        howtoplayB.setBackground(new java.awt.Color(204, 0, 0));
        howtoplayB.setFont(new java.awt.Font("Candara Light", 0, 18)); // NOI18N
        howtoplayB.setForeground(new java.awt.Color(255, 255, 255));
        howtoplayB.setText("How to Play");
        howtoplayB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                howtoplayBActionPerformed(evt);
            }
        });
        panel1.add(howtoplayB, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 230, -1, -1));

        cactus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cactus1.png"))); // NOI18N
        panel1.add(cactus1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 390, -1, 50));

        bye.setBackground(new java.awt.Color(204, 0, 0));
        bye.setFont(new java.awt.Font("Candara Light", 1, 18)); // NOI18N
        bye.setForeground(new java.awt.Color(255, 255, 255));
        bye.setText("X");
        bye.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                byeActionPerformed(evt);
            }
        });
        panel1.add(bye, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, -1));

        recordL.setFont(new java.awt.Font("Candara Light", 1, 18)); // NOI18N
        recordL.setText("Record");
        panel1.add(recordL, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, -1, -1));

        recordB.setEditable(false);
        recordB.setBackground(new java.awt.Color(204, 0, 0));
        recordB.setFont(new java.awt.Font("Candara Light", 0, 18)); // NOI18N
        recordB.setForeground(new java.awt.Color(255, 255, 255));
        recordB.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel1.add(recordB, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, 90, -1));

        pauseB.setBackground(new java.awt.Color(204, 0, 0));
        pauseB.setFont(new java.awt.Font("Candara Light", 0, 18)); // NOI18N
        pauseB.setForeground(new java.awt.Color(255, 255, 255));
        pauseB.setText("Pause");
        pauseB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseBActionPerformed(evt);
            }
        });
        panel1.add(pauseB, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        scoreB.setEditable(false);
        scoreB.setBackground(new java.awt.Color(204, 0, 0));
        scoreB.setFont(new java.awt.Font("Candara Light", 0, 18)); // NOI18N
        scoreB.setForeground(new java.awt.Color(255, 255, 255));
        scoreB.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panel1.add(scoreB, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 90, -1));

        scoreL.setFont(new java.awt.Font("Candara Light", 1, 18)); // NOI18N
        scoreL.setForeground(new java.awt.Color(51, 51, 51));
        scoreL.setText("Score");
        panel1.add(scoreL, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        menuB.setBackground(new java.awt.Color(204, 0, 0));
        menuB.setFont(new java.awt.Font("Candara Light", 0, 18)); // NOI18N
        menuB.setForeground(new java.awt.Color(255, 255, 255));
        menuB.setText("Main Menu");
        menuB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBActionPerformed(evt);
            }
        });
        panel1.add(menuB, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        titleB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/titulo1.png"))); // NOI18N
        panel1.add(titleB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 400, 50));

        againB.setBackground(new java.awt.Color(204, 0, 0));
        againB.setFont(new java.awt.Font("Candara Light", 0, 18)); // NOI18N
        againB.setForeground(new java.awt.Color(255, 255, 255));
        againB.setText("Play Again");
        againB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                againBActionPerformed(evt);
            }
        });
        panel1.add(againB, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 180, -1, -1));

        creditsB.setBackground(new java.awt.Color(204, 0, 0));
        creditsB.setFont(new java.awt.Font("Candara Light", 0, 18)); // NOI18N
        creditsB.setForeground(new java.awt.Color(255, 255, 255));
        creditsB.setText("Credits");
        creditsB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creditsBActionPerformed(evt);
            }
        });
        panel1.add(creditsB, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 310, -1, -1));

        bigDino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/trex3.png"))); // NOI18N
        panel1.add(bigDino, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 360, 380));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Finaliza la ejecución del programa.
     * @param evt ActionEvent recibido por el JButton bye.
     */
    private void byeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_byeActionPerformed
        fin=true; dispose();
    }//GEN-LAST:event_byeActionPerformed
    /**
     * Regresa al jugador a la pantalla inicial.
     * @param evt ActionEvent recibido por el JButton menuB.
     */
    private void menuBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBActionPerformed
        set0();
    }//GEN-LAST:event_menuBActionPerformed
    /**
     * Detiene eventualmente el juego.
     * @param evt ActionEvent recibido por el JButton pauseB.
     */
    private void pauseBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseBActionPerformed
        if(vivo){
            pause=!pause;
            if(!pause){
                mov(); obst(); kill();
                panel1.requestFocusInWindow();
            }
        }
    }//GEN-LAST:event_pauseBActionPerformed
    /**
     * Reinicia la etapa de juego.
     * @param evt ActionEvent recibido por el JButton againB.
     */
    private void againBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_againBActionPerformed
        set1(); mov(); obst(); kill();
    }//GEN-LAST:event_againBActionPerformed
    /**
     * Muestra un cuadro de diálogo con información del desarrollador.
     * @param evt ActionEvent recibido por el JButton creditsB.
     */
    private void creditsBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creditsBActionPerformed
        JOptionPane.showMessageDialog(this, "Developed by: Leonardo Vergara Márquez."
                + "\nGmail: vergaradl@uninorte.edu.co.\n\nObject-oriented programming");
    }//GEN-LAST:event_creditsBActionPerformed
    /**
     * Muestra un cuadro de diálogo con las indicaciones de cómo jugar.
     * @param evt ActionEvent recibido por el JButton howtoplayB.
     */
    private void howtoplayBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_howtoplayBActionPerformed
        JOptionPane.showMessageDialog(this, "- a: move left.\n- d: move right.\n- w: jump.\n- r: restart.\n- p: pause."
                + "\n\nFor a short jump, active 'Caps Lock'.");
    }//GEN-LAST:event_howtoplayBActionPerformed
    /**
     * Inicia la etapa de juego.
     * @param evt ActionEvent recibido por el JButton howtoplayB.
     */
    private void startBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startBActionPerformed
        set1(); mov(); obst(); kill();
    }//GEN-LAST:event_startBActionPerformed
    /**
     * Método main generado por NetBeans IDE 8.2.
     * @param args 
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton againB;
    private javax.swing.JLabel bigDino;
    private javax.swing.JButton bye;
    private javax.swing.JLabel cactus1;
    private javax.swing.JButton creditsB;
    private javax.swing.JButton howtoplayB;
    private javax.swing.JButton menuB;
    private javax.swing.JPanel panel1;
    private javax.swing.JButton pauseB;
    private javax.swing.JLabel piso;
    private javax.swing.JTextField recordB;
    private javax.swing.JLabel recordL;
    private javax.swing.JTextField scoreB;
    private javax.swing.JLabel scoreL;
    private javax.swing.JButton startB;
    private javax.swing.JLabel titleB;
    private javax.swing.JLabel trex;
    // End of variables declaration//GEN-END:variables
    /**
     * Lee la tecla digitada por el jugador y ejecuta el movimiento corresponiente.
     * <p>
     * Método que sobreescribe el método keyTyped de la interface KeyListener.
     * </p>
     * @param key Objeto de tipo KeyEvent que contiene el código de la tecla digitada.
     */
    @Override
    public void keyTyped(KeyEvent key) {
        if(etapa==1 && trex.getX()>=10 && trex.getX()<=500 && vivo){
            switch (key.getKeyChar()) {
                case 'W':
                    if(trex.getY()==350 && !pause){
                        saltar = new ActionListener(){
                            double cont=0;
                            @Override
                            public void actionPerformed(ActionEvent evento){
                                cont+=1;
                                if(cont<=14){
                                    salto(15-cont, true, 0);
                                }else if(cont>14 && cont<=28){
                                    salto(cont-14, true, 1);
                                }
                                if(cont>28){
                                    ((Timer)evento.getSource()).stop();
                                }
                            }
                        };new Timer(15, saltar).start();
                    }
                    break;
                case 'w':
                    if(trex.getY()==350 && !pause){
                        saltar = new ActionListener(){
                            double cont=0;
                            @Override
                            public void actionPerformed(ActionEvent evento){
                                cont+=0.5;
                                if(cont<=13.5){
                                    salto(14.5-cont, false, 0);
                                }else if(cont>13.5 && cont<=27.5){
                                    salto(cont-13.5, false, 1);
                                }
                                if(cont>28){
                                    ((Timer)evento.getSource()).stop();
                                }
                            }
                        };  new Timer(15, saltar).start();
                    }
                    break;
                case 'd':
                case 'D':
                    if(trex.getX()+10<=500 && !pause){
                        trex.setLocation(trex.getX()+10, trex.getY());
                    }
                    break;
                case 'a':
                case 'A':
                    if(trex.getX()-10>=10 && !pause){
                        trex.setLocation(trex.getX()-10, trex.getY());
                    }
                    break;
                case 'p':
                case 'P':
                    pause=!pause;
                    if(!pause){
                        mov(); obst(); kill();
                    }
                    break;
                default:
                    break;
            }
        }else if(!vivo){
            switch(key.getKeyChar()){
                case 'r':
                case 'R':
                    set1(); mov(); obst(); kill();
                    break; 
            }
           
        }
    }
    /**
     * No realiza acciones en este programa.
     * <p>
     * Método que sobreescribe el método keyPressed de la interface KeyListener.
     * </p>
     * @param key Objeto de tipo KeyEvent que contiene el código de la tecla presionada.
     */
    @Override
    public void keyPressed(KeyEvent key) {
    }
    /**
     * No realiza acciones en este programa.
     * <p>
     * Método que sobreescribe el método keyReleased de la interface KeyListener.
     * </p>
     * @param key Objeto de tipo KeyEvent que contiene el código de la tecla mantenida.
     */
    @Override
    public void keyReleased(KeyEvent key) {
    }
}