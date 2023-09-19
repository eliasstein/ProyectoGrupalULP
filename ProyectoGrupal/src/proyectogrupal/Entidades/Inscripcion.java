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
    
    // Podes elegir un valor predeterminado si el alumno es nulo sino daba error
    public int getIdAlumno(){
        if (alumno != null) {
            return alumno.getIdalumno();
        }
        return 0; 
    }
    // Crea un nuevo objeto Alumno si es nulo sino da error NullPointerException
    public void setIdAlumno(int idalumno){
        if (alumno == null) {
            alumno = new Alumno(); 
        }
        alumno.setIdAlumno(idalumno);
    }
    
    // Podes elegir un valor predeterminado si la materia es nula
    public int getIdMateria(){
        if (materia != null) {
            return materia.getIdmateria();
        }
        return 0; 
    }
    //Crea un nuevo objeto Materia si es nula
    public void setIdMateria(int idmateria){
        if (materia == null) {
            materia = new Materia(); 
        }
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
