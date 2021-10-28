package com.ru.questiondiary.service;

import com.ru.questiondiary.exception.AuthoritiesGrantedException;
import com.ru.questiondiary.exception.DuplicateUserEmailException;
import com.ru.questiondiary.exception.TokenValidationException;
import com.ru.questiondiary.exception.UserNotFoundException;
import com.ru.questiondiary.repo.CommunityRepository;
import com.ru.questiondiary.repo.UserRepository;
import com.ru.questiondiary.web.dto.CommunityDto;
import com.ru.questiondiary.web.dto.UserDto;
import com.ru.questiondiary.web.dto.UserLoginDto;
import com.ru.questiondiary.web.dto.request.RegisterRequest;
import com.ru.questiondiary.web.entity.Community;
import com.ru.questiondiary.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CommunityRepository communityRepository;
    private final TokenService tokenService;
    private final ImageService imageService;

    @Override
    @Transactional
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user: users) {
            userDtos.add(UserDto.from(user));
        }
        return userDtos;
    }

    @Override
    @Transactional
    public UserLoginDto findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with Email: %s not found", email));
        }
        String token = tokenService.createToken(user.get());
        return UserLoginDto.from(user.get(), token);
    }

    @Override
    @Transactional
    public UserLoginDto register(RegisterRequest request) throws DuplicateUserEmailException {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new DuplicateUserEmailException(String.format("User with email %s already exists. Consider logging in.", request.getEmail()));
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(hashedPassword)
                .role("USER")
                .isApproved(false)
                .avatar("https://i.ibb.co/5Bt6Q0t/Default-avatar.png")
                .answers(new ArrayList<>())
                .following(new ArrayList<>())
                .followers(new ArrayList<>())
                .build();
        userRepository.save(user);
        String token = tokenService.createToken(user);
        return UserLoginDto.from(user, token);
    }

    @Override
    @Transactional
    public UserDto findUserById(Long id, String rawToken) {
        User requester = getUserFromToken(rawToken);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID [%s] not found", id));
        }
        Boolean isFollowed = user.get().getFollowers().contains(requester);
        return UserDto.from(user.get(), isFollowed);
    }

    @Override
    @Transactional
    public UserDto findUserByToken(String token) {
        User user = getUserFromToken(token);
        return UserDto.from(user);
    }

    @Override
    @Transactional
    public void followUser(Long userId, String rawToken) {
        User user = getUserFromToken(rawToken);
        Optional<User> userToFollow = userRepository.findById(userId);
        if (userToFollow.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID [%s] not found", userId));
        }
        user.followOtherUser(userToFollow.get());
        userToFollow.get().addFollower(user);
        userRepository.save(userToFollow.get());
        userRepository.save(user);
    }

    @Override
    public List<CommunityDto> findLastFourSubscriptions(String rawToken) {
        User user = getUserFromToken(rawToken);
        List<Community> communities = communityRepository.findLastFourSubscriptions(user.getId());
        List<CommunityDto> communityDtos = new ArrayList<>();
        for (Community community: communities) {
            communityDtos.add(CommunityDto.from(community));
        }
        return communityDtos;
    }

    @Override
    public UserDto approveUser(Long userId, String rawToken) {
        User user = getUserFromToken(rawToken);
        if (user.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AuthoritiesGrantedException("You have no rights to update any user");
        }
        Optional<User> userToApprove = userRepository.findById(userId);
        if (userToApprove.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID [%s] not found", userId));
        }
        userToApprove.get().setIsApproved(true);
        userRepository.save(userToApprove.get());
        return UserDto.from(userToApprove.get());
    }

    @Override
    public UserDto uploadAvatar(MultipartFile image, String rawToken) throws IOException {
        User user = getUserFromToken(rawToken);
        String newAvatarURL = imageService.uploadImage(image);
        user.setAvatar(newAvatarURL);
        userRepository.save(user);
        return UserDto.from(user);
    }

    @Override
    public UserLoginDto createFromGoogle(Map<String, Object> attributes) {
        String email = attributes.get("email").toString();
        String firstName = attributes.get("given_name").toString();
        String lastName = attributes.get("family_name").toString();
        String avatar = attributes.get("picture").toString();
        String password = passwordEncoder.encode(email);
        User user = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .avatar(avatar)
                .password(password)
                .role("USER")
                .isApproved(false)
                .followers(new ArrayList<>())
                .following(new ArrayList<>())
                .answers(new ArrayList<>())
                .createdQuestions(new ArrayList<>())
                .favoriteQuestions(new ArrayList<>())
                .followingCommunities(new ArrayList<>())
                .build();
        User savedUser = userRepository.save(user);
        String token = tokenService.createToken(savedUser);
        return UserLoginDto.from(savedUser, token);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", email)));
    }

    private User getUserFromToken(String token) {
        Map<String, String> userData = tokenService.getUserDataFromToken(token);
        if (userData == null || userData.isEmpty()) {
            throw new TokenValidationException("Invalid token");
        }
        Optional<User> user = userRepository.findById(Long.parseLong(userData.get("id")));
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("Token [%s] has no linked user", token));
        }
        return user.get();
    }
}
