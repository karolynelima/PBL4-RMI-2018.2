package Rmi;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.nio.file.Path;

/*
 *Interface remota que será implementada via RMI
*/

public interface Servico extends Remote {

    public String getDataHora() throws RemoteException;
    public String inverteString(String string) throws RemoteException;
    
    
    //retorna a lista de diretorios dentro do diretorio indicado por DIR
    public ArrayList<String> lerDiretorio(String dir) throws RemoteException;
    
    public ArrayList<String> lerArquivos(String dir) throws RemoteException;
    
    public boolean criarArquivo(String nomeArquivo) throws RemoteException;
            
    public boolean arquivoExiste(String nomeArquivo) throws RemoteException;
    
    public boolean podeLer(String nomeArquivo) throws RemoteException;
    
    public boolean podeEscrever(String nomeArquivo) throws RemoteException;
    
    public void setarSomenteLeitura(String nomeArquivo, boolean readOnly) throws RemoteException;
    
    public void criarDiretorio(String dir) throws RemoteException, IOException;
    
    public byte[] lerArquivo(String nomeArquivo) throws RemoteException, IOException;
    
    public long escreverArquivo(String nomeArquivo, byte[] conteudo) throws RemoteException, IOException;
    
    public void apagarArquivo(String nomeArquivo) throws RemoteException, IOException;
}
