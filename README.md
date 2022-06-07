# spring-chainsLog
A CRUD [Spring Boot](http://projects.spring.io/spring-boot/) project focused in building discussion board for web 3.0. To see the full website visit https://chainslog.herokuapp.com/.

## Tech Stack Used
![Spring](https://img.shields.io/badge/-Spring-%232c3e50?style=for-the-badge&logo=Spring)
![SpringSecurity](https://img.shields.io/badge/-Spring%20Security-%232c3e50?style=for-the-badge&logo=SpringSecurity)
![Thymeleaf](https://img.shields.io/badge/-Thymeleaf-%232c3e50?style=for-the-badge&logo=Thymeleaf)
![CSS3](https://img.shields.io/badge/-CSS%203-%232c3e50?style=for-the-badge&logo=CSS3)
![MySQL](https://img.shields.io/badge/-MySQL-%232c3e50?style=for-the-badge&logo=MySQL)

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [MySQL](https://www.mysql.com/)

## Running the application locally

This project is done through [Eclipse IDE](https://www.eclipse.org/downloads/packages/release/2021-03/r/eclipse-ide-enterprise-java-and-web-developers), to run the application locally open projects from file system and select the project, then run as spring application.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

**get dependencies**
```shell
mvn clean install
```

**run application locally**
```shell
mvn spring-boot:run
```

## Screenshots

**Landing Page**
![image](https://user-images.githubusercontent.com/32029746/169800241-22b06024-9a7e-401b-9845-0d9f6b35388f.png)

**Signin and Signup Pages**
![image](https://user-images.githubusercontent.com/32029746/169801193-b293dc9e-d1ac-44c4-af0f-05dbc7004f07.png)
![image](https://user-images.githubusercontent.com/32029746/169801225-b77cbdc4-276a-49d4-b8d3-a93660516880.png)

**Forum Page**
![image](https://user-images.githubusercontent.com/32029746/169802119-6bc138c0-4c74-47b6-b8d7-69968958dddf.png)

**Create a New Post Page**
![image](https://user-images.githubusercontent.com/32029746/169803726-61109f73-1d16-4338-aab5-3b07b81bff00.png)

**Post Page**
![image](https://user-images.githubusercontent.com/32029746/169803537-06014dc1-5d57-47c8-a0e5-8194c23b04ed.png)


## Implementation of Frameworks

![SpringSecurity](https://img.shields.io/badge/-Spring%20Security-%232c3e50?style=for-the-badge&logo=SpringSecurity) <br>

- **Used in:** Authentication and Route Protection *[AuthService, Home Controller]*
- **Reference Video:** [Spring Boot Security Fundamentals](https://youtube.com/playlist?list=PLVApX3evDwJ1d0lKKHssPQvzv2Ao3e__Q)
- **How it is implemented in the project?:** 
  -  Initial Setup for Spring Security (Dependency)
       ```xml
        <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
       ```
  -  To configure spring security, create a SecurityConfiguration that extends WebSecurityConfigurerAdapter.
     1. Override two configure methdos (AuthenticationManagerBuilding and HttpSecurity)
     2. **HttpSecurity** focuses on route protection and must be user defined based on roles and authentication;
     3. Additionally, to interact with the session token the following must be also configure: Login, Logout, RememberMe.
     4. AuthenicationManagerBuilder focuses on authentication of the account, which needs to configure the following two beans;
     5. DaoAuthenticationProvider to get the input and validate the input from database using the UserAccountService, while
     6. Password Encoder is a spring security features that uses bycrpy to encrypt passwords before receiving and sending in databases.
  - Next, is configure the UserAccount Model;
     1. UserAccount will implements the UserDetails from Spring Security to access its internal model for users,
     2. Then map the username/email, password to your existing User model.
     3. [Optional], you can also implement custom properties in this model to be accessed in session token.
  - For the UserAccount Service,
     1. UserAccount service will implement the UserDetailsService from Spring Security to retrieve the user details from the database.
     2. Create a Data Access Object (Dao) that will find user using username.
     3. Assign the user to the UserAccount Model you configure earlier.
  - Additional Steps,
     1. You can interact directly to spring security if you are currently using thymeleaf.
     2. Include in the pom.xml the following dependency:
     <br>
     
       ```xml
        <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-thymeleaf</artifactId>  
        </dependency>
       ```
___
![Thymeleaf](https://img.shields.io/badge/-Thymeleaf-%232c3e50?style=for-the-badge&logo=Thymeleaf) <br>

- **Used in:** Frontend *[Template]*
- **Reference Link:** [Baeldung Thymeleaf Fundamentals](https://www.baeldung.com/?s=thymeleaf)
- **How it is implemented in the project?:** 
  - Initial Setup for Thymeleaf (Dependency)
       ```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
       ```
  -  Integrating Thymeleaf in templates
     1. Create a HTML file in resources/templates.
     2. Include the following attributes in the HTML Tag;
       ```html
        <html xmlns:th="http://www.thymeleaf.org"
            xmlns:sec="http://www.thymeleaf.org/extras/spring-security"
            lang="en">
       ```
  -  Using Thymeleaf Fragments
     1. Create fragments.html which you will put all of your thymeleaf fragments in project.
     2. Suggested fragments is head (if you will not use JS), navbar and repetitive components in your web app.
     3. To define a thymeleaf fragment include in the opening tag the attribute `th:fragment="<name-of-fragment>"`
     4. To use the defined fragment on other pages use the attribute `th:replace="<html-of-your-fragments> :: <name-of-fragment>"`
  -  Using Thymeleaf Links/URL
     1. To link an image in your thymeleaf page, use the ordinary `<img>` tag with the following attribute, `th:src="@{/link}"`
     2. Similar with the `<a>` and other tags that utilizes links, you must append `th:` in the beggining of attribute and define the link as `@{/link}`
  -  Using Thymeleaf Conditionals
     1. In a scenario that you need to display something base on a variable (defined by `${var}), you can use conditionals directly in thymeleaf.
     2. `th:if="${var}"` is used to display a html tag when the var is true,
     3. Vice versa, `th:unless="${true}"` is used to _**NOT**_ display a html tag when the var is true.
     4. `sec:authorize="isAuthenticated()"` is used to display a html tag if the user is authenticated.
     5. Vice versa, `sec:authorize="isAnonymous()"` is used to display a html tag if the user is anonymous.
  -  Other usage of Thymeleaf in Project
     1. `th:text="${var} / <text>` is used to replace the text of the current tag.
     2. `th:each="var-name: ${var-list}"` is used to repetively create a html tag based on the current var-list.


## Contact
<a href="https://twitter.com/intent/follow?screen_name=scaredmeow_&tw_p=followbutton">
  <img src="https://img.shields.io/twitter/follow/scaredmeow_?label=Twitter&style=social" alt="github">
</a>
<a href="https://www.linkedin.com/in/neilriego/">
  <img src="https://img.shields.io/badge/- -%232c3e50?label=LinkedIn&style=social&logo=linkedin" alt="linkedin">
</a>
<a href="mailto:neilchristianriego3@gmail.com">
  <img src="https://img.shields.io/badge/- -%232c3e50?label=Email&style=social&logo=gmail" alt="gmail">
</a>
<a href="https://calendly.com/neilriego/book-a-meeting">
  <img src="https://img.shields.io/badge/- -%232c3e50?label=Book a Meeting with Me&style=social&logo=Google Calendar" alt="Calendly">
</a>
