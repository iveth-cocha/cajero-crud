import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


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
    private JButton eliminarUsuarioButton;
    private JButton actualizarInformacionButton;
    private JButton buscarButton;
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
        eliminarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idx = id.getText();
                Eliminar(idx);
            }
        });
        actualizarInformacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idx = id.getText();
                nomx = nombre.getText().trim();
                apex = apellido.getText().trim();
                rolx = rol.getText().trim();
                contrax = contra.getText().trim();
                suelx = salario.getText().trim();
                fingrx = fIngreso.getText().trim();
                Actualizar(idx, nomx, apex, rolx, contrax, suelx, fingrx);
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idx = id.getText();
                BuscarPorId(idx);
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
    public void Eliminar( String idU){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Usuario WHERE idUsuario = ?")) {
            pstmt.setInt(1, Integer.parseInt(idU));
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un registro con ese ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Actualizar(String idU, String nom, String ape, String trol, String cont, String suel, String ingre){
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE Usuario SET nombre = ?, apellido = ?, tipoUsuario = ?, contraseña = ?, salario = ?, fechaContratacion = ? WHERE idUsuario = ?")) {
            pstmt.setString(1, nom);
            pstmt.setString(2, ape);
            pstmt.setString(3, trol);
            pstmt.setString(4, cont);
            pstmt.setDouble(5, Double.parseDouble(suel));
            pstmt.setString(6, ingre);
            pstmt.setInt(7, Integer.parseInt(idU));
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Registro actualizado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un registro con ese ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void BuscarPorId(String idU) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("idUsuario");
        model.addColumn("nombre");
        model.addColumn("Apellido");
        model.addColumn("Rol");
        model.addColumn("Contraseña");
        model.addColumn("Salario");
        model.addColumn("FechaIngreso");
        visor.setModel(model);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Usuario WHERE idUsuario = ?")) {
            pstmt.setInt(1, Integer.parseInt(idU));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String[] informacion = new String[7];
                informacion[0] = rs.getString(1);
                informacion[1] = rs.getString(2);
                informacion[2] = rs.getString(3);
                informacion[3] = rs.getString(4);
                informacion[4] = rs.getString(5);
                informacion[5] = rs.getString(6);
                informacion[6] = rs.getString(7);
                model.addRow(informacion);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un registro con ese ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

