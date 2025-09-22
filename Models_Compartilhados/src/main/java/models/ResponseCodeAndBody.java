package models;

//Record para receber o status code e o body da requisição e usar no retorno das requisições HTTP
public record ResponseCodeAndBody(int code, String body)
{
}
