package proyectogrupal.Entidades;

public class Inscripcion {
    
    private int idinscripcion;
    private Alumno alumno;
    private Materia materia;
    private double nota;

    public Inscripcion(int idinscripcion, Alumno alumno, Materia materia, double nota) {
        this.idinscripcion = idinscripcion;
        this.alumno = alumno;
        this.materia = materia;
        this.nota = nota;
    }

    public Inscripcion(Alumno alumno, Materia materia, double nota) {
        this.alumno = alumno;
        this.materia = materia;
        this.nota = nota;
    }

    public Inscripcion(double nota) {
        this.nota = nota;
    }
    
    public Inscripcion(){
        
    }

    public int getIdinscripcion() {
        return idinscripcion;
    }

    public void setIdinscripcion(int idinscripcion) {
        this.idinscripcion = idinscripcion;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    
    public int getIdAlumno(){
        return alumno.getIdalumno();
    }
    
    public void setIdAlumno(int idalumno){
       alumno.setIdAlumno(idalumno);
    }
    
    public int getIdMateria(){
        return materia.getIdmateria();
    }
    
    public void setIdMateria(int idmateria){
       materia.setIdmateria(idmateria);
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    
    
    
    
}