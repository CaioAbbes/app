package br.com.domenic.edtech.app.utils.geradores;

import br.com.domenic.edtech.app.models.AulaCurso;

public class GerarAulaCurso {

    public static AulaCurso gerarAulaCurso(int idCurso, int idProfessor) {
        AulaCurso aulaCurso = new AulaCurso();
        aulaCurso.setIdCurso(idCurso);
        aulaCurso.setIdProfessor(idProfessor);
        aulaCurso.setAulaParticular(GeradorDados.getRandom().nextBoolean());
        aulaCurso.setAulaOnline(GeradorDados.getRandom().nextBoolean());
        String especialidade = GeradorDados.gerarEspecialidadeAleatoria();
        aulaCurso.setTitulo("Aula de " + especialidade);
        aulaCurso.setDescricao("Descrição de aula " + GeradorDados.gerarDescricaoAleatoria(especialidade));
        aulaCurso.setDataAula(GeradorDados.gerarDataAleatoria());
        aulaCurso.setLinkAulaAoVivo(GeradorDados.getLinkAula());
        return aulaCurso;
    }
}
