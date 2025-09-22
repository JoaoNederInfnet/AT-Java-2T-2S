package controllers;

/*/ ------------------------------- IMPORTAÇÕES------------------------------- /*/
import com.fasterxml.jackson.databind.JsonNode;
import models.Mensalista;
import io.javalin.http.Context;
import java.io.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
//========================================================


public class Controller
{
    //http://localhost:7777
    /*/ ------------------------------- SETUP ------------------------------- /*/
    //1) Para simular  o banco de dados
    private static Map<Integer, Mensalista> mensalistas = new HashMap<>();
    //--------------------------------------------/------------------------------------------
    //2) Para fazer a incrementação do id
    private static int lastMatricula = 0;
    //========================================================

    /*/ ------------------------------- HANDLERS DE REQUISIÇÃO ------------------------------- /*/
    // # Rúbrica 1
    //#) GETS
    public static void getHoraAtual(Context context)
    {
        Map<String, Object> resposta = new HashMap<>();

        resposta.put("status" , "ok");

        resposta.put("timestamp" , Instant.now().toString());

        context.status(200);

        context.json(resposta);
    }
    //--------------------------------------------/------------------------------------------
    public static void  getSaudacaoNome(Context context)
    {
        Map<String, Object> resposta = new HashMap();

        String nome = context.pathParam("nome");

        resposta.put("mensagem" , "Olá, " + nome + "!");

        if (nome == null)
        {
            context.status(404);

            context.result("Não sei o seu nome , então não consigo formular a frase!");
        }
        context.status(200);

        context.json(resposta);
    }
    //-----------------------------#---------------------------
    //#) POSTS
    public static void postMensagem(Context context)
    {
        Map<String, Object> resposta = context.bodyAsClass(Map.class);

        if (resposta == null)
        {
            context.status(404);

            context.result("Não sei a mensagem que você deseja imprimir!");
        }
        context.status(200);

        context.json(resposta);
    }
    //--------------------------------------------#--------------------------------------------

    // # Rúbrica 4
    //#) GETS
    //1) GET Lista com todos os mensalistas
    public static void getListaMensalistas(Context context)
    {
        //•ETAPAS•//
        //Validando
        if(mensalistas.isEmpty())
        {
            context.status(204);
        }
        else
        {
            context.status(200);

            context.json(mensalistas.values());
        }
    }
    //--------------------------------------------/------------------------------------------
    //2) GET Mensalista usando a matrícula
    public static void  getMensalistaPorMatricula(Context context)
    {
        //•ETAPAS•//
        String param = context.pathParam("matricula");

        int matricula = 0;

        try
        {
            matricula = Integer.parseInt(param);
        }
        catch (NumberFormatException e)
        {
            context.status(400);

            context.result("A matrícula precisa ser um número inteiro válido!");

            return;
        }

        Mensalista mensalista = new Mensalista();

        mensalista =   mensalistas.get(matricula);

        if(mensalista == null)
        {
            context.status(404);

            context.result("Mensalista de matrícula \"" + matricula + "\" não existe!");
            return;
        }

        context.status(200);

        context.json(mensalista);
    }
    //-----------------------------#---------------------------
    //#) POSTS
    //1) POST novo mensalista
    public static void postNovoMensalista(Context context)
    {
        //•ETAPAS•//
        Mensalista mensalista = new Mensalista();

        try
        {
            mensalista = context.bodyAsClass(Mensalista.class);
        }
        catch (Exception e)
        {
            context.status(400);
            context.result("Você enviou as informações do mensalista em um formato inválido!");
            return;
        }

        if (mensalista == null || mensalista.getNome() == null || mensalista.getNome().isBlank())
        {
            context.status(400);
            context.result("Não sei o nome do mensalista que você deseja cadastrar!");
        }
        else
        {
            mensalista.setMatricula(lastMatricula);

            lastMatricula++;

            mensalistas.put(mensalista.getMatricula(), mensalista);

            context.status(201);

            context.json(mensalista);
        }
    }
}
