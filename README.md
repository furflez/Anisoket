Anisoket
========

Estas classes tem por objetivo simplificar a configuração e utilização de sockets em Java e C#

##Usabilidade


#### 1 - Criar uma instância da classe `SoketSupport`

```csharp
var soket = new SoketSupport();
```

#### 2 - Invoke `Configure()` 
Passando por argumentos:
* Tipo do soket (`As.Server` ou `As.Client`)
* IP do servidor
* Porta do servidor

```csharp
soket.Configure(As.Client,"192.169.10.1","41000"); //Configurado como cliente
//ou
soket.Configure(As.Server,"192.169.10.1","41000"); //Configurado como servidor
```
#### 3 - Iniciar o ciclo
Agora você tem acesso aos métodos `void SendMessage(string text)` e `string ReceiveMessage()` para trocar dados entre cliente e servidor.

Se foi configurado com:
* `As.Client` : primeiro deve fazer a chamada para o servidor usando o `SendMessage`, e então invocar o `ReceiveMessage` para obter sua resposta.
* `As.Server` : primeiro deve ouvir a resposta do cliente com `ReceiveMessage`, para então invocar o `SendMessage` para retornar a resposta para o cliente.

##Métodos Auxiliares
* `GetTypeConnection()`: Retorna o tipo de conexão informado no parâmetro `As`.
* `GetLocalIp()`: Retorna o IP da máquina local na rede que está inserida.
* `ForceClose()`: Cancela o request, interrompendo o ciclo e deixando disponível para o próximo. No caso de
  * Cliente: Deve ser chamado após `SendMessage()` para cancelar o recebimento resposta do server.
  * Servidor: Deve ser chamado após `ReceiveMessage()` para cancelar o envio da resposta para o client


