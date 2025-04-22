# Vendor Management System

A Spring Boot application for managing vendors, purchase orders, and vendor performance.

# Swagger Link 
 vendormanagementsystem-h2oa.onrender.com/swagger-ui/index.html


## Configuration

1. Copy `application-example.yml` to `application.yml`:
```bash
cp src/main/resources/application-example.yml src/main/resources/application.yml
```

2. Update the following in `application.yml`:

### MongoDB Configuration
- Replace `<username>`, `<password>`, `<cluster>`, and `<database>` with your MongoDB credentials

### Email Configuration
- Set your email credentials for notifications
- For Gmail, you'll need to use an App Password

### JWT Configuration
- Set a secure secret key for JWT token generation

### OAuth2 Configuration (Optional)
- Add your Google OAuth2 credentials if using Google authentication

## Environment Variables (Alternative)

Instead of using `application.yml`, you can set these environment variables:

```bash
export SPRING_DATA_MONGODB_URI=mongodb+srv://<username>:<password>@<cluster>.mongodb.net/<database>
export SPRING_MAIL_USERNAME=your-email@gmail.com
export SPRING_MAIL_PASSWORD=your-app-password
export JWT_SECRET=your-jwt-secret-key
export GOOGLE_CLIENT_ID=your-google-client-id
export GOOGLE_CLIENT_SECRET=your-google-client-secret
```

## Security Note

Never commit your actual `application.yml` or any file containing real credentials to version control. Always use environment variables or secure configuration management in production. 
