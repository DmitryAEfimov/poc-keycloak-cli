# Client Demo Application for custom Keycloak User Storage Provider

This application authorizes users with Keycloak server and show Keycloak Principal Details. 
Also it show user roles details according to [user_model](https://github.com/DmitryAEfimov/keycloak-external-db-provider/blob/master/example/db_schema.png)
See [Custom Keycloak User Storage Provider](https://github.com/DmitryAEfimov/keycloak-external-db-provider) for more details

## Prepare
1. Follow [README.md](https://github.com/DmitryAEfimov/keycloak-external-db-provider/blob/master/README.md) to run user& roles external database and Keycloak server
2. Login to Keycloak with admin credentials 
3. Go to realm and switch to `Client` panel
4. Create new client. For example create
   * `Client ID`: cli-app. Leave defaults and save.
   * On the `Settings tab` set
     * `Access Type`: `confidential` or `public`
     * `Valid Redirect URIs`: `http://localhost:8080/*`
     * Leave defaults and save.
   * Switch to `Credentials` tab and copy `Secret` when `Access Type=confidential` 

### Client application Dockerfile environments
* KEYCLOAK_AUTH_URL. Keycloak login page. No defaults. Set it as `KEYCLOAK_AUTH_URL=http://<hostname_or_ip>:<keycloak_port>/auth`
* KEYCLOAK_REALM_NAME. Keycloak realm to work with. No defaults. 
* KEYCLOAK_CLI. Keycloak client ID. No defaults. Set this value equals to Keycloak `Client ID`
* KEYCLOAK_SECRET. Keycloak client secret. No defaults. Skip when Keycloak client has `Access Type=public`. Set this value equals to Keycloak client `Credentials -> Secret`. 
* KEYCLOAK_PUBLIC_CLI. Is Keycloak client public. Default is `false`
* DB_URL. User&role database. Used to pull roles details. No defaults. Set it as `DB_URL=jdbc:postgresql://<db_hostname_or_ip>:<db_port>/<dbname>`
* DB_USERNAME. Username to connect to user&role database. No defaults.
* DB_PASSWORD. Password to connect to user&role database. No defaults.

## Deployment
    docker build -t cli-app .
    -p 8080:8080
    --env KEYCLOAK_AUTH_URL=http://<hostname_or_ip>:<keycloak_port>/auth
    --env KEYCLOAK_REALM_NAME=<realm_name>
    --env KEYCLOAK_CLI=<client_id>
    --env KEYCLOAK_SECRET=<client_secret>
    --env DB_URL=jdbc:postgresql://<db_hostname_or_ip>:<db_port>/<dbname>
    --env DB_USERNAME=<username>
    --env DB_PASSWORD=<password>
    --name cliapp
    -t
    -i
    cli-app

## Endpoints
1. Base URL:  `/`, `/poc`. Secured with Keycloak role `regular`
2. Check access `/poc/access/{accountType}`. Secured with Keycloak role equals to `{accountType}`

## Usage
1. Open web browser and go to application page `http://<cli-app.hostname>:<cli-app.port>/`
2. You will be redirected to Keycloak login page. Login to application using `username` and `password` 
3. After successfully login you will be redirected to application base-url `http://<cli-app.hostname>:<cli-app.port>/poc`
4. There is a detail info about Keycloak Principal
5. At the page's bottom you can see principal roles
6. You can see roles details (from External Database) by pressing `Show` button.