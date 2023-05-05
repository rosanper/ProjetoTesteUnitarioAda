# ProjetoTesteUnitarioAda

Projeto final do módulo de Teste Unitário no qual é feito um CRUD utilizando TDD.

Este aplicativo foi feito a partir do código fornecido pelo professor do curso através do repositório: https://github.com/alexsaspbr/projeto-final

## Requisitos do projeto

- Criar um CRUD com a entidade Veiculo.class, aplicando a técnica do TDD (Test Driven Development) com base nos testes disponibilizados;
- 90% de Cobertura do projeto.

## Regra de Negócio

A aplicação tem a opção de criar, alterar e deletar um veículo no banco de dados, além de listar todos os veículos;

A classe veiculoDTO é compospa por:
- placa: String;
- modelo: String;
- marca: String;
- disponivel: boolean;
- dataFabricacao: LocalDate.


A plca deve ser passada no formato "XXX-0000", onde X representa uma letre e 0 um número;

A data deve ser passada no formato yyyy-MM-dd.

### Endpoints
1. "/veiculo/todos" :
    - GET: Retorna todos os Veículos.

2. "/veiculos/" :
    - POST: Insere um novo veículo passando um "veiculoDTO" no body;
    - PUT: Atera as informações de um veículo passando um "veiculoDTO" no body;
  
3. "/veiculos/{placa} :
    - DELETE: Deleta um veículo passando a placa dele.
  
### Postman

https://www.postman.com/rosanper/workspace/teste-unitrio-ada/collection/19670354-ab55ddc1-dcaf-4acf-8c6f-dd82ecc6aa73?action=share&creator=19670354
