package foo;

import org.springframework.stereotype.Component;

@Component
public class WelcomMessage {
    public String getWelcomeMessage() {
        return "Welcome to the Demo Application!";
    }
}
