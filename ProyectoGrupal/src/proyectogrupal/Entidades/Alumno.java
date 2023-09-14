package proyectogrupal.Entidades;

import java.time.LocalDate;

public class Alumno {
    private int idalumno,dni;
    private String apellido,nombre;
    private LocalDate fechaNac;
    private boolean estado;
    
    public Alumno(int idalumno, int dni, String apellido, String nombre, LocalDate fechanac, boolean activo){
        this.idalumno = idalumno;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fechaNac = fechanac;
        this.estado = activo;
    }
    
    public Alumno(int dni, String apellido, String nombre, LocalDate fechanac, boolean activo){
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.fechaNac = fechanac;
        this.estado = activo;
    }
    public Alumno(){
        
    }

    public int getIdalumno() {
        return idalumno;
    }

    public void setIdAlumno(int idalumno) {
        this.idalumno = idalumno;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return ""+idalumno+"-"+apellido+"-"+nombre+"-"+fechaNac+"-"+estado;
    }
    
    
}
