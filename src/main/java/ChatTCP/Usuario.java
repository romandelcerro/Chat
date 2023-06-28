/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatTCP;

import java.time.LocalDateTime;

/**
 *
 * @author Tarde
 */
public class Usuario {
    
    private String ip;
    private int puerto;
    private String nombre;
    private LocalDateTime fechaIni;
    private LocalDateTime fechaFin;

    public Usuario(String ip, int puerto, String nobmre, LocalDateTime fechaIni) {
      this.ip = ip;
      this.puerto = puerto;
      this.nombre = nobmre;
      this.fechaIni = fechaIni;
      this.fechaFin = null;
    }
    
    public Usuario(){
        
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(LocalDateTime fechaIni) {
        this.fechaIni = fechaIni;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "ip= " + ip + ", puerto= " + puerto + ", nombre= " + nombre + ", fechaIni= " + fechaIni + ", fechaFin= " + fechaFin;
    }
    
    
}
