package extractFilesName;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ExtractFileNames {
	
    public static void main(String[] args) {
        String directoryPath = "D:\\\\GitMateriais\\\\FIA_Materiais_por_Assunto"; // Substitua pelo caminho do diretório que você deseja extrair nomes de arquivos

        try {
            FileWriter writer = new FileWriter("D:\\SaidaExtractFiles\\file_names.txt"); // Cria um objeto FileWriter para escrever em um arquivo chamado file_names.txt
            writer.write("| Path + Arquivo  |\n");
            writer.write("| ------------- |\n");
            listFilesAndSubDirectories(new File(directoryPath), writer); // Chama a função recursiva para listar arquivos e subdiretórios

            writer.close(); // Fecha o objeto FileWriter quando terminar de escrever
            moveDuplicateFiles(directoryPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void listFilesAndSubDirectories(File directory, FileWriter writer) throws IOException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile()) { // Verifica se o arquivo é um arquivo, não uma pasta
            	writer.write("| "+file.getAbsolutePath() + " |\n"); // Escreve o caminho completo do arquivo no arquivo txt, seguido de uma nova linha
            } else if (file.isDirectory()) { // Verifica se o arquivo é uma pasta
                listFilesAndSubDirectories(file, writer); // Chama esta função recursivamente para listar arquivos e subdiretórios dentro da pasta
            }
        }
    }
    
    private static void moveDuplicateFiles(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        Path duplicatesPath = Paths.get("D:\\SaidaExtractFiles\\duplicates", "duplicates");

        if (!Files.exists(duplicatesPath)) {
            Files.createDirectory(duplicatesPath);
        }

        for (int i = 0; i < files.length; i++) {
            for (int j = i + 1; j < files.length; j++) {
                if (files[i].getName().equals(files[j].getName()) && files[i].getAbsolutePath().equals(files[j].getAbsolutePath())) {
                    Path sourcePath = files[i].toPath();
                    Path targetPath = Paths.get(duplicatesPath.toString(), files[i].getName());

                    if (Files.isDirectory(sourcePath)) {
                        targetPath = Paths.get(duplicatesPath.toString(), "directory_" + files[i].getName());
                    }

                    Files.move(sourcePath, targetPath);
                }
            }
        }
    }
}
