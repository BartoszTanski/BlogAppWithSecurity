## Multi module spring boot project 
### consistring of:
- mail service - sending emails and email templates, used for email verification and password change
- user service - user authentication
- posts service - all operations connected to posts 
### App utilizes JWT and Spring Security to secure and provide user authentication
### Its connected to 2 databases:
- PostgreSQL - user service
- MongoDB - posts service
Communicating with AWS RDS PostgreSQL db. Utilizing Java mail API  
## Deployed to AWS EC2 virtual machine
### frontend: [ Link]([https://bartosztanski.azurewebsites.net](https://bartosztanski.azurewebsites.net)
### See also: [ Link]([https://github.com/BartoszTanski/BlogSB](https://github.com/BartoszTanski/BlogSB) for screenshots
