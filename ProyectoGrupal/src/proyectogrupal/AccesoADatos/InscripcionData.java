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

    public InscripcionData() {
        con = Conexion.getConexion();
        aluData = new AlumnoData();
        matData = new MateriaData();
    }

    public void GuardarInscripcion(Inscripcion inscripcion) {
        String sql = "INSERT INTO inscripcion (nota, idAlumno, idMateria)"
                + "VALUES(?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, inscripcion.getNota());
            ps.setInt(2, inscripcion.getIdAlumno());
            ps.setInt(3, inscripcion.getIdMateria());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                inscripcion.setIdinscripcion(rs.getInt(1));
                /* Uso el índice 1 para obtener 
                el valor generado Asi estaba antes: inscripcion.setIdinscripcion(rs.getInt("idInscripto")); 
                de esta forma funciona pero larga un error de id, ya que maria debe genera un nobre propio como
                insert_id, .insert_id] y al no encontrarlo se va al cath y da el erro:
                Error al acceder a la tabla inscripcion Unknown Label 'idInscripto'.Possible value [insert_id, .insert_id]*/
                JOptionPane.showMessageDialog(null, "Inscripción exitosa.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla inscripcion " + ex.getMessage());
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla inscripcion " + ex.getMessage());
        }
        return inscripciones;
    }

    public List<Inscripcion> obtenerInscripcionesPorAlumno(int id) {
        
    List<Inscripcion> inscripciones = new ArrayList<>();
    String sql = "SELECT inscripcion.idInscripto, inscripcion.nota, inscripcion.idAlumno, inscripcion.idMateria, materia.nombre " +
                 "FROM inscripcion " +
                 "INNER JOIN materia ON inscripcion.idMateria = materia.idmateria " +
                 "WHERE inscripcion.idAlumno = ?";
    
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        rs = ps.executeQuery();

        while (rs.next()) {
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setIdinscripcion(rs.getInt("idInscripto"));
            inscripcion.setNota(rs.getDouble("nota"));
            inscripcion.setIdAlumno(id);
            inscripcion.setIdMateria(rs.getInt("idMateria"));
            
            // Obtén el nombre de la materia desde la columna "nombre"
            String nombreMateria = rs.getString("nombre");
            
            // Configura el nombre de la materia en la inscripción
            inscripcion.getMateria().setNombre(nombreMateria);
            
            inscripciones.add(inscripcion);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al acceder a la tabla inscripcion: " + ex.getMessage());
        System.out.println(ex);
    } finally {
        // Cierre de PreparedStatement y ResultSet
    }
    
    return inscripciones;
    }

    public List<Materia> obtenerMateriasCursadas(int id) {
        List<Materia> materias = new ArrayList<>();
        try {
            String sql = "SELECT *"
                    + "FROM inscripcion "
                    + "JOIN materia ON(inscripcion.idMateria=materia.idMateria)"
                    + "WHERE inscripcion.idAlumno = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Materia materia;
            while (rs.next()) {
                materia = new Materia();
                materia.setIdmateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAniomateria(rs.getInt("año"));
                materias.add(materia);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener Inscripciones" + ex.getMessage());
        }
        return materias;
    }

    public List<Materia> obtenerMateriasNOCursadas(int id) {

        List<Materia> materias = new ArrayList<>();
        try {
            String sql = "SELECT * "
                    + "FROM materia "
                    /*LEFT JOIN en lugar de JOIN para asegurarte de que obtienes las materias en las que el alumno
                    no está inscrito. Además, es importante incluir un LEFT JOIN con una cláusula ON que incluya 
                    tanto el ID del alumno como el ID de la materia para que puedas compararlos en la condición 
                     */
                    + "LEFT JOIN inscripcion ON materia.idMateria = inscripcion.idMateria AND inscripcion.idAlumno = ? "
                    + "WHERE inscripcion.idAlumno IS NULL "
                    + "AND materia.estado = 1";
            /* Puse esta condicion para que me muestre solo las con estado 1 por si 
                    alguna fue eliminada logicamente*/
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Materia materia;
            while (rs.next()) {
                materia = new Materia();
                materia.setIdmateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAniomateria(rs.getInt("año"));
                materias.add(materia);
            }
            ps.close();
        } catch (SQLException e) {
            // No se me ocurrio que excepción manejar
            e.printStackTrace();
        }
        return materias;

    }

    public void borrarInscripcionMateriaAlumno(int idAlumno, int idMateria) {
        String sql = "DELETE FROM inscripcion WHERE idAlumno = ? AND idMateria = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Inscripcion eliminada exitosamente");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener Inscripciones" + ex.getMessage());
        }
    }

    public void ActualizarNota(int idalumno, int idmateria, double nota) {
        // Corregí la consulta SQL para actualizar la nota en la tabla 'inscripcion'
        String sql = "UPDATE inscripcion SET nota = ? WHERE idAlumno = ? AND idMateria = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, nota);
            ps.setInt(2, idalumno);
            ps.setInt(3, idmateria);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Inscripcion actualizada exitosamente");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener Inscripciones" + ex.getMessage());
        }
    }

    public List<Alumno> obtenerAlumnosXMateria(int id) {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT alumno.*,materia.nombre "
                + "FROM alumno "
                + "INNER JOIN inscripcion on alumno.idAlumno = inscripcion.idAlumno "
                + "INNER JOIN materia on materia.idMateria = inscripcion.idMateria "
                + "WHERE materia.idMateria=?; ";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(rs.getInt("idAlumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNac(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(true);
                alumnos.add(alumno);
            }
                ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla inscripcion" + ex.getMessage());
        }
        return alumnos;
    }

}
