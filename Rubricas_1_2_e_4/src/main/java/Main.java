import models.ResponseCodeAndBody;
import routings.Routing;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
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
        /*/ ------------------------------- SETUP DA API ------------------------------- /*/
        //•ETAPAS•//
        //1) Instanciando meu servidor e configurando para o javalin usar o Jackson como conversor de JSON
        var app = Javalin.create(config ->
        {
            config.jsonMapper(new JavalinJackson());
        });
        //--------------------------------------------/------------------------------------------

        //2)  Mapeamento de rota para checar se o servidor subiu
        app.get("/", context -> context.result("Servidor online."));
        //--------------------------------------------/------------------------------------------

        //3) Adicionando as rotas mapeadas no controller
        Routing.mapeamentoDasRotas(app);
        //--------------------------------------------/------------------------------------------

        //4) Iniciando o servidor configurado
        app.start(7777);
    }
}
