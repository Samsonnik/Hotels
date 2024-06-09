package by.samsonnik.hotel.utill;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Hotels RESTFULL api",
                description = "This is just a test API, however you can try it. There was realized basic functional like" +
                        "GET and POST requests. The service can receive, send and process data in JSON format." +
                        "p.s. If you have advices, or can say couple words about this app disadvantages or bugs give me " +
                        "feedback, please on my email bellow or telegram \"@raccoon2244\". This is important for me :)",
                contact = @Contact(
                        name = "Evgen",
                        email = "samsonnik1997@gmail.com"
                )

        )
)
public class OpenApiConfig {
}
