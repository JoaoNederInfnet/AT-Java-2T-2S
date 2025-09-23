/*/ ------------------------------- IMPORTAÇÕES------------------------------- /*/
import routings.Routing;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
//========================================================

public class Main
{
    /* _>
            / ------------------------------- *DISCLAIMER* ------------------------------- /
            Se o senhor preferir ver o código com todos os comentários, por favor cheque o código pelo github (preferi enviar o AT só com comentários mais básicos e adicionar tudo depois quando eu tiver terminado todos os AT's) */
    public static void main(String[] args)
    {
        /*/ ------------------------------- SETUP DA API ------------------------------- /*/
        //•ETAPAS•//
        //1) Instanciando minha api e configurando para o javalin usar o Jackson como conversor de JSON
        var app = Javalin.create(config ->
        {
            config.jsonMapper(new JavalinJackson());
        });
        //--------------------------------------------/------------------------------------------

        //2)  Mapeamento de rota para checar se a api subiu
        app.get("/", context -> context.result("Servidor online.")); // Só fazer curl http://localhost:7777/ no terminal para checar
        //--------------------------------------------/------------------------------------------

        //3) Adicionando as rotas mapeadas no controller
        Routing.mapeamentoDasRotas(app);
        //--------------------------------------------/------------------------------------------

        //4) Iniciando a api configurada
        app.start(7777);
    }
}
