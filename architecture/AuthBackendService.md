# Authentication Backend

The authentication flow starts with the user entering the web application and choosing either to sign in, or to sign up. In the sign in page the user can enter a non existent account, and he will be redirected to the signup page for account creation. Login/Signup uses the user's email address and password. If he has an account he will be signed in, otherwise he will need to create an account.
The authentication service will be a Spring service running on a Fargate Container, where user credentials are stored using Amazon RDS, a managed distributed relational database.

## Sign up

The sign up process requires the user to enter his email, his password twice, as well as his age, gender and nationality. After this, his password is encrypted and an entry is added to the RDS database to identify the user at a later time. After the credentials have been added, the unique id is then used to setup the user profile.

## Sign in

Signing in requires the user to just enter his email and password. The user's password is encrypted and the user is authenticated by looking for the password's sha256 digets inside RDS based on the user's email address.

After the Signin/Signup process is done successfully, the server responds with an access token. That JWT token will then be used to authenticate all user requests further on. The user is redirected to the main "Browse" page, where all the beverages are listed.
