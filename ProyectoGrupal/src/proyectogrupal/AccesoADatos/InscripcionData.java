package proyectogrupal.AccesoADatos;

import proyectogrupal.Entidades.Inscripcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class InscripcionData {
    private Connection con;
    private AlumnoData aluData;
    private MateriaData matData;
    
    public InscripcionData(){
        con = Conexion.getConexion();
        aluData = new AlumnoData();
        matData = new MateriaData();
    }
    
    public void GuardarInscripcion(Inscripcion inscripcion){
        String sql = "INSERT INTO inscripcion (nota, idAlumno, idMateria)"+
                     "VALUES(?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, inscripcion.getNota());
            ps.setInt(2, inscripcion.getAlumno().getIdalumno());
            ps.setInt(3, inscripcion.getMateria().getIdmateria());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                inscripcion.setIdinscripcion(rs.getInt("idInscripto"));
                JOptionPane.showMessageDialog(null, "Inscripción exitosa.");
            }
            ps.close();
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla inscripcion "+ ex.getMessage());
        }
    }
    
}
