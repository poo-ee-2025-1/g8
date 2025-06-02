# REPOSITÓRIO DO GRUPO 8

## Projeto: KeepFix

### 🎯 Objetivo:
Consiste em um projeto - com estrutura de App Planner - para controle e visualização de manutenções de equipamentos.

### 🧩Funcionalidades Previstas:
- **Dashboard resumido**, com cards informativos sobre manutenções e equipamentos.
- **Cadastro, edição e exclusão de dados** (CRUD completo).
- **Cadastro de manutenções realizadas e futuras**, com status e descrição.
- **Tela de configurações** para ajustes gerais.
- **Sistema de navegação intuitivo**, com FXML modularizado.
- **Layout moderno e responsivo**.

### 📁 Estrutura do Projeto:
```
├── docs/				→ Documentações técnicas do projeto
│	├── uml/
│	└── README.md
├── src/
│	└── main/
│		├── java/		→ Código-fonte
│		└── resources/		→ FXML, imagens, fontes e estilos
├──.gitignore
├── pom.xml				→ Configuração Maven
├── README.md
```

### 🛠️ Requisitos Técnicos:
- JDK 17+
- JavaFX (configurado via Maven)
- VS Code ou IDE de preferência

### 👥 Equipe:
**Líder:** Ricardo Nunes [github.com/ricardo-45]
**Integrantes:** Henrique Ferreira [github.com/henriqueferreira002], Victor Afonso [github.com/alphonseminus]

### 🌳Estrutura das Branches:
- `main`: versão final e estável (entregável)
- `dev`: base para integrar a parte funcional do projeto à `main`
- `docs`: base para integrar a parte de documentos à `main`
- Cada integrante terá sua própria branch:
  - `seunome-dev` → para desenvolvimento de funcionalidades
  - `seunome-fix` → para correções ou ajustes
  - `seunome-docs` → para escrever ou atualizar documentos
  
  Exemplo: `henrique-dev`, `victor-docs`, etc.

### 🚀 Como contribuir:
1. **Crie sua branch:**
	```
	git checkout -b seunome-dev
	```
2. **Trabalhe na sua tarefa** (implementação, tela, componente...)
3. **Faça commit com mensagens claras**
	```
	git commit -m "Adiciona tela de equipamentos com layout inicial"
	```
4. **Suba sua branch**
	```
	git push origin seunome-dev
	```
5. **Abra um Pull Request** para a branch `dev`, `docs` ou conforme o tipo de contribuição.
6. **Aguarde revisão e aprovação do líder antes do merge**
7. **Observações:**
	- Qualquer dúvida específica relacionada a estrutura do projeto ou sobre a contribuição, contatar o líder no privado.
	- No repositório [https://github.com/marceloakira/tutorials] possui os devidos tutoriais para os tópicos abordados no desenvolvimento do projeto. Considere revisá-lo antes de direcionar alguma dúvida. (recomendo, a princípio, os tutoriais de Git e UML Básico).

### Observações Importantes:
- Para todas as funções Git, recomendo fortemente utilizar o GitHub Desktop, pois facilita muito. Link de Download: [https://github.com/apps/desktop].
- Para rodar o projeto, recomendo fortemente que utilize o Maven [https://maven.apache.org/download.cgi], assim, evitando qualquer problema.
- Escrevam código limpo, comentem quando necessário e mantenham as boas práticas para os commits e pull requests (qualquer dúvida, contatem o líder).
- Para os documentos (ex: README, UMLs...) mantenham atualizados de acordo com as mudanças feitas. Registrem tudo que for necessário, de forma concisa, no momento da mudança, para não gerar desatualização no projeto.
- Não altere a estrutura inicial do projeto, a menos que seja discutido e acordado entre os integrantes previamente, para evitar problemas futuros.
- Leia o README de [docs/](.docs/) para ter as informações sobre a documentação técnica do projeto.

### 📄 Licença
Este projeto é de uso acadêmico, desenvolvido para fins educacionais. Todos os direitos reservados ao Grupo 8.
