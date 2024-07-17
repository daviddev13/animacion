package imagen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

//para leer imagen
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

//para dibujar icono encima
import javax.swing.ImageIcon;
import java.net.URL;

public class ventana extends javax.swing.JFrame 
{
   public ventana() {
        initComponents();
        // Añadir botón
        panel.add(boton);
        // Agregar esta línea para repintar el panel
        panel.repaint();
   }
    
    static int columna = 3;
    static int fila = 700;
    static int numero = 1;
    
    //Variable para imagen de fondo
    BufferedImage bi;
    
    private void initComponents() 
    {
        //se inicia boton y fondo 
        boton = new javax.swing.JButton();
        // se asigna un panel no generico
        panel = new FondoPanel();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        // Tratar de agarrar la imagen de fondo      
        try 
        { 
          bi = ImageIO.read(new File("/home/david/Desktop/fondo.png"));
            System.out.println("consiguió imagen");
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Ventana fija
        setSize(1000, 1000); 
        setResizable(false);
        setVisible(true);
        
        // Añadir el panel
        getContentPane().add(panel);
        
        //caracteristicas del boton
        boton.setText("Play");
        boton.addActionListener((java.awt.event.ActionEvent evt) -> {
            botonActionPerformed(evt);
        });
    }
    
    //clase con subclase que sobreescribe paintcomponetn no jframe
    public class FondoPanel extends javax.swing.JPanel 
    {
        @Override
        protected void paintComponent(Graphics g) 
        {
            //método paint de la superclase para asegurar un repintado correcto
            super.paintComponent(g);
            // Dibuja fondo inicia en 0,0 y se adapta al contenedor
            g.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
            
            // Icono encima del fondo
            URL imageURL = getClass().getResource("/imagenes/" + numero + ".png");
            if (imageURL != null) 
            {
                ImageIcon imagen = new ImageIcon(imageURL);
                g.drawImage(imagen.getImage(), columna, fila, 200, 200, null); 
            } 
            else 
            {
                System.out.println("No se encontró la imagen: " + numero + ".png");
            }
        }
    }
    
    //hilo que se ejecuta al tiempo 
    public class MiHilo extends Thread 
    {
        @Override 
        public void run() {
            System.out.println("Ejecutando en el hilo");
            try {
                while(true) {
                    numero++;
                    if(numero == 5) {
                        numero = 1;
                    }
                    panel.repaint();  
                    columna += 10;
                    MiHilo.sleep(100);
                }
            } catch (java.lang.InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    //funcion que se ejecuta con el boton
    public void mover() 
    {
        System.out.println("boton llevo a mover");
        if(!hilo.isAlive()) {
            hilo.start();
        }
        columna = 3;
    }
    
    
    private void botonActionPerformed(java.awt.event.ActionEvent evt) {
        mover();
    }
    
    MiHilo hilo = new MiHilo();
    private javax.swing.JButton boton;
    private javax.swing.JPanel panel;
}