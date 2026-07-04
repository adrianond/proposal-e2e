# language: pt
Funcionalidade: Login na aplicação de Propostas
  Como usuário do sistema de propostas
  Quero realizar login com minhas credenciais
  Para acessar o dashboard de propostas

  Cenário: Login com credenciais válidas
    Dado que o usuário está na tela de login da aplicação de propostas
    Quando ele informa o usuário "PEDRO SANTOS" e a senha "123"
    E confirma o login
    Então ele deve ser redirecionado para o dashboard de propostas
