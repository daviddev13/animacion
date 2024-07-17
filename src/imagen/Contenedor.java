
package imagen;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Contenedor extends JComponent 
{
  
     Contenedor(JPanel panel)
     {
       System.out.println("entro a contenedor");
       setBounds(0, 0,panel.getWidth() , panel.getHeight());
       // No necesario llamar paint(), activa automáticamente al repintar
     }
     
     
}
