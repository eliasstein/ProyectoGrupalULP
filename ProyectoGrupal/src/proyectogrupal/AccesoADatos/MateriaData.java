package proyectogrupal.AccesoADatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import proyectogrupal.Entidades.Materia;

public class MateriaData {
    private Connection con = null;
    
    public MateriaData(){
        con = Conexion.getConexion();
    }
    
    public void GuardarAlumno(Materia materia){
        String sql = "INSERT INTO materia (idMateria, nombre, año, estado)"+
                     "VALUES(?,?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, materia.getIdmateria());
            ps.setString(2, materia.getNombre());
            ps.setInt(3, materia.getAniomateria());
            ps.setBoolean(4, materia.isEstado());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                materia.setIdmateria(rs.getInt("idMateria"));
            JOptionPane.showMessageDialog(null, "Alumno añadido con exito.");
            }
            ps.close();
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla alumno "+ ex.getMessage());
        }
    }
    
    public Materia buscarMateria(int id){
        Materia materia = null;
        String sql ="SELECT nombre, año, estado FROM materia WHERE idMateria = ? AND estado = 1";
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                materia = new Materia();
                materia.setIdmateria(id);
                materia.setNombre(rs.getString("nombre"));
                materia.setAniomateria(rs.getInt("año"));
                materia.setEstado(rs.getBoolean("estado"));
            } 
            else {
                JOptionPane.showMessageDialog(null, "No existe el alumno");
                ps.close();
            }
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Materia"+ex.getMessage());
        }
        return materia;
    }
    
    
    public void modificarMateria(Materia materia){
        String sql = "UPDATE materia SET nombre = ? , año = ?, estado = ? WHERE idMateria = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAniomateria());
            ps.setBoolean(3, materia.isEstado());
            ps.setInt(4, materia.getIdmateria());
            int exito = ps.executeUpdate();
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Modificado Exitosamente.");
            } 
            else {
                JOptionPane.showMessageDialog(null, "La materia no existe");
            }
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Materia "+ex.getMessage());
        }
    }
    
    public void eliminarMateria(int id) {
        try {
            String sql = "UPDATE materia SET estado = 0 WHERE idMateria = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int fila=ps.executeUpdate();

            if(fila==1){
                JOptionPane.showMessageDialog(null, "Se eliminó el alumno.");
            }
            ps.close();
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al acceder a la tabla Alumno");
        }
    }
    
    public List<Materia> listarAlumnos() {
        List<Materia> materias = new ArrayList<>();
        try {
            String sql = "SELECT * FROM materia WHERE estado = 1 ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Materia materia = new Materia();
                materia.setIdmateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAniomateria(rs.getInt("año"));
                materia.setEstado(rs.getBoolean("estado"));
                materias.add(materia);
            }
            ps.close();
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " Error al acceder a la tabla materia "+ex.getMessage());
        }
        return materias;
    }    

    
}
