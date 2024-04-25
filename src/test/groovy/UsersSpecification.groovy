import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import com.fasterxml.jackson.databind.ObjectMapper
import com.globalogic.bci.ejercicioapi.dto.CreateUserRequestDTO
import com.globalogic.bci.ejercicioapi.dto.CreateUserResponseDTO
import com.globalogic.bci.ejercicioapi.dto.ErrorResponseDTO
import com.globalogic.bci.ejercicioapi.utils.TokenJWTUtils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest(classes= com.globalogic.bci.ejercicioapi.EjercicioApiApplication.class)
@ActiveProfiles("test")
class UsersSpecification extends Specification {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    def "Name"() {
    }

    def "given a valid request should sign up a user successfully returns 200 OK"() {
        given:
        CreateUserRequestDTO validRequest = createRequest([])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/ejercicio-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.CREATED.value()
        def responseDTO = objectMapper.readValue(response.getContentAsString(), CreateUserResponseDTO.class)
        responseDTO.id != null
        responseDTO.created != null
        responseDTO.isActive.booleanValue() == true

        if(Objects.nonNull(validRequest.name)){
            responseDTO.name == "Matias"
        }

        responseDTO.email != null
        if(validRequest.phones.size() > 0){
            def phones = responseDTO.phones
            phones.size() > 0
            phones.get(0).number == 11345666
            phones.get(0).cityCode == 2
            phones.get(0).number == "3"
        }

        responseDTO.token != null
        def token = responseDTO.token

        Jws<Claims> claimsJws = TokenJWTUtils.validateAndExtractJWTClaims(token);

        claimsJws.getBody().containsKey("email")
        claimsJws.getBody().get("email", String.class) == "matias.rios156@gakkmail.com"
        claimsJws.getBody().containsKey("password")
    }


    def "given an invalid request where email has no '@' returns 400 BAD_REQUEST"() {
        given:
        CreateUserRequestDTO invalidRequest = createRequest([email: "otro.emailexample.com"])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/ejercicio-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        def errorResponseDTO = objectMapper.readValue(response.getContentAsString(), ErrorResponseDTO.class)
        errorResponseDTO.errors.size() > 0
        errorResponseDTO.errors.get(0).detail.contains("email")
    }

    def "given an invalid request where email has no domain has no '.' returns 400 BAD_REQUEST"() {
        given:
        CreateUserRequestDTO invalidRequest = createRequest([email: "otro.email@examplecom"])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/ejercicio-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        def errorResponseDTO = objectMapper.readValue(response.getContentAsString(), ErrorResponseDTO.class)
        errorResponseDTO.errors.size() > 0
        errorResponseDTO.errors.get(0).detail.contains("email")
    }

    def "given an invalid request where email not present returns 400 BAD_REQUEST"() {
        given:
        CreateUserRequestDTO invalidRequest = new CreateUserRequestDTO([
                name: "Matias",
                password: "a2asfGfdfdf4",
                phones: [
                        [number: 11345666, cityCode: 2, countryCode: "3"]
                ]
        ])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/users-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        def errorResponseDTO = objectMapper.readValue(response.getContentAsString(), ErrorResponseDTO.class)
        errorResponseDTO.errors.size() > 0
        errorResponseDTO.errors.get(0).detail.contains("email")
    }

    def "given an invalid request where email is null returns 400 BAD_REQUEST"() {
        given:
        CreateUserRequestDTO invalidRequest = createRequest([email: null])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/ejercicio-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        def errorResponseDTO = objectMapper.readValue(response.getContentAsString(), ErrorResponseDTO.class)
        errorResponseDTO.errors.size() > 0
        errorResponseDTO.errors.get(0).detail.contains("email")
    }

    def "given an invalid request where email is empty returns 400 BAD_REQUEST"() {
        given:
        CreateUserRequestDTO invalidRequest = createRequest([email: ""])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/ejercicio-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        def errorResponseDTO = objectMapper.readValue(response.getContentAsString(), ErrorResponseDTO.class)
        errorResponseDTO.errors.size() > 0
        errorResponseDTO.errors.get(0).detail.contains("email")
    }

    def "given an invalid request where email is blank returns 400 BAD_REQUEST"() {
        given:
        CreateUserRequestDTO invalidRequest = createRequest([email: " "])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/ejercicio-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        def errorResponseDTO = objectMapper.readValue(response.getContentAsString(), ErrorResponseDTO.class)
        errorResponseDTO.errors.size() > 0
        errorResponseDTO.errors.get(0).detail.contains("email")
    }

    def "given an invalid request where email has special character returns 400 BAD_REQUEST"() {
        given:
        CreateUserRequestDTO invalidRequest = createRequest([email: "matias.rios156@gakkmail.com"])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/ejercicio-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        def errorResponseDTO = objectMapper.readValue(response.getContentAsString(), ErrorResponseDTO.class)
        errorResponseDTO.errors.size() > 0
        errorResponseDTO.errors.get(0).detail.contains("email")
    }


    def "given an invalid request where password has less than 8 characters returns 400 BAD_REQUEST"() {
        given:
        CreateUserRequestDTO invalidRequest = createRequest([password: "a2asfG4", email: generateRandomEmailAddress()])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/ejercicio-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        def errorResponseDTO = objectMapper.readValue(response.getContentAsString(), ErrorResponseDTO.class)
        errorResponseDTO.errors.size() > 0
        errorResponseDTO.errors.get(0).detail.contains("password")
    }

    def "given an invalid request where password has more than 12 characters returns 400 BAD_REQUEST"() {
        given:
        CreateUserRequestDTO invalidRequest = createRequest([password: "a2asfGfdfdf4a", email: generateRandomEmailAddress()])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/ejercicio-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        def errorResponseDTO = objectMapper.readValue(response.getContentAsString(), ErrorResponseDTO.class)
        errorResponseDTO.errors.size() > 0
        errorResponseDTO.errors.get(0).detail.contains("password")
    }

    def "given an invalid request where password has no upperCase character returns 400 BAD_REQUEST"() {
        given:
        CreateUserRequestDTO invalidRequest = createRequest([password: "a2asfafdfdf4a", email: generateRandomEmailAddress()])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/ejercicio-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        def errorResponseDTO = objectMapper.readValue(response.getContentAsString(), ErrorResponseDTO.class)
        errorResponseDTO.errors.size() > 0
        errorResponseDTO.errors.get(0).detail.contains("password")
    }

    def "given an invalid request where password has more than one upperCase character returns 400 BAD_REQUEST"() {
        given:
        CreateUserRequestDTO invalidRequest = createRequest([password: "a2asfGfdfdF4", email: generateRandomEmailAddress()])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/ejercicio-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        def errorResponseDTO = objectMapper.readValue(response.getContentAsString(), ErrorResponseDTO.class)
        errorResponseDTO.errors.size() > 0
        errorResponseDTO.errors.get(0).detail.contains("password")
    }
    

    def "given an invalid request where password has no numbers in it returns 400 BAD_REQUEST"() {
        given:
        CreateUserRequestDTO invalidRequest = createRequest([password: "abasfGfdfdFh", email: generateRandomEmailAddress()])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/ejercicio-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        def errorResponseDTO = objectMapper.readValue(response.getContentAsString(), ErrorResponseDTO.class)
        errorResponseDTO.errors.size() > 0
        errorResponseDTO.errors.get(0).detail.contains("password")
    }

    def "given an invalid request where  password has more than 2 numbers in it returns 400 BAD_REQUEST"() {
        given:
        CreateUserRequestDTO invalidRequest = createRequest([password: "a2a5fGfdfdf4", email: generateRandomEmailAddress()])

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/ejercicio-api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andReturn().response

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        def errorResponseDTO = objectMapper.readValue(response.getContentAsString(), ErrorResponseDTO.class)
        errorResponseDTO.errors.size() > 0
        errorResponseDTO.errors.get(0).detail.contains("password")
    }

    def createRequest(overrides) {
        def defaultRequest = [
                name: "Matias",
                email: "matias.rios156@gakkmail.com",
                password: "a2asfGfdfdf4",
                phones: [
                        [number: 11345666, cityCode: 2, countryCode: "3"]
                ]
        ]
        new CreateUserRequestDTO(defaultRequest + overrides ?: [:])
    }

    def generateRandomEmailAddress() {
        def longitudNombre = 8
        def caracteres = "abcdefghijklmnopqrstuvwxyz"
        def random = new Random()

        def nombreAleatorio = (0..<longitudNombre).collect { caracteres[random.nextInt(caracteres.length())] }.join()

        def dominio = "example.com"

        return "${nombreAleatorio}@${dominio}"
    }


}
