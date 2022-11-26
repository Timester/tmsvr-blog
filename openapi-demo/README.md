# OpenAPI demo

This PoC application demonstrates the usage of OpenAPI descriptors and the [openapi generator](https://github.com/OpenAPITools/openapi-generator) project to generate client and server code from OpenAPI descriptors with Gradle for Spring Boot projects.

## Setup

For the code generation to work there must be OpenAPI descriptors available. These should represent a contract between services, fontend-backend etc. on how the API looks like.

The ```build.gradle``` file is commented to highlight the necessary modifications to enable code generation. Generation can be used to create client and server code as well. This example application implements the API described in ```petstore.yml``` and consumes an API described by ```uuid.yml```. The business logic is nonsense, the goal of the PoC is to demonstrate the code generation, and the basic usage of the generated code.

## Code generation on the backend

The ```build.gradle``` file contains the configuration for the code generation in the ```buildUuidClient``` and ```buildPetStoreServer``` tasks. Details on the configuration options can be found [here](https://openapi-generator.tech/docs/generators/) for the various generator options (the PoC uses java for client and spring for server generation), generic config options are [here](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-gradle-plugin)

The generated code is an independent gradle/maven project created in the folder specified in ```build.gradle```. Since it is a project in itself it is possible to publish artifacts built from it to Maven repositories. A scenario can be to generate both client and server code for the API of a service. The client code can be published to a Maven repository and used by other services connecting to this one as a dependency.

In the PoC project instead of depending on the separate artifact created from the generated code, we include the generated source folders in the source sets of the project, and adding the necessary dependencies for it to compile.

## Frontend use

The openapi generator used in the PoC is capable of creating clients for many languages and frameworks, however frontend developers often choose [NSwag](https://github.com/RicoSuter/NSwag) (an other genertor) instead. It is better in generating code for Angular projects.

## How to try out this PoC

Just clone the repo and run the ```bootRun``` gradle task. The code will be generated in the ```build/generated``` folder, according to the settings in ```build.gradle```.

There is an endpoint (generated) which is implemented in the PetStoreController. The delete pet method calls the UUID API using the also generated client code. So by calling the following, you can try out both the server and client generated parts.

```shell
curl --location -H 'Content-Type: application/json' -H 'Accept: application/json' --request GET 'http://localhost:8080/pet/12'
```
