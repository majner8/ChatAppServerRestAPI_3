package ChatAPP_test.FullTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ChatAPP_test.Authorization.jwtTokenTestAuthorizationToken;

@SpringBootTest(classes=Main.Main.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class SendAndSaveToDatabaseMessageTest {
	@Autowired
    private jwtTokenTestAuthorizationToken autToken;
}
