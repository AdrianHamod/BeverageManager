# Frontend service

The Front-End part of BEM app will consist of an Angular application that makes requests to an API Gateway in order to retreive/send data.

The app design will consist of three main components:

## Authentication page

This is the part of the application that handles user registration or user access. It is a fundamental part for providing recommandations and filtering, but it is not required.

### Register

The Registration page will consist of a form with 5 fields. All of the fields are mandatory, and they are:

- Email Address
- Password
- Password repeat
- Age
- Gender
- Nationality

### Sign In

The Sign Up page consists only of the email address and password fields, and are to be used by an already registered user. The page is a form as well, submitting data upon clicking "Sign In" button.

## Beverage Page

The Beverages page is the main part of the application. Here, the user will see a list of all the beverages, which are filtered to fit his recommandations, but only if he is registered. The UI of the application has been thought with simplicity and ease of use in mind.
The app contains a page with a header, that contans a hamburger menu and the content of the application.

### Beverage Listing

This is the page where the user will be presented with a list of beverages, filtered by his preferences and context. He will be able to filter further, by using the simple list filter (by name, by price...). For each beverage he can see the title of the beverage, a picture, a small description, an average price, a rating for the beverage as well as an icon suggesting that the beverage is recommended for the user.

### Beverage Details

Upon clicking a beverage from the list the user will be taken to the beverage details page where he can see in detail the beverage description, more pictures, the contents, reviews. On this page he can also mark the beverage as a favorite, thus adding it to his preferences.

## Profile Page

On the user profile page, a logged in user can see his personal details, and he can update them as well. He is also able to view his previous recommandations, removing the ones that don't make sense anymore (if there are any)

### User Profile

Here the user can view/edit/update his personal details in case there was a mistake or a change occured in one of them

### User Preferences

Here the user can view/edit/update/delete his personal preferences for beverages. He is able to also see the context of the preference here as well.
