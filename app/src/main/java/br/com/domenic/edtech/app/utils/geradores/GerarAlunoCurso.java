package br.com.domenic.edtech.app.utils.geradores;

import java.util.Date;

import br.com.domenic.edtech.app.models.AlunoCurso;

public class GerarAlunoCurso {

    public static AlunoCurso gerarAlunoCurso(int idAluno, int idCurso) {
        AlunoCurso alunoCurso = new AlunoCurso();
        alunoCurso.setIdAluno(idAluno);
        alunoCurso.setIdCurso(idCurso);
        alunoCurso.setDataMatricula(new Date());
        alunoCurso.setDataConclusao(GeradorDados.gerarDataConclusaoAleatoria());
        alunoCurso.setStatus("Iniciado");
        alunoCurso.setQtdCursoAulaParticular(0);
        alunoCurso.setNotaFinal(0.0);
        alunoCurso.setComentarios("");
        return alunoCurso;
    }
}