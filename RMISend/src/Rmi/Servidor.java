/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rmi;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antonio
 */
public class Servidor extends UnicastRemoteObject implements Servico {
    
    //construtor
    public Servidor() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        Servidor servidor = new Servidor();
        String localizacao = "//localhost/servico";
        Naming.rebind(localizacao, servidor);
    }

    @Override
    public String getDataHora() throws RemoteException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(Calendar.getInstance().getTime());
    }

    @Override
    public String inverteString(String string) throws RemoteException {
        String retorno = "";
        StringBuffer strb = new StringBuffer(string);
        retorno = strb.reverse().toString();
        return retorno;
    }

    @Override
    public ArrayList<String> lerDiretorio(String dir) throws RemoteException {
        ArrayList<String> result = new ArrayList<>();
        File diretorio = new File(dir);
        File[] listagem = diretorio.listFiles();
        for(int i=0; i< listagem.length; i++){//melhor fzr um for each
            if (listagem[i].isDirectory()){ //seleciona somente os diretorios
                result.add(listagem[i].getName());
            }
        }        
        return result;
    }

    @Override
    public ArrayList<String> lerArquivos(String dir) throws RemoteException {        
        ArrayList<String> result = new ArrayList<>();
        File diretorio = new File(dir);
        File[] listagem = diretorio.listFiles();
        for(int i=0; i< listagem.length; i++){//melhor fzr um for each
            if (listagem[i].isFile()){ //seleciona somente os arquivos
                result.add(listagem[i].getName());
            }
        }        
        return result;    
    
    }

    @Override
    public boolean arquivoExiste(String nomeArquivo) throws RemoteException {
        return new File(nomeArquivo).exists();
    }

    @Override
    public boolean podeLer(String nomeArquivo) throws RemoteException {
        return new File(nomeArquivo).canRead();
    }

    @Override
    public boolean podeEscrever(String nomeArquivo) throws RemoteException {
        return new File(nomeArquivo).canWrite();
    }
    
    //readonly = true --> bloqueia o arquivo para escrita (deixa somente leitura)
    @Override
    public void setarSomenteLeitura(String nomeArquivo, boolean readOnly) throws RemoteException {
        new File(nomeArquivo).setWritable(!readOnly); //como seta writable, tem que usar not readOnly
    }

    @Override
    public void criarDiretorio(String dir) throws RemoteException, IOException {
        Files.createDirectory(Paths.get(dir));
    }

    @Override
    public byte[] lerArquivo(String nomeArquivo) throws RemoteException, IOException {
        Path f = Paths.get(nomeArquivo);
        byte[] dados = Files.readAllBytes(f);
        return dados;
    }

    @Override
    public long escreverArquivo(String nomeArquivo, byte[] conteudo) throws RemoteException, IOException {
        Path f = Paths.get(nomeArquivo);
        byte[] dados = Files.readAllBytes(f);
        return Files.copy(new ByteArrayInputStream(conteudo), f , LinkOption.NOFOLLOW_LINKS);
    }

    @Override
    public void apagarArquivo(String nomeArquivo) throws RemoteException, IOException {
        Files.delete(Paths.get(nomeArquivo));
    }

    @Override
    public boolean criarArquivo(String nomeArquivo) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static String SERVER = "localhost";
    private static Integer PORTA = 1099;
    private static String SERVICO = "ServicoAcessoArquivo";
    
    public static String getURI(){
        String uri = String.format("rmi://%s:%d/$s", SERVER, PORTA, SERVICO);   
        return uri; // rmi://<host_name>[port_number]/<service_name>
    }
            
}
