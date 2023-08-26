import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JScrollPane;


public class Muestra {
    private JButton ingresarInformacionButton;
    private JTextField nombre;
    private JPanel rootPanel;
    private JTextField apellido;
    private JTextField rol;
    private JTextField salario;
    private JTextField id;
    private JTextField contra;
    private JTextField fIngreso;
    private JButton listadoDeCajerosButton;
    private JTable visor;
    static final String DB_URL="jdbc:mysql://localhost/Principal";
    static final String USER="root";
    static final String PASS="poo123";
    static final String QUERY="Select * From Usuario WHERE tipoUsuario = 'cajero'";

    String nomx;
    String apex;
    String rolx;
    String suelx;
    String idx ;
    String contrax;
    String fingrx;


    public Muestra() {
        listadoDeCajerosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mostrar();

            }
        });
        ingresarInformacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               /* idx= String.valueOf(Integer.parseInt(id.getText()));
                nomx=nombre.getText().trim();
                apex=apellido.getText().trim();
                rolx=rol.getText().trim();
                contrax=contra.getText().trim();
                suelx= String.valueOf(Float.parseFloat(salario.getText())).trim();
                fingrx=fIngreso.getText().trim();
                Ingresar(idx, nomx, apex, rolx, contrax, suelx,fingrx );*/

                idx = id.getText();
                nomx = nombre.getText().trim();
                apex = apellido.getText().trim();
                rolx = rol.getText().trim();
                contrax = contra.getText().trim();
                suelx = salario.getText().trim();
                fingrx = fIngreso.getText().trim();
                Ingresar(idx, nomx, apex, rolx, contrax, suelx, fingrx);


            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Registro de Personas");
        frame.setContentPane(new Muestra().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 150);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
    public void Mostrar(){
        //genera columnas de la tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("idUsuario");
        model.addColumn("nombre");
        model.addColumn("Apellido");
        model.addColumn("Rol");//columna tipoUsuario
        model.addColumn("Contraseña");
        model.addColumn("Salario");
        model.addColumn("FechaIngreso");

        // Poner el modelo hecho en el Jtable
        visor.setModel(model);

        //arreglo que almnacena datos
        String [] informacion=new String[7];//especifico el numero de columnas

        try{
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt= conn.createStatement();
            ResultSet rs= stmt.executeQuery(QUERY);

            while (rs.next()){
                informacion[0]=rs.getString(1);//num de columna
                informacion[1]=rs.getString(2);
                informacion[2]=rs.getString(3);
                informacion[3]=rs.getString(4);
                informacion[4]=rs.getString(5);
                informacion[5]=rs.getString(6);
                informacion[6]=rs.getString(7);

                //una fila por cada ingreso
                model.addRow(informacion);
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            JOptionPane.showMessageDialog(null,"Error"+e.toString());
        }
    }

    public void Ingresar(String idU, String nom, String ape, String trol, String cont, String suel, String ingre){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Usuario (idUsuario, nombre, apellido, tipoUsuario, contraseña, salario, fechaContratacion) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setInt(1, Integer.parseInt(idU));
            pstmt.setString(2, nom);
            pstmt.setString(3, ape);
            pstmt.setString(4, trol);
            pstmt.setString(5, cont);
            pstmt.setDouble(6, Double.parseDouble(suel));
            pstmt.setString(7, ingre);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Informacion ingresada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
