<h1 align=center> Desafio Quality </h1>
<p align=center> TRANSFORMELI </p>
<div align=center>
<img src="https://github.com/rebecccruz/gif/blob/main/giphy.gif" />
</div>
<br>

## ✅ Testes
* Integração [`src/test/java/com/github/transformeli/desafio_quality/integration`](src/test/java/com/github/transformeli/desafio_quality/integration)
* Unitários [`src/test/java/com/github/transformeli/desafio_quality`](src/test/java/com/github/transformeli/desafio_quality)

## 🚩 Postman collection
* Exemplos de Uso [`docs/Desafio-Quality-Grupo-2-Transformeli.postman_collection.json`](docs/Desafio-Quality-Grupo-2-Transformeli.postman_collection.json)

## 📝 Proposta
A empresa "Seu Imóvel" precisa ser capaz de calcular a metragem e o custo dos
diferentes imóveis que possui em sua carteira de clientes.
Para isso, solicita de cada Imóvel:
- um nome do imóvel,
- um bairro,
- e a lista de cômodos.
#### Um Bairro deve ter:
- nome do bairro,
- valor do metro quadrado no bairro

#### Cada cômodo tem:
- um nome,
- uma largura e
- um comprimento.

Para isso, é necessário a criação de uma API Rest que permita:

* US-0001: Calcular a área total de uma propriedade.
* US-0002: Indicar o preço dessa mesma propriedade com base na área total.
Lembre-se que os preços por metro quadrado são determinados em função do
bairro.
* US-0003: Determinar qual é o maior cômodo da propriedade.
* US-0004: Determinar a área de cada cômodo.

## 🚀 Como clonar e iniciar a aplicação

- Abra seu terminal e digite o seguinte comando:

```
git clone git@github.com:evycoliveira/desafio_quality.git
```

- Ainda em seu terminal digite o comando abaixo para entrar na pasta do desafio:

```
cd desafio_quality
```

- Instale as dependências do maven:

```
mvn install
```

ou
<br>

```
maven install
```

- Rode localmente:

```
mvn spring-boot:run
```

## 👥 Membros do grupo

- <a href="https://github.com/aborgssouzameli">Alexandre Borges Souza</a>
- <a href="https://github.com/evycoliveira">Evelyn Cristini Oliveira</a>
- <a href="https://github.com/isaiasfmeli">Isaias Finger</a>
- <a href="https://github.com/laridevmeli">Larissa Navarro</a>
- <a href="https://github.com/lucaspinheirorocha">Lucas Pinheiro Rocha</a>
- <a href="https://github.com/rebecccruz">Rebecca da Cunha Cruz</a>
