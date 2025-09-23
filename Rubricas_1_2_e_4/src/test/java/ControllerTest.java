/*/ ------------------------------- IMPORTAÇÕES------------------------------- /*/

import controllers.Controller;
import io.javalin.http.Context;
import models.Mensalista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
//========================================================

public class ControllerTest
{
    /*/ ------------------------------- SETUP ------------------------------- /*/
    //1) Mockando a classe context para usar nost testes dos endpoints
    private final Context context = mock(Context.class);
    //--------------------------------------------/------------------------------------------
    //2) Definindo um before each
     @BeforeEach
     public void beforeeach()
     {
         //1) Limpando o repositório de mensalistas
         Controller.mensalistas.clear();
     }
    //========================================================

    /*/ ------------------------------- TESTES ------------------------------- /*/
    // #Testando o endpoint
    //1) getHello
    @Test
    public void getHello_deveRetornarCode200EBodyHelloJavalin()
    {
        //•ETAPAS•//
        // •ACTIONS•
        //1) Chamando o handler do endpoint com o mock simulando a classe context
        Controller.getHello(context);
        //•••••••••••••••••••••••••••••••••••••••

        // •ASSERTIONS•
        //1) Verificando o status de retorno
        verify(context).status(200);
        //--------------------------------------------/------------------------------------------
        //2) Verificando o corpo de retorno
        verify(context).result("Hello, Javalin!");
    }
    //-------------------------------------------------------------------------------------------------------------

    //2) postNovoMensalista
    //A) Caso a requisição tenha sucesso
    @Test
    public void postNovoMensalista_deveRetornarCode201EBodyMensalista_quandoMensalistaValido()
    {
        //•ETAPAS•//
        // •ARRANGEMENTS•
        //1) Instanciando um mensalista válido
        Mensalista mensalista = new Mensalista("João");
        //--------------------------------------------/------------------------------------------
        //2) Configurando o mock para retornar um objeto mensalista quando o corpo da requisição for lido
        when(context.bodyAsClass(Mensalista.class)).thenReturn(mensalista);
        //•••••••••••••••••••••••••••••••••••••••

        // •ACTIONS•
        //1) Chamando o handler do endpoint com o mock simulando a classe context
        Controller.postNovoMensalista(context);
        //•••••••••••••••••••••••••••••••••••••••

        // •ASSERTIONS•
        //1) Verificando o status de retorno
        verify(context).status(201);
        //--------------------------------------------/------------------------------------------
        //2) Verificando o corpo de retorno
        verify(context).json(mensalista);
    }
    //--------------------------------------------/------------------------------------------
    //B) Caso a requisição falhe
    //a) Pois o mensalista é inválido
    @Test
    public void postNovoMensalista_deveRetornarCode400EBodyMensagem_quandoMensalistaInvalido()
    {
        //•ETAPAS•//
        // •ARRANGEMENTS•
        //1) Configurando o mock para retornar um objeto mensalista quando o corpo da requisição for lido
        when(context.bodyAsClass(Mensalista.class)).thenThrow(new RuntimeException());
        //•••••••••••••••••••••••••••••••••••••••

        // •ACTIONS•
        //1) Chamando o handler do endpoint com o mock simulando a classe context
        Controller.postNovoMensalista(context);
        //•••••••••••••••••••••••••••••••••••••••

        // •ASSERTIONS•
        //1) Verificando o status de retorno
        verify(context).status(400);
        //--------------------------------------------/------------------------------------------
        //2) Verificando o corpo de retorno
        verify(context).result("Você enviou as informações do mensalista em um formato inválido!");
    }
    //----------------------------------//--------------------------------
    //b) Pois o nome mensalista é inválido
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "  " })
    public void postNovoMensalista_deveRetornarCode400EBodyMensagema_quandoNomeInvalido(String nomeInvalido)
    {
        //•ETAPAS•//
        // •ARRANGEMENTS•
        //1) Instanciando um nome de mensalista inválido
        Mensalista mensalista = new Mensalista(nomeInvalido);
        //--------------------------------------------/------------------------------------------
        //2) Configurando o mock para retornar um objeto mensalista quando o corpo da requisição for lido
        when(context.bodyAsClass(Mensalista.class)).thenReturn(mensalista);
        //•••••••••••••••••••••••••••••••••••••••

        // •ACTIONS•
        //1) Chamando o handler do endpoint com o mock simulando a classe context
        Controller.postNovoMensalista(context);
        //•••••••••••••••••••••••••••••••••••••••

        // •ASSERTIONS•
        //1) Verificando o status de retorno
        verify(context).status(400);
        //--------------------------------------------/------------------------------------------
        //2) Verificando o corpo de retorno
        verify(context).result("Não sei o nome do mensalista que você deseja cadastrar!");
    }
    //-------------------------------------------------------------------------------------------------------------

    //3) getMensalistaPorMatricula
    //A) Caso a requisição tenha sucesso
    @Test
    public void getMensalistaPorMatricula_deveRetornarCode200EBodyMensalista_quandoMatriculaValida()
    {
        //•ETAPAS•//
        // •ARRANGEMENTS•
        //1) Instanciando um mensalista válido
        Mensalista mensalista = new Mensalista(0, "João");
        //--------------------------------------------/------------------------------------------
        //2) Configurando o mock para retornar a matrícula quando o param da requisição for lido
        when(context.pathParam("matricula")).thenReturn("0");
        //--------------------------------------------/------------------------------------------
        //3) Colocando o mensalista dentro da api
        Controller.mensalistas.put(0, mensalista);
        //•••••••••••••••••••••••••••••••••••••••

        // •ACTIONS•
        //1) Chamando o handler do endpoint com o mock simulando a classe context
        Controller.getMensalistaPorMatricula(context);
        //•••••••••••••••••••••••••••••••••••••••

        // •ASSERTIONS•
        //1) Verificando o status de retorno
        verify(context).status(200);
        //--------------------------------------------/------------------------------------------
        //2) Verificando o corpo de retorno
        verify(context).json(mensalista);
    }
    //--------------------------------------------/------------------------------------------
    //B) Caso a requisição falhe
    //a) Pois a matrícula é inválida
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "  ", "balalau" })
    public void getMensalistaPorMatricula_deveRetornarCode400EBodyMensagem(String matriculaInvalida)
    {
        //•ETAPAS•//
        // •ARRANGEMENTS•
        //1)  Configurando o mock para retornar uma matrícula inválida após ler o param
        when(context.pathParam("matricula")).thenReturn(matriculaInvalida);
        //•••••••••••••••••••••••••••••••••••••••

        // •ACTIONS•
        //1) Chamando o handler do endpoint com o mock simulando a classe context
        Controller.getMensalistaPorMatricula(context);
        //•••••••••••••••••••••••••••••••••••••••

        // •ASSERTIONS•
        //1) Verificando o status de retorno
        verify(context).status(400);
        //--------------------------------------------/------------------------------------------
        //2) Verificando o corpo de retorno
        verify(context).result("A matrícula precisa ser um número inteiro válido!");
    }
    //----------------------------------//--------------------------------
    //b) Pois a matrícula não existe na api
    @Test
    public void getMensalistaPorMatricula_deveRetornarCode404EBodyMensagem_quandoMatriculaNaoExiste()
    {
        //•ETAPAS•//
        // •ARRANGEMENTS•
        //1) Configurando o mock para retornar uma matrícula qualquer após ler o param
        int matricula = 1;
        when(context.pathParam("matricula")).thenReturn(String.valueOf(matricula));
        //•••••••••••••••••••••••••••••••••••••••

        // •ACTIONS•
        //1) Chamando o handler do endpoint com o mock simulando a classe context
        Controller.getMensalistaPorMatricula(context);
        //•••••••••••••••••••••••••••••••••••••••

        // •ASSERTIONS•
        //1) Verificando o status de retorno
        verify(context).status(404);
        //--------------------------------------------/------------------------------------------
        //2) Verificando o corpo de retorno
        verify(context).result("Mensalista de matrícula \"" + matricula + "\" não existe!");
    }
    //-------------------------------------------------------------------------------------------------------------

    //4) getListaMensalistas
    //A) Caso a requisição tenha sucesso
    @Test
    public void getListaMensalistas_deveRetornarCode200EBodyListaMensalistas()
    {
        //•ETAPAS•//
        // •ARRANGEMENTS•
        //1) Instanciando um mensalista válido
        Mensalista mensalista = new Mensalista(1, "João");
        //--------------------------------------------/------------------------------------------
        //2) Declarando uma matrícula válida
        int matricula = 1;
        //--------------------------------------------/------------------------------------------
        //3) Colocando dentro do repositório
        Controller.mensalistas.put(matricula, mensalista);
        //•••••••••••••••••••••••••••••••••••••••

        // •ACTIONS•
        //1) Chamando o handler do endpoint com o mock simulando a classe context
        Controller.getListaMensalistas(context);
        //•••••••••••••••••••••••••••••••••••••••

        // •ASSERTIONS•
        //1) Verificando o status de retorno
        verify(context).status(200);
        //--------------------------------------------/------------------------------------------
        //2) Verificando o corpo de retorno
        verify(context).json(new ArrayList<>(Controller.mensalistas.values()));
    }
    //--------------------------------------------/------------------------------------------
    //B) Caso a requisição falhe
    //a) Pois não há nenhum mensalista cadastrado
    @Test
    public void getListaMensalistas_deveRetornarCode204ESemBody_quandoNaoHaMensalistas()
    {
        // •ACTIONS•
        //1) Chamando o handler do endpoint com o mock simulando a classe context
        Controller.getListaMensalistas(context);
        //•••••••••••••••••••••••••••••••••••••••

        // •ASSERTIONS•
        //1) Verificando o status de retorno
        verify(context).status(204);
        //--------------------------------------------/------------------------------------------
        //2) Verificando o corpo de retorno
        verify(context, never()).json(any());
        verify(context, never()).result(anyString());
    }
}
