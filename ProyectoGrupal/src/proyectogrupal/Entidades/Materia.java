package proyectogrupal.Entidades;

public class Materia {
    
    private int idmateria;
    private String nombre;
    private int aniomateria;
    private boolean estado;

    public Materia(int idmateria, String nombre, int aniomateria, boolean activo) {
        this.idmateria = idmateria;
        this.nombre = nombre;
        this.aniomateria = aniomateria;
        this.estado = activo;
    }

    public Materia(String nombre, int aniomateria, boolean activo) {
        this.nombre = nombre;
        this.aniomateria = aniomateria;
        this.estado = activo;
    }
    
    public Materia(){
        
    }

    public int getIdmateria() {
        return idmateria;
    }

    public void setIdmateria(int idmateria) {
        this.idmateria = idmateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAniomateria() {
        return aniomateria;
    }

    public void setAniomateria(int aniomateria) {
        this.aniomateria = aniomateria;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return this.nombre + " " + this.idmateria + "."+this.aniomateria;
    }

     
}
