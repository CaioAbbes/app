package br.com.domenic.edtech.app.utils.geradores;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import br.com.domenic.edtech.app.models.AulaCurso;
import br.com.domenic.edtech.app.models.Curso;

public class GeradorDados {

    private static final Random random = new Random();
    private static final Map<String, List<String>> listaCursoDescricao = new HashMap<>();
    private static final Map<String, List<String>> listaAulaDescricao = new HashMap<>();
    private static final List<String> listaLinksAula = Arrays.asList(
            "https://www.youtube.com/watch?v=UDrDg6uUOVs",
            "https://www.youtube.com/watch?v=0TndL-Nh6Ok",
            "https://www.youtube.com/watch?v=gcv5hXyTcIo",
            "https://www.youtube.com/watch?v=z9YKsZt7-ZM",
            "https://www.youtube.com/watch?v=s3PaJTu9rtU",
            "https://www.youtube.com/watch?v=tSGMyE9lirE",
            "https://www.youtube.com/watch?v=06e1kSSyQPg",
            "https://www.youtube.com/watch?v=kYIUzzg61jU",
            "https://www.youtube.com/watch?v=E9AnAWahYKk",
            "https://www.youtube.com/watch?v=kP1kktlbUTs",
            "https://www.youtube.com/watch?v=U1Sweidt0Vs",
            "https://www.youtube.com/watch?v=Xhorz6goaG4",
            "https://www.youtube.com/watch?v=yMzvYDbYWxs",
            "https://www.youtube.com/watch?v=yMzvYDbYWxs",
            "https://www.youtube.com/watch?v=_Dfm-FPfal0"
    );

    private static final List<String> listaEspecialidades = Arrays.asList(
            "Análise de Dados", "Automação Industrial", "Ciência de Dados", "Desenvolvimento de Jogos",
            "Design Gráfico", "Direito Empresarial", "Engenharia Civil", "Engenharia de Software",
            "Engenharia Elétrica", "Engenharia Mecânica", "Finanças e Contabilidade", "Gestão de Projetos",
            "Inglês", "Inteligência Artificial", "Marketing Digital", "Mecatrônica", "Negócios e Empreendedorismo",
            "Programação", "Recursos Humanos", "Redes de Computadores", "Robótica",
            "Segurança da Informação", "Tecnologia da Informação"
    );

    private static final List<String> listaNomes = Arrays.asList(
            "João", "Pedro", "Lucas", "Mateus", "Gabriel", "Rafael", "Gustavo", "Felipe", "Leonardo", "Arthur",
            "Marcos", "Thiago", "José", "Carlos", "Bruno",
            "Maria", "Ana", "Beatriz", "Camila", "Julia", "Sofia", "Isabela", "Larissa", "Fernanda", "Luana",
            "Patrícia", "Aline", "Viviane", "Renata", "Gabriela"
    );

    private static final List<String> listaSobrenomes = Arrays.asList(
            "Silva", "Santos", "Oliveira", "Souza", "Pereira", "Lima", "Carvalho", "Almeida", "Ribeiro", "Ferreira",
            "Costa", "Gomes", "Martins", "Araujo", "Mendes", "Barbosa", "Rocha", "Dias", "Teixeira", "Correia",
            "Freitas", "Vieira", "Castro", "Moreira", "Pinto", "Cardoso", "Moraes", "Barros", "Santana", "Monteiro"
    );

    static {
        // Adiciona nomes de cursos e suas respectivas descrições
        listaCursoDescricao.put("Análise de Dados", Arrays.asList(
                "Curso de introdução à análise de grandes volumes de dados.",
                "Curso avançado sobre técnicas de análise e visualização de dados."
        ));

        listaCursoDescricao.put("Automação Industrial", Arrays.asList(
                "Aprenda a automatizar processos industriais usando PLCs.",
                "Curso completo sobre sistemas de controle e automação."
        ));

        listaCursoDescricao.put("Ciência de Dados", Arrays.asList(
                "Curso de fundamentos em ciência de dados e aprendizado de máquina.",
                "Aprenda a manipular e interpretar grandes volumes de dados."
        ));

        listaCursoDescricao.put("Desenvolvimento de Jogos", Arrays.asList(
                "Curso prático de criação de jogos 2D e 3D.",
                "Introdução ao desenvolvimento de jogos com Unity e Unreal."
        ));

        listaCursoDescricao.put("Design Gráfico", Arrays.asList(
                "Curso de design gráfico com foco em criação de identidade visual.",
                "Aprenda a utilizar softwares de design como Photoshop e Illustrator."
        ));

        listaCursoDescricao.put("Direito Empresarial", Arrays.asList(
                "Introdução ao direito empresarial e contratos corporativos.",
                "Curso sobre legislações empresariais e responsabilidade civil."
        ));

        listaCursoDescricao.put("Engenharia Civil", Arrays.asList(
                "Fundamentos de engenharia civil e construção de edifícios.",
                "Curso sobre gestão de obras e análise estrutural de projetos."
        ));

        listaCursoDescricao.put("Engenharia de Software", Arrays.asList(
                "Curso completo sobre desenvolvimento e arquitetura de software.",
                "Aprenda sobre metodologias ágeis e ciclo de vida de software."
        ));

        listaCursoDescricao.put("Engenharia Elétrica", Arrays.asList(
                "Curso sobre circuitos elétricos e sistemas de distribuição de energia.",
                "Fundamentos de eletricidade aplicada e eletrônica de potência."
        ));

        listaCursoDescricao.put("Engenharia Mecânica", Arrays.asList(
                "Introdução à mecânica aplicada e termodinâmica.",
                "Curso sobre projeto e análise de sistemas mecânicos."
        ));

        listaCursoDescricao.put("Finanças e Contabilidade", Arrays.asList(
                "Fundamentos de contabilidade e análise financeira.",
                "Curso sobre gestão financeira empresarial e contabilidade gerencial."
        ));

        listaCursoDescricao.put("Gestão de Projetos", Arrays.asList(
                "Curso prático de gestão de projetos com foco em metodologias ágeis.",
                "Aprenda a gerenciar projetos complexos e liderar equipes eficazmente."
        ));

        listaCursoDescricao.put("Inglês", Arrays.asList(
                "Curso de inglês básico para iniciantes.",
                "Curso de conversação avançada em inglês para negócios."
        ));

        listaCursoDescricao.put("Inteligência Artificial", Arrays.asList(
                "Introdução aos conceitos e aplicações da inteligência artificial.",
                "Curso avançado sobre redes neurais e aprendizado profundo."
        ));

        listaCursoDescricao.put("Marketing Digital", Arrays.asList(
                "Fundamentos de marketing digital e estratégias de SEO.",
                "Curso sobre campanhas de publicidade online e mídias sociais."
        ));

        listaCursoDescricao.put("Mecatrônica", Arrays.asList(
                "Curso introdutório sobre sistemas mecatrônicos e robótica.",
                "Aprenda a projetar sistemas integrados de mecânica e eletrônica."
        ));

        listaCursoDescricao.put("Negócios e Empreendedorismo", Arrays.asList(
                "Curso sobre criação e gestão de startups.",
                "Fundamentos de empreendedorismo e estratégias de negócios."
        ));

        listaCursoDescricao.put("Programação", Arrays.asList(
                "Curso de programação para iniciantes com Python.",
                "Desenvolvimento de aplicações com Java para programadores experientes."
        ));

        listaCursoDescricao.put("Recursos Humanos", Arrays.asList(
                "Fundamentos de gestão de pessoas e recursos humanos.",
                "Curso sobre recrutamento, seleção e desenvolvimento de talentos."
        ));

        listaCursoDescricao.put("Redes de Computadores", Arrays.asList(
                "Curso sobre administração de redes e protocolos de comunicação.",
                "Aprenda a configurar e gerenciar redes locais e remotas."
        ));

        listaCursoDescricao.put("Robótica", Arrays.asList(
                "Introdução à robótica e automação de sistemas.",
                "Curso avançado de controle de robôs e sensores embarcados."
        ));

        listaCursoDescricao.put("Segurança da Informação", Arrays.asList(
                "Fundamentos de segurança da informação e proteção de dados.",
                "Curso sobre criptografia e segurança em redes e sistemas."
        ));

        listaCursoDescricao.put("Tecnologia da Informação", Arrays.asList(
                "Curso de fundamentos de TI e infraestrutura de sistemas.",
                "Gestão de serviços de TI com foco em práticas ITIL e COBIT."
        ));

        /////////////////////////////////////////////////////////////////////////
        // LISTA DE AULAS
        /////////////////
        listaAulaDescricao.put("Análise de Dados", Arrays.asList(
                "Aula prática de análise de dados usando ferramentas como Excel.",
                "Aula sobre conceitos básicos de visualização de dados.",
                "Introdução à análise de grandes volumes de dados.",
                "Técnicas avançadas de análise de dados com Python."
        ));

        listaAulaDescricao.put("Automação Industrial", Arrays.asList(
                "Aula prática sobre sensores em sistemas de automação.",
                "Aula sobre controle e monitoramento de processos industriais.",
                "Introdução à automação de fábricas inteligentes.",
                "Implementação de sistemas de controle PLC em indústrias."
        ));

        listaAulaDescricao.put("Ciência de Dados", Arrays.asList(
                "Introdução ao aprendizado de máquina.",
                "Aula prática sobre mineração de dados e modelos preditivos.",
                "Fundamentos de ciência de dados aplicados à indústria.",
                "Modelos estatísticos e algoritmos para análise preditiva."
        ));

        listaAulaDescricao.put("Desenvolvimento de Jogos", Arrays.asList(
                "Aula prática de criação de jogos 2D com Unity.",
                "Introdução à programação de jogos com C#.",
                "Desenvolvimento de jogos 3D usando Unreal Engine.",
                "Técnicas de animação para jogos com Blender."
        ));

        listaAulaDescricao.put("Design Gráfico", Arrays.asList(
                "Introdução ao design gráfico com Adobe Photoshop.",
                "Criação de logotipos e identidade visual.",
                "Aula prática de manipulação de imagens e edição digital.",
                "Design gráfico avançado com Illustrator e InDesign."
        ));

        listaAulaDescricao.put("Engenharia Civil", Arrays.asList(
                "Aula sobre análise estrutural de edifícios.",
                "Fundamentos de engenharia de pontes e rodovias.",
                "Introdução à gestão de obras e construção civil.",
                "Aula prática sobre fundações e terraplanagem."
        ));

        listaAulaDescricao.put("Engenharia de Software", Arrays.asList(
                "Aula sobre padrões de projeto em desenvolvimento de software.",
                "Desenvolvimento de software com metodologias ágeis.",
                "Introdução à arquitetura de microserviços.",
                "Testes de software automatizados com JUnit e Selenium."
        ));

        listaAulaDescricao.put("Inteligência Artificial", Arrays.asList(
                "Introdução à inteligência artificial e redes neurais.",
                "Aula prática de aprendizado profundo com TensorFlow.",
                "Fundamentos de redes neurais convolucionais para visão computacional.",
                "Técnicas avançadas de processamento de linguagem natural."
        ));

        listaAulaDescricao.put("Programação", Arrays.asList(
                "Introdução à programação em Python para iniciantes.",
                "Aula sobre algoritmos e estruturas de dados com Java.",
                "Desenvolvimento de aplicações web com JavaScript e Node.js.",
                "Programação orientada a objetos com C#."
        ));

        listaAulaDescricao.put("Robótica", Arrays.asList(
                "Aula prática de construção de robôs autônomos.",
                "Programação de robôs industriais com ROS.",
                "Introdução à robótica aplicada à automação de processos.",
                "Simulação de robôs em ambientes industriais."
        ));

        listaAulaDescricao.put("Segurança da Informação", Arrays.asList(
                "Aula prática de proteção de dados em redes corporativas.",
                "Introdução à criptografia e segurança em TI.",
                "Técnicas de defesa cibernética contra ataques.",
                "Análise de vulnerabilidades em sistemas e redes."
        ));

        listaAulaDescricao.put("Tecnologia da Informação", Arrays.asList(
                "Aula sobre infraestrutura de redes e servidores.",
                "Introdução à administração de sistemas Linux.",
                "Gestão de serviços de TI com ITIL e COBIT.",
                "Fundamentos de virtualização e computação em nuvem."
        ));

        listaAulaDescricao.put("Marketing Digital", Arrays.asList(
                "Aula sobre criação de campanhas de marketing no Google Ads.",
                "Introdução às estratégias de SEO para otimização de sites.",
                "Gestão de mídias sociais para empresas.",
                "Técnicas de email marketing e automação de vendas."
        ));

        listaAulaDescricao.put("Mecatrônica", Arrays.asList(
                "Introdução a sistemas mecatrônicos e robótica.",
                "Aula prática de controle de movimento em sistemas mecatrônicos.",
                "Integração de sistemas de controle com Arduino.",
                "Programação de sistemas embarcados para automação."
        ));
    }

    public static Random getRandom() {
        return random;
    }

    // Método para gerar um curso aleatório
    public static Curso gerarCurso() {
        Curso curso = new Curso();

        String nomeCurso = getNomeCursoComDescricao(curso);
        curso.setNomeCurso(nomeCurso);

        double precoCurso = 49.90 + (random.nextDouble() * (299.00 - 49.90));
        precoCurso = Math.round(precoCurso * 100.0) / 100.0;
        curso.setPrecoCurso(precoCurso);

        double precoCursoAulaParticular = 3.90 + (random.nextDouble() * (29.90 - 3.90));
        precoCursoAulaParticular = Math.round(precoCursoAulaParticular * 100.0) / 100.0;
        curso.setPrecoCursoAulaParticular(precoCursoAulaParticular);

        int duracaoHoras = 8 + random.nextInt(294);
        curso.setDuracaoHoras(duracaoHoras);

        curso.setDataCriacao(gerarDataCriacaoAleatoria());

        int popularidade = 1 + random.nextInt(100);
        curso.setPopularidade(popularidade);

        int recomendacao = 1 + random.nextInt(10);
        curso.setRecomendacao(recomendacao);

        return curso;
    }

    public static Date gerarDataCriacaoAleatoria() {
        Calendar calendar = Calendar.getInstance();
        int anoAleatorio = 2023 + random.nextInt(2); // Gera 2023 ou 2024
        int diaDoAno = random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_YEAR)); // Gera um dia aleatório no ano

        calendar.set(Calendar.YEAR, anoAleatorio);
        calendar.set(Calendar.DAY_OF_YEAR, diaDoAno);

        return calendar.getTime();
    }

    // Método para gerar o nome do curso e atribuir a descrição correspondente
    public static String getNomeCursoComDescricao(Curso curso) {
        // Seleciona uma especialidade aleatoriamente
        String especialidade = listaEspecialidades.get(random.nextInt(listaEspecialidades.size()));

        // Obtém as descrições relacionadas a essa especialidade
        List<String> descricoes = listaCursoDescricao.get(especialidade);

        // Seleciona uma descrição aleatória
        String descricao = descricoes.get(random.nextInt(descricoes.size()));
        curso.setDescricao(descricao);

        // Retorna o nome do curso (mesmo que a especialidade neste caso)
        return especialidade;
    }

    // Método para gerar CPF aleatório válido
    public static String gerarCpfAleatorio() {
        int[] cpf = new int[9];
        for (int i = 0; i < 9; i++) {
            cpf[i] = random.nextInt(10);
        }
        int digito1 = calcularDigitoCpf(cpf, 10);
        int digito2 = calcularDigitoCpf(cpf, 11);

        StringBuilder cpfGerado = new StringBuilder();
        for (int i : cpf) {
            cpfGerado.append(i);
        }
        cpfGerado.append(digito1).append(digito2);

        return cpfGerado.toString();
    }

    // Método para gerar celular aleatório com DDD
    public static String gerarCelularAleatorio() {
        int ddd = 10 + random.nextInt(80); // Gera um DDD aleatório entre 10 e 99
        int celular = 900000000 + random.nextInt(100000000); // Gera um número de celular no formato 9XXXXXXX
        return String.valueOf(ddd) + String.valueOf(celular);
    }

    // Método auxiliar para calcular os dígitos verificadores do CPF
    public static int calcularDigitoCpf(int[] cpf, int pesoInicial) {
        int soma = 0;
        for (int i = 0; i < cpf.length; i++) {
            soma += cpf[i] * (pesoInicial - i);
        }
        int digito = 11 - (soma % 11);
        return (digito > 9) ? 0 : digito;
    }

    // Método para gerar uma aula aleatória
    public static AulaCurso gerarAulaGrupo(int idCurso) {
        AulaCurso aula = new AulaCurso();

        // Define o curso para a aula
        aula.setIdCurso(idCurso);

        // Gera o título e a descrição da aula
        String tituloAula = getTituloAulaComDescricao(aula);
        aula.setTitulo(tituloAula);

        // Define se a aula será online ou presencial
        boolean aulaOnline = random.nextBoolean();
        aula.setAulaOnline(aulaOnline);

        // Se a aula for online, adiciona um link de aula fictício
        if (aulaOnline) {
            aula.setLinkAulaAoVivo(getLinkAula());
        }

        // Gera uma data aleatória para a aula
        aula.setDataAula(gerarDataAulaAleatoria());

        return aula;
    }

    // Método para gerar uma aula aleatória
    public static AulaCurso gerarAulaGrupo2() {
        AulaCurso aula = new AulaCurso();

        aula.setTitulo(getTituloAulaComDescricao(aula));
        aula.setAulaOnline(true);
        aula.setLinkAulaAoVivo(getLinkAula());
        aula.setDataAula(gerarDataAulaAleatoria());

        return aula;
    }

    public static String getTituloAulaComDescricao(AulaCurso aula) {
        // Seleciona uma especialidade aleatoriamente
        String especialidade = listaEspecialidades.get(random.nextInt(listaEspecialidades.size()));

        // Obtém as descrições relacionadas a essa especialidade
        List<String> descricoes = listaAulaDescricao.get(especialidade);

        // Verifica se a lista de descrições está disponível para a especialidade
        if (descricoes != null && !descricoes.isEmpty()) {
            // Seleciona uma descrição aleatória
            String descricao = descricoes.get(random.nextInt(descricoes.size()));
            aula.setDescricao(descricao);

            // Retorna o título da aula (usando a especialidade como base)
            return especialidade;
        } else {
            // Retorna um valor padrão se não houver descrições para a especialidade
            aula.setDescricao("Descrição padrão para " + especialidade);
            return "Aula de " + especialidade;
        }
    }

    // Método para gerar uma data aleatória para a aula
    public static Date gerarDataAulaAleatoria() {
        Calendar calendar = Calendar.getInstance();
        int anoAleatorio = 2023 + random.nextInt(2); // Gera um ano entre 2023 e 2024
        int diaDoAno = random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_YEAR)); // Gera um dia aleatório no ano

        calendar.set(Calendar.YEAR, anoAleatorio);
        calendar.set(Calendar.DAY_OF_YEAR, diaDoAno);

        return calendar.getTime();
    }

    // Método para obter um link de aula aleatório
    public static String getLinkAula() {
        return listaLinksAula.get(random.nextInt(listaLinksAula.size()));
    }

    public static String formatarDataParaMarcacao(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(data);
    }







    private static final List<String> listaEnderecos = Arrays.asList(
            "Rua das Flores, 123", "Avenida Paulista, 1500", "Rua Augusta, 456", "Avenida Brasil, 789",
            "Rua da Consolação, 987", "Avenida Atlântica, 234", "Rua XV de Novembro, 111", "Avenida Rio Branco, 222"
    );

    private static final List<String> listaFotos = Arrays.asList(
            "https://example.com/imagem1.jpg", "https://example.com/imagem2.jpg", "https://example.com/imagem3.jpg",
            "https://example.com/imagem4.jpg", "https://example.com/imagem5.jpg"
    );

    private static final List<String> listaBios = Arrays.asList(
            "Professor com mais de 10 anos de experiência na área.",
            "Especialista em ensino de métodos avançados.",
            "Apaixonado por compartilhar conhecimento.",
            "Dedicado ao desenvolvimento pessoal dos alunos.",
            "Especialista em projetos de inovação."
    );

    private static final List<String> dominiosEmail = Arrays.asList(
            "example.com", "email.com", "edu.com", "professor.com", "school.com"
    );

    // Método para gerar um endereço aleatório
    public static String gerarEnderecoAleatorio() {
        return listaEnderecos.get(random.nextInt(listaEnderecos.size()));
    }

    // Método para gerar uma foto de perfil aleatória
    public static String gerarFotoAleatoria() {
        return listaFotos.get(random.nextInt(listaFotos.size()));
    }

    // Método para gerar uma bio aleatória para o professor
    public static String gerarBioAleatoria() {
        return listaBios.get(random.nextInt(listaBios.size()));
    }

    // Método para gerar um email aleatório
    public static String gerarEmailAleatorio() {
        String nome = gerarNomeAleatorio().toLowerCase().replaceAll(" ", ".");
        String dominio = dominiosEmail.get(random.nextInt(dominiosEmail.size()));
        return nome + "@" + dominio;
    }

    // Método para gerar uma especialidade aleatória
    public static String gerarEspecialidadeAleatoria() {
        return listaEspecialidades.get(random.nextInt(listaEspecialidades.size()));
    }

    // Método para gerar uma data aleatória
    public static Date gerarDataAleatoria() {
        Calendar calendar = Calendar.getInstance();
        int anoAleatorio = 2024; // Gera ano entre 1990 e 2020
        int diaDoAno = random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_YEAR)); // Gera um dia aleatório
        calendar.set(Calendar.YEAR, anoAleatorio);
        calendar.set(Calendar.DAY_OF_YEAR, diaDoAno);
        return calendar.getTime();
    }

    // Método para gerar uma data de conclusão aleatória
    public static Date gerarDataConclusaoAleatoria() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 6); // Conclusão em 6 meses
        return calendar.getTime();
    }

    // Método para gerar nome completo aleatório
    public static String gerarNomeAleatorio() {
        String nome = listaNomes.get(random.nextInt(listaNomes.size()));
        String sobrenome = listaSobrenomes.get(random.nextInt(listaSobrenomes.size()));
        return nome + " " + sobrenome;
    }

    // Método para gerar uma descrição aleatória com base na especialidade
    public static String gerarDescricaoAleatoria(String especialidade) {
        List<String> descricoes = listaAulaDescricao.get(especialidade);

        // Verifica se há descrições disponíveis para a especialidade
        if (descricoes != null && !descricoes.isEmpty()) {
            return descricoes.get(random.nextInt(descricoes.size()));
        }

        // Retorna uma descrição genérica se não houver descrições específicas
        return "Descrição padrão para " + especialidade;
    }
}