package com.gajanan.Job.Posting.Application.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Job Posting API",
                description = """
                         📌 **Job Posting API Documentation** \s
                         This API allows users to manage job postings, including: \s
                         - 📝 **Creating new job postings** \s
                         - 🔍 **Retrieving job listings** \s
                         - ✏ **Updating job details** \s
                         - ❌ **Deleting jobs** \s
                        
                         **🔗 API Support & Resources:** \s
                         - 📧 Contact: [Gajanan](mailto:gajagajanan05@gmail.com) \s
                         - 🔗 GitHub: [Project Repository](https://github.com/Gajanan-Puvanenthiran) \s
                         - 📜 License: [Apache 2.0](http://springdoc.org)
                        \s""",
                version = "1.0",
                contact = @Contact(
                        name = "Gajanan Puvanenthiran",
                        email = "gajagajanan05@gmail.com",
                        url = "https://github.com/Gajanan-Puvanenthiran"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://springdoc.org"
                ),
                termsOfService = "https://example.com/terms"
        ),
        servers = {
                @Server(
                        description = "🚀 Local Development Server",
                        url = "http://localhost:8080"
                )
        }
)
public class SwaggerConfig {
}
