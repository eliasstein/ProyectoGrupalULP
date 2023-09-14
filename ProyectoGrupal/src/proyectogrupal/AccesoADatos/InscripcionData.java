package proyectogrupal.AccesoADatos;

import proyectogrupal.Entidades.Materia;
import proyectogrupal.Entidades.Inscripcion;
import proyectogrupal.Entidades.Alumno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
            ps.setInt(2, inscripcion.getIdAlumno());
            ps.setInt(3, inscripcion.getIdMateria());
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
    
    public List<Inscripcion> obtenerInscripciones() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        try {
            String sql = "SELECT * FROM inscripcion";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdinscripcion(rs.getInt("idInscripto"));
                inscripcion.setNota(rs.getDouble("nota"));
                inscripcion.setIdAlumno(rs.getInt("idAlumno"));
                inscripcion.setIdMateria(rs.getInt("idMateria"));
                inscripciones.add(inscripcion);
            }
            ps.close();
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla inscripcion "+ex.getMessage());
        }
        return inscripciones;
    }
    
    public List<Inscripcion> obtenerInscripcionesPorAlumno(int id){
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql ="SELECT nota, idAlumno, idMateria FROM inscripcion WHERE idAlumno = ?";
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdinscripcion(rs.getInt("idInscripcion"));
                inscripcion.setNota(rs.getDouble("nota"));
                inscripcion.setIdAlumno(id);
                inscripcion.setIdMateria(rs.getInt("idMateria"));
            } 
            else {
                JOptionPane.showMessageDialog(null, "No existe la inscripcion");
                ps.close();
            }
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla inscripcion"+ex.getMessage());
        }
        return inscripciones;
    }    

    public List<Materia> obtenerMateriasCursadas(int id){
        List<Materia> materias = new ArrayList<>();
        try{
            String sql = "SELECT *"
                       + "FROM inscripcion "
                       + "JOIN materia ON(inscripcion.idMateria=materia.idMateria)"
                       + "WHERE inscripcion.idAlumno = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs =ps.executeQuery();
            Materia materia;
            while(rs.next()){
                materia = new Materia();
                materia.setIdmateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAniomateria(rs.getInt("año"));
                materias.add(materia);
            }
        ps.close();
    }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al obtener Inscripciones"+ex.getMessage());
        }
        return materias;
    }
    
    public List<Materia> obtenerMateriasNOCursadas(int id){
        List<Materia> materias = new ArrayList<>();
        try{
            String sql = "SELECT *"
                       + "FROM inscripcion "
                       + "JOIN materia ON(inscripcion.idMateria=materia.idMateria)"
                       + "WHERE inscripcion.idAlumno <> ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs =ps.executeQuery();
            Materia materia;
            while(rs.next()){
                materia = new Materia();
                materia.setIdmateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAniomateria(rs.getInt("año"));
                materias.add(materia);
            }
        ps.close();
    }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al obtener Inscripciones"+ex.getMessage());
        }
        return materias;
    }
    
    public void borrarInscripcionMateriaAlumno(int idAlumno, int idMateria){
        String sql ="DELETE FROM inscripcion WHERE idAlumno = ? AND idMateria = ?;";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            int filas=ps.executeUpdate();
            if(filas>0){
                JOptionPane.showMessageDialog(null, "Inscripcion eliminada exitosamente");
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al obtener Inscripciones"+ex.getMessage());
        }
    }
    
    public void ActualizarNota(int idalumno, int idmateria, double nota){
        String sql="UPDATE FROM inscripcion SET nota = ? "
                + "WHERE idAlumno = ? AND idMateria = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, nota);
            ps.setInt(2, idalumno);
            ps.setInt(3,idmateria);
            int filas=ps.executeUpdate();
            if(filas>0){
                JOptionPane.showMessageDialog(null, "Inscripcion actualizada exitosamente");
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al obtener Inscripciones"+ex.getMessage());
        }
    }
    
    public List<Alumno> obtenerAlumnosXMateria(int id){
        List<Alumno> alumnos = new ArrayList<>();
        String sql ="SELECT alumno.*,materia.nombre" +
                    "FROM alumno" +
                    "INNER JOIN inscripcion on alumno.idAlumno = inscripcion.idAlumno" +
                    "INNER JOIN materia on materia.idMateria = inscripcion.idMateria" +
                    "WHERE materia.idMateria=?;";
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(rs.getInt("idAlumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNac(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(true);
            } 
            else {
                JOptionPane.showMessageDialog(null, "No existe un alumno con esta materia");
                ps.close();
            }
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla inscripcion"+ex.getMessage());
        }
        return alumnos;
    }    
    
    
    
}
