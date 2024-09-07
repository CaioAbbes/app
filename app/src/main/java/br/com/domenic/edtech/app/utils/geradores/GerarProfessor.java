package br.com.domenic.edtech.app.utils.geradores;

import java.util.Date;
import br.com.domenic.edtech.app.models.Professor;

public class GerarProfessor {

    public static Professor gerarProfessor(int contadorProfessor) {
        Professor professor = new Professor();
        professor.setNomeCompleto(GeradorDados.gerarNomeAleatorio());
        professor.setDataNascimento(GeradorDados.gerarDataAleatoria());
        professor.setCelular(GeradorDados.gerarCelularAleatorio());
        professor.setEndereco(GeradorDados.gerarEnderecoAleatorio());
        professor.setCpf(GeradorDados.gerarCpfAleatorio());
        professor.setBio(GeradorDados.gerarBioAleatoria());
        professor.setFotoPerfil(GeradorDados.gerarFotoAleatoria());
        professor.setEmail("professor" + contadorProfessor + "@gmail.com");
        professor.setSenha("1234");
        professor.setDataCadastro(new Date());
        professor.setEspecialidade(GeradorDados.gerarEspecialidadeAleatoria());
        return professor;
    }
}