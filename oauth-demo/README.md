# OAuth 2.0 demo

1. Set up the config in `application.yml`
2. Start the app, call the endpoints
   1. `curl --request GET \
      --url http://localhost:8080/api/authorization/start-auth`
   2. `curl --request POST \
      --url http://localhost:8080/api/authorization/finish-auth \
      --header 'Content-Type: application/json' \
      --data '{
      "state":"dont-do-this",
      "code":"4/0AX4...35LBw"
      }'`

For details see: https://tmsvr.com/oauth-2-implementation