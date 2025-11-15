# Introdução

## Descrição

## Objetivo do projeto

# Arquitetura do Sistema

## Descrição da Arquitetura

## Diagrama da Arquitetura

```mermaid
classDiagram
    
    class Address{
    	-Long id
        -String street
        -String number
        -String city
        -String zipCode
    }
    
    class User{
        <<abstract>>
      -Long id
      -String name
      -String email
      -String login
      -String password
      -LocalDate lastModifiedDate
      -Address address
    }

    class Client{
    	-List~Order~ orders
    }

    class RestaurantOwner{
    	-List restaurants
    }

    class UserDTO{
    <<record>>
    -String name
    -String email
    -String login
    -Address address
    }
    
    class UserCreateDTO{
    <<record>>
    -String name
    -String email
    -String login
    -String password
    -Address address
    }
    
    class UserUpdateDTO{
    <<record>>
    -String name
    -String email
    -Address address
    }
    
    class UserChangePasswordDTO{
    <<record>>
    -String password
    }

    class LoginDTO{
        <<record>>
        -String login
        -String password
    }
    
    class UserService {
        <<interface>>
        +createUser(UserCreateDTO userCreateDto) Long
        +updateUser(Long id, UserCreateDTO userCreateDto)
        +changePassword(Long id, UserChangePasswordDTO userChangePasswordDto)
        +validateLogin(LoginDTO login) String
        +searchByName(String name) List~UserDTO~
    }
    
    class UserRepository {
        <<interface>>
        +findById(Long id) Optional~User~
        +findByEmail(String email) Optional~User~
        +findByNameContaining(String name) List~User~
    }

	User --> Address
    User <|-- Client
    User <|-- RestaurantOwner
    UserService .. User
    UserService .. LoginDTO
    UserService .. UserDTO
    UserService .. UserCreateDTO
    UserService .. UserUpdateDTO
    UserService .. UserChangePasswordDTO
    UserService .. UserRepository
    
```

```mermaid
sequenceDiagram
  actor User as User
  participant Controller as Controller
  participant Service as Service
  participant DB as DB
  User ->> Controller: POST /user
  Controller ->> Service: createUser()
  Service ->> DB: Save User
  DB ->> Service: User
  Service ->> Controller: UserDTO
  Controller ->> User: 201 created
  User ->> Controller: GET /user?name=xx
  Controller ->> Service: searchByName()
  DB ->> Service: List<User>
  Service ->> Controller: List<UserDTO>
  Controller ->> User: 200 UserDTO
  User ->> Controller: PUT /user/{userId}/pass
  Controller ->> Service: changePassword()
  DB ->> Service: User
  Service ->> Controller: OK
  Controller ->> User: 204 No Content
  User ->> Controller: DELETE /user/{userId}
  Controller ->> Service: delete()
  DB ->> Service: OK
  Service ->> Controller: OK
  Controller ->> User: 204 No Content
  User ->> Controller: POST user/login
  Controller ->> Service: validateLogin()
  Service ->> Controller: OK
  Controller ->> User: 200 Token JWT
```

# Descrição dos Endpoints da API

## Tabela de Endpoints

## Exemplos de requisição e resposta

# Configuração do Projeto

## Configuração do Docker Compose

## Instruções para execução local

# Qualidade do Código

## Boas Práticas Utilizadas

# Collections para Teste

## Link para a Collection do Postman

## Descrição dos Testes Manuais

# Repositório do Código

## URL do Repositório

