# Trabalho desenvolvido para cadeira de Dispositivos Móveis da Uniritter, professor Jean Lopes.

Este projeto é um aplicativo de previsão do tempo que foca em pessoas que tendem a viajar, e querem saber a temperatura do local atual e de destino.

Este projeto utiliza em seu conteúdo RecyclerView e DataBinding tentando otimizar seu conteúdo.

O aplicativo utiliza sensores de Geolocalização do dispositivo, tendo esta solicitação ao usuário, e sendo utilizada diretamente nas requests que são feitas à uma API externa.

O sensor de temperatura também é aplicado no código podendo ser utilizado para verificação da temperatura ambiente em dispositivos que possuam esta possibilidade.

A API utilizada foi da https://https://openweathermap.org/api para requisições de previsão do tempo.

No projeto também existe a possibilidade de compartilhamento de dados via WhatsApp, um dos requisitos propostos no projeto.

Nele existem autenticações de email, senha, telefone e várias obrigatoriedades impostas durante o cadastro.

O cadastro depois de criado é salvo no serviço do Firebase do Google, o que permite um melhor gerenciamento e segurança dos dados.

O perfil criado também é salvo no Firestore Database para que estes dados possam ser apresentados no menu perfil apresentado dentro do aplicativo.



