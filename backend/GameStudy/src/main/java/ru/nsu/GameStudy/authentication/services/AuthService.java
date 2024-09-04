package ru.nsu.GameStudy.authentication.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.users.dto.StudentDTO;
import ru.nsu.GameStudy.users.dto.StudentRequest;
import ru.nsu.GameStudy.users.dto.TeacherDTO;
import ru.nsu.GameStudy.authentication.dto.UserDTO;
import ru.nsu.GameStudy.mappers.UserMapper;
import ru.nsu.GameStudy.exceptions.UserAlreadyExistsException;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.authentication.models.Token;
import ru.nsu.GameStudy.authentication.models.User;
import ru.nsu.GameStudy.authentication.repositories.TokenRepository;
import ru.nsu.GameStudy.authentication.dto.requests.LoginRequest;
import ru.nsu.GameStudy.authentication.dto.requests.RegisterRequest;
import ru.nsu.GameStudy.authentication.dto.response.AuthResponse;
import ru.nsu.GameStudy.users.dto.TeacherRequest;
import ru.nsu.GameStudy.users.services.StudentService;
import ru.nsu.GameStudy.users.services.TeacherService;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @Transactional
    public AuthResponse register(RegisterRequest request)
            throws UserAlreadyExistsException, ValueAlreadyExistsException, ParseException {
        log.info("register user with name: {}, surname: {}, fathers_name: {}, " +
                "email: {}, phoneNumber: {}", request.getName(), request.getSurname(),
                request.getFathersName(), request.getEmail(), request.getPhoneNumber());
        // check if user already exist. if exist than authenticate the user
        if (userService.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        UserDTO userDTO = UserDTO.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .fathersName(request.getFathersName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userDTO = userService.createUser(userDTO);

        String accessToken = jwtService.generateAccessToken(userDTO);
        String refreshToken = jwtService.generateRefreshToken(userDTO);
        saveUserToken(accessToken, refreshToken, userDTO);

        switch (userDTO.getRole()) {
            case STUDENT -> {
                StudentRequest studentDTO = StudentRequest.builder()
                        .user(userDTO.getId())
                        .build();
                studentService.createStudent(studentDTO);
                break;
            }
            case TEACHER -> {
                TeacherRequest teacherDTO = TeacherRequest.builder()
                        .user(userDTO.getId())
                        .build();
                teacherService.createTeacher(teacherDTO);
                break;
            }
        }

        //userDTO = userService.getUserByEmail(userDTO.getEmail());

        return new AuthResponse(accessToken, refreshToken, userDTO);

    }

    public AuthResponse auth(LoginRequest request) {
        log.info("auth user with email: {}", request.email());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken (
                        request.email(),
                        request.password()
                )
        );

        UserDTO user = userService.getUserByEmail(request.email());
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(accessToken, refreshToken, user);

        return new AuthResponse(accessToken, refreshToken, user);

    }

    public UserDTO authWithToken(String inputToken) {
        log.info("GET user by token");

        Optional<Token> token = tokenRepository.findByAccessToken(inputToken);
        if (token.isEmpty()) {
            throw new NotFoundException("no user found by this token");
        }
        User user = token.get().getUser();

        return userMapper.toDTO(user);
    }

    private void revokeAllTokenByUser(UserDTO user) {
        log.info("revokeAllTokenByUser {}", user.getEmail());
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, UserDTO user) {
        log.info("SAVE user token, user: {}", user.getEmail());
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(userService.getUser(user.getId()));
        tokenRepository.save(token);
    }

    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        // extract the token from authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        // extract email from token
        String email = jwtService.extractUsername(token);

        // check if the user exist in database
        UserDTO user = userService.getUserByEmail(email);

        // check if the token is valid
        if(jwtService.isValidRefreshToken(token, user)) {
            // generate access token
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity(new AuthResponse(accessToken, refreshToken,
                    user), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }
}
