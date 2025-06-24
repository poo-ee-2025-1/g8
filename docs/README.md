## 📖 Introdução:
A pasta `docs/` consiste no espaço para registro conciso e organizado de toda a documentação técnica produzida no projeto. Sempre deve ser mantida atualizada.

## 📁 Estrutura da pasta `docs/`
```
├── docs/
    ├── relatorios/     → relatorios individuais
    ├── uml/            → UMLs do projeto
    └── README.md       → Todos os detalhes, boas práticas, etc.
```

## 🔎 Sobre a pasta `relatorios/`
## 🔎 Sobre a pasta `uml/`
- **Boas Práticas:**
  - Mantenha os diagramas atualizados e concisos com as funcionalidades reais criadas a partir deles.
  - Antes de abrir um pull request, revise atentamente o que foi produzido.
  - Utilize o PlantUML, e salve o diagrama com a seguinte estrutura:
    ```
    nomediagrama-tipodiagrama.txt
    nomediagrama-tipodiagrama.png

    Exemplo:
        equipamento-classe.txt
        equipamento-classe.png
        usuario-casodeuso.txt
        usuario-casodeuso.png
    ```


- **Diagramas**

  - **Diagramas de caso de uso**: 
  
     - **Usuário**: apresenta as ações que o **usuário** pode realizar no sistema **KeepFix**, com foco, no momento, na tela de **Dashboard**.
        ```
         Ações representadas:
         - Visualizar estatísticas
         - Alternar entre tipos de dados
         - Navegar pelo calendário semanal
        ```
    
  - **Diagrama de sequência**: mostra o fluxo de execução do sistema **KeepFix** desde que o **usuário** inicia a aplicação até a visualização da tela **Dashboard**.
   ```
   Etapas representadas:
    - Início da aplicação pela classe `App`
    - Carregamento do layout principal (`MainLayout`)
    - Abertura da tela `Dashboard.fxml`
    - Instanciação dos componentes:
     - Estatísticas de Manutenções
     - Estatísticas de Equipamentos
     - Calendário Semanal
    - Renderização da interface ao usuário
    - Interação com abas e calendário
   ```
