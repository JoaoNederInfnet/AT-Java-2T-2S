package clients;

/*/ ------------------------------- IMPORTAÇÕES------------------------------- /*/

import models.ResponseCodeAndBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
//========================================================

public class Client
{
    /*/ ------------------------------- SETUP ------------------------------- /*/
    //1) Para definir o pathing nos métodos
    private static String pathMensalistas = "/mensalistas";
    //--------------------------------------------/------------------------------------------
    //2) Para gerar a conexão com a APi
    static String urlAPI = "http://localhost:7777";

    private static HttpURLConnection gerarConn(String resource, String method)
    throws URISyntaxException, IOException
    {
        String sUri = urlAPI + resource;
        URI uri = new URI(sUri);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        return conn;
    }
    //--------------------------------------------/------------------------------------------
    //3) Para configurar o body da requisição
    private  static void configurarBody(HttpURLConnection conn, String body)
    throws IOException
    {
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        byte[] bBody = body.getBytes();
        os.write(bBody);
    }
    //--------------------------------------------/------------------------------------------
    //4) Para tratar a resposta recebida
    private static  String tratarResposta(HttpURLConnection conn, boolean requisicaoFoiBemSucedida)
    throws IOException
    {
        StringBuilder sbResponse = new StringBuilder();

        InputStream is = null;

        if(requisicaoFoiBemSucedida)
        {
            is = conn.getInputStream();
        }
        else
        {
            is = conn.getErrorStream();
        }

        InputStreamReader  isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String responseLine;
        while ((responseLine = br.readLine()) != null)
        {
            sbResponse.append(responseLine);
        }
        conn.disconnect();
        return sbResponse.toString();
    }
    //========================================================

    /*/ ------------------------------- HANDLERS DE REQUISIÇÃO ------------------------------- /*/
    // # Rúbrica 1
    public static ResponseCodeAndBody getHoraAtual() throws URISyntaxException, IOException
    {
        String responseBody = null;

        HttpURLConnection conn = gerarConn("/status", "GET");

        int responseCode = conn.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK)
        {
            responseBody = tratarResposta(conn, false);
        }
        //Para 200
        else
        {
            responseBody = tratarResposta(conn, true);
        }
        conn.disconnect();

        return new ResponseCodeAndBody(responseCode, responseBody);
    }
    //-----------------------------#---------------------------

    // # Rúbrica 4
    //#) GETS
    //1) GET Lista com todos os mensalistas
    public static ResponseCodeAndBody getListaMensalistas() throws URISyntaxException, IOException
    {
        String responseBody = null;

        HttpURLConnection conn = gerarConn(pathMensalistas, "GET");

        int responseCode = conn.getResponseCode();

        //Para 204
        if (responseCode == HttpURLConnection.HTTP_NO_CONTENT)
        {
            responseBody = "Não há nenhum mensalista cadastrado no sitema!";
        }
        //Para 200
        else if(responseCode == HttpURLConnection.HTTP_OK)
        {
            responseBody = tratarResposta(conn, true);
        }
        else
        {
            responseBody = tratarResposta(conn, false);
        }
        conn.disconnect();

        return new ResponseCodeAndBody(responseCode, responseBody);
    }
    //--------------------------------------------/------------------------------------------
    //2) GET Mensalista usando a matrícula
    public static ResponseCodeAndBody getMensalistaPorMatricula(int matricula) throws URISyntaxException, IOException
    {
        String responseBody = null;

        HttpURLConnection conn = gerarConn(pathMensalistas+ "/" + matricula, "GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK)
        {
            responseBody = tratarResposta(conn, false);
        }
        else
        {
            responseBody = tratarResposta(conn, true);
        }
        conn.disconnect();

        return new ResponseCodeAndBody (responseCode, responseBody);
    }
    //-----------------------------#---------------------------
    //#) POSTS
    //1) POST novo mensalista
    public ResponseCodeAndBody postNovoMensalista(String nome)
    throws URISyntaxException, IOException
    {
        String responseBody = null;
        HttpURLConnection conn = gerarConn(pathMensalistas, "POST");

        conn.setRequestProperty("Content-Type", "application/json");

        configurarBody(conn, nome);

        int responseCode = conn.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_CREATED)
        {
            responseBody = tratarResposta(conn, false);
        }
        else
        {
            responseBody = tratarResposta(conn, true);
        }
        conn.disconnect();

        return new ResponseCodeAndBody(responseCode, responseBody);
    }
}
