import clients.Client;
import models.ResponseCodeAndBody;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class Main
{
    /* _>
            / ------------------------------- *DISCLAIMER* ------------------------------- /
            Se o senhor preferir ver o código com todos os comentários, por favor cheque o código pelo github (preferi enviar o AT só com comentários mais básicos e adicionar tudo depois quando eu tiver terminado todos os AT's) */
    public static void main(String[] args)
    {
        /*/ ------------------------------- SETUP ------------------------------- /*/
        //1) Instanciando o Client para realizar a conexão e realizar as requisições
        Client client = new Client();
        //========================================================

        /*/ // # Rúbrica 3 ------------------------------- CONSUMINDO A API ------------------------------- /*/
        //1)Crie um cliente Java que envie uma requisição POST para o endpoint de criação de um Mensalista, com um JSON representando um novo item do seu sistema (endpoint da Rubrica 4).
        System.out.println("\n" + "--------------------------------------------------Rúbrica 3--------------------------------------");
        try
        {
            System.out.println("1)");

            String mensalista = "{\"nome\": \"João Pedro\"}";
            ResponseCodeAndBody response = client.postNovoMensalista(mensalista);

            if (response.code() != HttpURLConnection.HTTP_CREATED)
            {
                System.out.println("O mensalista não pôde ser cadastrado no sistema, pois: " +
                "\n" + response.body() + "\n" + "Código de status HTTP: " + response.code());
            }
            else
            {
                System.out.println("Mensalista " + response.body() + " cadastrado! " +  "\n" + "Código de status HTTP: " + response.code());
            }
        }
        catch (IOException | URISyntaxException e)
        {
            System.out.println("<<FALHA DE CONEXÃO>> Não foi possível completar a requisição: " + e.getMessage());
        }
        //--------------------------------------------/------------------------------------------

        //2)Crie um cliente Java que realize uma requisição GET para o endpoint de listagem de todos os Mensalistas e imprima os dados retornados no console (endpoint da Rubrica 4).
        try
        {
            System.out.println("2)");
            ResponseCodeAndBody response = client.getListaMensalistas();

            if (response.code() != HttpURLConnection.HTTP_OK)
            {
                System.out.println("Corpo da requisição: " + response.body() + "\n" + "Código de status HTTP: " + response.code());
            }
            else
            {
                System.out.println("Lista de Mensalistas: " + response.body() + "\n" + "Código de status HTTP: " + response.code());
            }
        }
        catch (IOException | URISyntaxException e)
        {
            System.out.println("<<FALHA DE CONEXÃO>> Não foi possível completar a requisição: " + e.getMessage());
        }
        //--------------------------------------------/------------------------------------------

        //3)Crie um cliente Java que envie uma requisição GET com path param, buscando um Mensalista pela matrícula e imprima os dados retornados no console (endpoint da Rubrica 4).
        try {
            System.out.println("3)");

            ResponseCodeAndBody response = client.getMensalistaPorMatricula(0);

            if (response.code() != HttpURLConnection.HTTP_OK)
            {
                System.out.println("Corpo da requisição: " + response.body() + "\n" + "Código de status HTTP: " + response.code());
            }
            else
            {
                System.out.println("Mensalista: " + response.body() + "\n" + "Código de status HTTP: " + response.code());
            }
        }
        catch (IOException | URISyntaxException e)
        {
            System.out.println("<<FALHA DE CONEXÃO>> Não foi possível completar a requisição: " + e.getMessage());
        }
        //--------------------------------------------/------------------------------------------

        //4)Crie um cliente Java que envie uma requisição GET para o endpoint /status e imprima o JSON com o status e timestamp (endpoint da Rubrica 1).
        try
        {
            System.out.println("4)");

            ResponseCodeAndBody response = client.getHoraAtual();

            System.out.println("Hora atual: " + response.body() + "\n" + "Código de status HTTP: " + response.code());
        }
        catch (IOException | URISyntaxException e)
        {
            System.out.println("<<FALHA DE CONEXÃO>> Não foi possível completar a requisição: " + e.getMessage());
        }
    }
}
