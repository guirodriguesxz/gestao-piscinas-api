# 🏊‍♂️ Smart Pool Management API

> **Transformando um negócio tradicional de manutenção de piscinas em uma operação guiada por dados e preparada para Inteligência Artificial.**

## 📖 O Problema e o Valor de Negócio
Empresas tradicionais de manutenção de piscinas costumam operar de forma reativa e baseada em papel. O técnico visita o cliente, anota os dados da água e vai embora. O cliente só liga quando a piscina fica verde.

Este projeto é a **Fase 1** da modernização desse fluxo. Trata-se de uma API RESTful construída em Java que digitaliza a operação, registrando clientes, características das piscinas e o histórico químico da água (pH, Cloro, produtos aplicados).

O grande diferencial de negócio: **A arquitetura foi desenhada para a Fase 2**, onde um microsserviço em Python/IA consumirá este banco de dados para cruzar os níveis químicos com a previsão do tempo e **prever proativamente** quando a água vai desequilibrar, sugerindo visitas antes que o cliente precise reclamar.

## 🚀 Tecnologias Utilizadas
* **Linguagem:** Java 21
* **Framework:** Spring Boot 3 (Web, Data JPA)
* **Banco de Dados:** PostgreSQL relacional
* **Infraestrutura:** Docker (Containerização do Banco de Dados)
* **Documentação:** Swagger (OpenAPI 3)

## 🏗️ Modelagem de Dados (Domain-Driven Design)
A API foi estruturada com relacionamentos lógicos do mundo real:
* `Cliente` (1) -> (N) `Piscina` (Permite que condomínios tenham várias piscinas atreladas a um contrato).
* `Piscina` (1) -> (N) `Visita_Tecnica` (Gera a série histórica de dados necessária para o modelo preditivo futuro).

## ⚙️ Como rodar o projeto localmente

1. **Suba o Banco de Dados via Docker:**
```bash
docker run --name banco-piscinas -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=gestao_piscinas -p 5432:5432 -d postgres