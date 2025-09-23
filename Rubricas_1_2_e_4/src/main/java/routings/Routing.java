package routings;

/*/ ------------------------------- IMPORTAÇÕES------------------------------- /*/
import controllers.Controller;
import io.javalin.Javalin;
//========================================================

public class Routing
{
    /*/ ------------------------------- SETUP ------------------------------- /*/
    //1) Para definir o pathing no roteamento
    private static String pathMensalistas = "/mensalistas";
    //========================================================

    /*/ ------------------------------- MAPEAMENTO DAS ROTAS ------------------------------- /*/
    //1) Para mapear as rotas de requisição
    public static void mapeamentoDasRotas(Javalin app)
    {
        //•ENDPOINTS•//
        // # Rúbrica 1
        // hello
        app.get("/hello", Controller::getHello);
        //--------------------------------------------/------------------------------------------

        //  status
        app.get("/status", Controller::getHoraAtual);
        //--------------------------------------------/------------------------------------------

        //  echo
        app.post("/echo", Controller::postMensagem);
        //--------------------------------------------/------------------------------------------

        // saudacao/{nome}
        app.get("/saudacao/{nome}", Controller::getSaudacaoNome);
        //--------------------------------------------#--------------------------------------------

        // # Rúbrica 4
        //1)GET lista com todos os mensalistas
        app.get(pathMensalistas, Controller::getListaMensalistas);
        //--------------------------------------------/------------------------------------------

        //2) GET com um mensalista usando a matrícula
        app.get(pathMensalistas +"/{matricula}", Controller::getMensalistaPorMatricula);
        //--------------------------------------------/------------------------------------------

        //3) POST criação de um mensalista
        app.post(pathMensalistas, Controller::postNovoMensalista);
    }
}
