package br.com.domenic.edtech.app.utils.geradores;

import br.com.domenic.edtech.app.models.Curso;

public class GerarCurso {

    public static Curso gerarCurso(int idProfessor) {
        Curso curso = GeradorDados.gerarCurso();
        curso.setIdProfessor(idProfessor);
        return curso;
    }

    public static Curso gerarCursoAleatorio() {
        return GeradorDados.gerarCurso();
    }
}