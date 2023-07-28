package com.laywerapi.laywerapi.utils;

import com.laywerapi.laywerapi.dto.request.ClientRequestDTO;
import com.laywerapi.laywerapi.dto.request.RegisterUserRequestDTO;
import com.laywerapi.laywerapi.dto.request.TrialAddRequestDTO;
import com.laywerapi.laywerapi.dto.request.UserUpdateRequestDTO;
import com.laywerapi.laywerapi.dto.response.ClientResponseDTO;
import com.laywerapi.laywerapi.dto.response.UserUpdatedResponseDTO;
import com.laywerapi.laywerapi.entity.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtil {

    public static RegisterUserRequestDTO createUserAddRequestDTO() {
        RegisterUserRequestDTO registerUserRequestDTO = new RegisterUserRequestDTO();
        registerUserRequestDTO.setFirstName("FIRSTNAME");
        registerUserRequestDTO.setLastName("LASTNAME");
        registerUserRequestDTO.setEmail("email@example.com");
        registerUserRequestDTO.setPhone("+38145845687");
        registerUserRequestDTO.setUsername("username");
        registerUserRequestDTO.setPassword("password");

        return registerUserRequestDTO;
    }

    public static User createUser() {
        User user = new User();
        user.setId(1234L);
        user.setFirstName("FIRSTNAME");
        user.setLastName("LASTNAME");
        user.setEmail("email@example.com");
        user.setPhone("+38145845687");
        user.setUsername("username");
        user.setPassword("password");
        user.setRole("ROLE_USER");

        return user;
    }

    public static User newCreateUser() {
        User user = new User();
//        user.setId(1234L);
        user.setFirstName("FIRSTNAME");
        user.setLastName("LASTNAME");
        user.setEmail("email@example.com");
        user.setPhone("+38145845687");
        user.setUsername("username");
        user.setPassword("password");
        user.setRole("USER");

        return user;
    }

    public static User createUpdatedUser() {
        User user = new User();
        user.setId(1234L);
        user.setFirstName("FirstNameUpd");
        user.setLastName("LastNameUp");
        user.setEmail("updated@example.com");
        user.setPhone("+381458456872");
        user.setUsername("usernameUpdated");
        user.setPassword("password");
        user.setRole("USER");

        return user;
    }


    public static UserUpdateRequestDTO createUserUpdateRequestDTO() {
        UserUpdateRequestDTO userUpdateRequestDTO = new UserUpdateRequestDTO();
        userUpdateRequestDTO.setFirstName("FirstNameUpd");
        userUpdateRequestDTO.setLastName("LastNameUp");
        userUpdateRequestDTO.setEmail("updated@example.com");
        userUpdateRequestDTO.setPhone("381458456872");
        userUpdateRequestDTO.setUsername("usernameUpdated");

        return userUpdateRequestDTO;
    }

    public static UserUpdatedResponseDTO createUserUpdateResponseDTO() {
        UserUpdatedResponseDTO userUpdatedResponseDTO = new UserUpdatedResponseDTO();
        userUpdatedResponseDTO.setFirstName("FName1");
        userUpdatedResponseDTO.setLastName("LName1");
        userUpdatedResponseDTO.setPhone("+38145782154");
        userUpdatedResponseDTO.setEmail("update1@example.com");
        userUpdatedResponseDTO.setUsername("usernameNew");

        return userUpdatedResponseDTO;
    }

    public static List<User> createUserList() {
        List<User> users = new ArrayList<>();
        users.add(TestUtil.createUser());
        return users;
    }

    public static ClientRequestDTO createClientRequestDTO() {
        ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
        clientRequestDTO.setFirstName("FirstName");
        clientRequestDTO.setLastName("LastName");
        clientRequestDTO.setPhone("554479561");
        clientRequestDTO.setEmail("clr@example.com");
        clientRequestDTO.setIdNumber(123103213123L);

        return clientRequestDTO;
    }

    public static Client createClient(ClientRequestDTO clientRequestDTO, User user) {
        Client client = new Client();
        client.setId(12345L);
        client.setFirstName(clientRequestDTO.getFirstName().toUpperCase());
        client.setLastName(clientRequestDTO.getLastName().toUpperCase());
        client.setPhone(clientRequestDTO.getPhone());
        client.setEmail(clientRequestDTO.getEmail());
        client.setIdNumber(clientRequestDTO.getIdNumber());
        client.setUserId(user);

        return client;
    }

    public static Client createClientTrial(User user) {
        Client client = new Client();
        client.setId(12345L);
        client.setLastName("LastNAme");
        client.setFirstName("FirstNAme");
        client.setPhone("12345");
        client.setEmail("email@example.com");
        client.setIdNumber(1323123L);
        client.setUserId(user);
        return client;
    }

    public static List<Client> creatClientList(ClientRequestDTO clientRequestDTO, User user) {
        List<Client> clients = new ArrayList<>();
        clients.add(TestUtil.createClient(clientRequestDTO, user));
        return clients;
    }

    public static List<Client> clientsList() {
        List<Client> clients = new ArrayList<>();
        Client client = new Client();
        String firstName = "FirstName";
        client.setFirstName(firstName.toUpperCase());
        client.setLastName("LastName");
        client.setPhone("123123091230");
        client.setEmail("cl@example.com");
        client.setIdNumber(12391283091283L);
        client.setUserId(TestUtil.createUser());
        clients.add(client);
        return clients;
    }

    public static ClientResponseDTO createClientResponseDto(Client client) {
        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
        clientResponseDTO.setFirstName(client.getFirstName());
        clientResponseDTO.setLastName(client.getLastName());
        clientResponseDTO.setPhone(client.getPhone());
        clientResponseDTO.setEmail(client.getEmail());

        return clientResponseDTO;
    }

    public static List<ClientResponseDTO> clientResponseDTOSList(List<Client> clients) {
        return clients.stream().map(ClientResponseDTO::new).collect(Collectors.toList());
    }

    public static Client createUpdatedClient(ClientRequestDTO clientRequestDTO) {
        Client client = new Client();
        client.setFirstName(clientRequestDTO.getFirstName());
        client.setLastName(clientRequestDTO.getLastName());
        client.setPhone(clientRequestDTO.getPhone());
        client.setEmail(clientRequestDTO.getEmail());
        client.setIdNumber(clientRequestDTO.getIdNumber());

        return client;
    }

    public static List<Client> creatSameClientList(Client client, CustomUserDetails loggedUser) {
        List<Client> clients = new ArrayList<>();
        Client newClient = new Client();
        newClient.setId(client.getId());
        newClient.setFirstName(client.getFirstName());
        newClient.setLastName(client.getLastName());
        newClient.setPhone(client.getPhone());
        newClient.setEmail(client.getEmail());
        newClient.setIdNumber(client.getIdNumber());
        User user = TestUtil.createUser();
        user.setId(loggedUser.getId());
        newClient.setUserId(user);
        clients.add(newClient);
        return clients;
    }

    public static List<Client> creatSameClientListNew(Client client, UserRegistrationDetails loggedUser) {
        List<Client> clients = new ArrayList<>();
        Client newClient = new Client();
        newClient.setId(client.getId());
        newClient.setFirstName(client.getFirstName());
        newClient.setLastName(client.getLastName());
        newClient.setPhone(client.getPhone());
        newClient.setEmail(client.getEmail());
        newClient.setIdNumber(client.getIdNumber());
        User user = TestUtil.createUser();
        user.setId(loggedUser.getId());
        newClient.setUserId(user);
        clients.add(newClient);
        return clients;
    }

    public static Client createDifferentClient() {
        Client differentClient = new Client();
        differentClient.setId(2222L);
        differentClient.setFirstName("fname");
        differentClient.setLastName("lname");
        differentClient.setPhone("990909");
        differentClient.setEmail("dfcl@examle.com");
        differentClient.setIdNumber(222231234L);
        User user = TestUtil.createUser();
        differentClient.setUserId(user);

        return differentClient;

    }


    public static VerificationToken createVerificationToken(String token, User user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpirationTime(verificationToken.getTokenExpirationTime());

        return verificationToken;
    }

    public static Date addHoursToJavaUtilDate(Date expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expirationTime);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        return calendar.getTime();
    }

    public static TrialAddRequestDTO createTrialAddRequestDTO() {
        TrialAddRequestDTO trialAddRequestDTO = new TrialAddRequestDTO();
        LocalDateTime dateTime = dateTimeFormatterFromString("2022-12-08 09:42");
        trialAddRequestDTO.setDate(dateTime);
        trialAddRequestDTO.setIssue("issue");
        return trialAddRequestDTO;
    }

    public static Trial creatTrial(TrialAddRequestDTO trialAddRequestDTO, User newLogged, Client client) {
        Trial trial = new Trial(trialAddRequestDTO, newLogged, client);
        return trial;
    }

    private static LocalDateTime dateTimeFormatterFromString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return dateTime;
    }

    public static List<Trial> createTrialList(User user, Client client) {
        List<Trial> trials = new ArrayList<>();
        Trial trial = new Trial();
        LocalDateTime dateTime = dateTimeFormatterFromString("2022-12-08 09:42");
        trial.setDate(dateTime);
        trial.setIssue("issue");
        trial.setClientId(client);
        trial.setUserId(user);
        trial.setId(12334L);
        trials.add(trial);
        return trials;

    }

    public static List<Trial> createTrailsListHourLater(User user, Client client) {
        List<Trial> trials = new ArrayList<>();
        Trial trial = new Trial();
        trial.setDate(LocalDateTime.now().minusHours(1));
        trial.setIssue("issue");
        trial.setClientId(client);
        trial.setUserId(user);
        trial.setId(12334L);
        trials.add(trial);
        return trials;
    }
}
