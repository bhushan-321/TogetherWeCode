	Welcome to E-store of all of us
 
   This project is build using technologies java, spring boot, Angular. This project is creted for the purpose of learning. It involves features like CRUD, Authorization , Authentication, Role Authentication, User, Product & Order Management, Social Logins, Payment Gateway.
 
 
Url to run project
http://localhost:9090/index.html
 
Steps to clone this project
 
git clone url
 
Enter your credentials
 
git checkout -b feature/your-branch 
create feature branch to test or to make changes
 
 
steps to run project
 
In application.properties
 
server.port=9090
#you can change this port if you want
 
#db configurations
spring.datasource.url= jdbc:mysql://localhost:3306/electronic_store?serverTimezone=UTC
#replace above url if required
 
spring.datasource.username=root
#replace above username as per yours username
 
spring.datasource.password=root
#replace above password as per yours password
 
 
#file config
#These properties are for validations of file upload functionality
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
 
 
 
#path config
#images would be inserted in this directories after successful upload
user.profile.image.path=images/users/
product.image.path=images/products/
 
#This is for role authorization
admin.role.id=1
normal.role.id=2
 
 
#These credentials are for payment pgateway that is razorpay, you can create yours if you wish
razorPayKeyId=rzp_test_UmG5aFbiLnRrMU
razorPayKeySecret=VEucafEN9lYD3JtNDLm0FUFH
 
 
#these properties are for feature login using google/ social login
googleClientId=857115525270-kut1uh97adk9tq0ealooi8d2gpvbsj73.apps.googleusercontent.com
googleSecretClient=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
newPassword=aenanasnfnaskfknasfkassdfdsfsdfsdgsdg
 
# email properties
#these are for forgot password functionality
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=Chaitanya.renuse17@gmail.com
spring.mail.password=sgdxrhgjjzickkpo
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.ssl.trust=smtp.gmail.com
