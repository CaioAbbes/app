package br.com.domenic.edtech.app.utils.geradores;

import java.util.Date;
import br.com.domenic.edtech.app.models.Aluno;

public class GerarAluno {

    public static Aluno gerarAluno(int contadorAluno) {
        Aluno aluno = new Aluno();
        aluno.setNomeCompleto(GeradorDados.gerarNomeAleatorio());
        aluno.setDataNascimento(GeradorDados.gerarDataAleatoria());
        aluno.setCelular(GeradorDados.gerarCelularAleatorio());
        aluno.setEndereco(GeradorDados.gerarEnderecoAleatorio());
        aluno.setCpf(GeradorDados.gerarCpfAleatorio());
        aluno.setFotoPerfil(GeradorDados.gerarFotoAleatoria());
        aluno.setEmail("aluno" + contadorAluno + "@gmail.com");
        aluno.setSenha("1234");
        aluno.setDataCadastro(new Date());
        return aluno;
    }
}