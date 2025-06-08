package com.example.tebeoteca.cliente.comic;

public class NuevoComicResponseDTO {
    private int id;
    private String portada;
    private int clienteId;
    private String titulo;
    private String audiencia;
    private String selloEditorial;
    private String fechaLanzamiento;
    private String estado;
    private String autores;
    private String descripcion;
    private String paisOrigen;
    private String idiomaOriginal;
    private String categorias;
    private String precioCompra;
    private String precioAlquiler;

    public NuevoComicResponseDTO(){}
    public NuevoComicResponseDTO(int id, String portada,
                                 int clienteId,
                                 String titulo,
                                 String audiencia,
                                 String selloEditorial,
                                 String fechaLanzamiento,
                                 String estado,
                                 String autores,
                                 String descripcion,
                                 String paisOrigen,
                                 String idiomaOriginal,
                                 String categorias,
                                 String precioCompra,
                                 String precioAlquiler) {
        this.id = id;
        this.portada = portada;
        this.clienteId = clienteId;
        this.titulo = titulo;
        this.audiencia = audiencia;
        this.selloEditorial = selloEditorial;
        this.fechaLanzamiento = fechaLanzamiento;
        this.estado = estado;
        this.autores = autores;
        this.descripcion = descripcion;
        this.paisOrigen = paisOrigen;
        this.idiomaOriginal = idiomaOriginal;
        this.categorias = categorias;
        this.precioCompra = precioCompra;
        this.precioAlquiler = precioAlquiler;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAudiencia() {
        return audiencia;
    }

    public void setAudiencia(String audiencia) {
        this.audiencia = audiencia;
    }

    public String getSelloEditorial() {
        return selloEditorial;
    }

    public void setSelloEditorial(String selloEditorial) {
        this.selloEditorial = selloEditorial;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public String getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(String precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getPrecioAlquiler() {
        return precioAlquiler;
    }

    public void setPrecioAlquiler(String precioAlquiler) {
        this.precioAlquiler = precioAlquiler;
    }
}
