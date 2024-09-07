import os

def listar_arquivos(caminho_relativo, arquivo_saida, apenasArquivosJava): 
    # Obtém o caminho absoluto da pasta em que o executável está sendo executado
    caminho_pasta_atual = os.getcwd()
    
    # Concatena o caminho atual com o caminho relativo desejado
    caminho_desejado = os.path.join(caminho_pasta_atual, caminho_relativo)
    
    # Verifica se o diretório existe
    if os.path.exists(caminho_desejado):
        # Escreve o cabeçalho da estrutura de arquivos
        arquivo_saida.write("Estrutura de arquivos e pastas:\n\n")
        
        # Percorre todos os diretórios e arquivos na pasta desejada
        for raiz, diretorios, arquivos in os.walk(caminho_desejado):
            nivel = raiz.replace(caminho_desejado, "").count(os.sep)
            indentacao = ' ' * 4 * nivel
            arquivo_saida.write(f"{indentacao}+-- {os.path.basename(raiz)}/\n")
            sub_indentacao = ' ' * 4 * (nivel + 1)
            for nome_arquivo in arquivos:
                caminho_completo_arquivo = os.path.join(raiz, nome_arquivo)
                if 'layout' in raiz or 'menu' in raiz or not apenasArquivosJava or nome_arquivo.endswith(".java"):
                    arquivo_saida.write(f"{sub_indentacao}+-- {nome_arquivo}\n")
    else:
        arquivo_saida.write(f"O diretório {caminho_desejado} não foi encontrado.\n")

def ler_arquivos(caminho_relativo, arquivo_saida, apenasArquivosJava):
    # Obtém o caminho absoluto da pasta em que o executável está sendo executado
    caminho_pasta_atual = os.getcwd()
    
    # Concatena o caminho atual com o caminho relativo desejado
    caminho_desejado = os.path.join(caminho_pasta_atual, caminho_relativo)
    
    # Verifica se o diretório existe
    if os.path.exists(caminho_desejado):
        # Escreve o cabeçalho da leitura dos arquivos
        arquivo_saida.write("\n\nConteúdo dos arquivos (sem imports e comentários):\n\n")
        
        # Percorre todos os diretórios e arquivos na pasta desejada
        for raiz, diretorios, arquivos in os.walk(caminho_desejado):
            for nome_arquivo in arquivos:
                caminho_completo_arquivo = os.path.join(raiz, nome_arquivo)
                if 'layout' in raiz or 'menu' in raiz or not apenasArquivosJava or nome_arquivo.endswith(".java"):
                    # Substitui as barras invertidas por barras normais
                    caminho_completo_arquivo_formatado = caminho_completo_arquivo.replace("\\", "/")
                    arquivo_saida.write(f"\n\n--- Conteúdo do arquivo: {caminho_completo_arquivo_formatado} ---\n")
                    ler_e_filtrar_arquivo(caminho_completo_arquivo, arquivo_saida)
    else:
        arquivo_saida.write(f"O diretório {caminho_desejado} não foi encontrado.\n")

def ler_e_filtrar_arquivo(caminho_arquivo, arquivo_saida):
    """Lê o arquivo, remove linhas de imports e comentários e escreve no arquivo de saída"""
    try:
        with open(caminho_arquivo, 'r', encoding="utf-8") as arquivo:
            for linha in arquivo:
                linha_strip = linha.strip()
                # Filtra linhas que são imports, comentários ou vazias
                if not (linha_strip.startswith("import") or 
                        linha_strip.startswith("//") or 
                        linha_strip.startswith("/*") or 
                        linha_strip.startswith("*") or 
                        linha_strip.startswith("#") or 
                        linha_strip == ""):
                    arquivo_saida.write(linha)
    except Exception as e:
        arquivo_saida.write(f"Erro ao ler o arquivo {caminho_arquivo}: {e}\n")

if __name__ == "__main__":
    caminho_relativo = "app/src/main"
    apenasArquivosJava = True
    
    # Cria/abre o arquivo de saída "resultado.txt" na pasta atual
    with open("resultado.txt", "w", encoding="utf-8") as arquivo_saida:
        listar_arquivos(caminho_relativo, arquivo_saida, apenasArquivosJava)
        ler_arquivos(caminho_relativo, arquivo_saida, apenasArquivosJava)
