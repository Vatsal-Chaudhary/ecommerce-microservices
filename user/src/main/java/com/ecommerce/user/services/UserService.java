package com.ecommerce.user.services;

import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.models.Address;
import com.ecommerce.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final KeyCloakAdminService keyCloakAdminService;

    public List<UserResponse> fetchAllUsers() {
        return userRepo.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest) {
        String token = keyCloakAdminService.getAdminAccessToken();
        System.out.println(token);
        String keycloakUserId = keyCloakAdminService.createUser(token, userRequest);
        User user = new User();
        mapFromUserRequest(user, userRequest);
        user.setKeycloakId(keycloakUserId);
        keyCloakAdminService.assignRealmRoleToUser(userRequest.getUsername(), "USER", keycloakUserId);
        userRepo.save(user);
    }

    public Optional<UserResponse> getUserById(String id) {
        return userRepo.findById(id)
                .map(this::mapToUserResponse);
    }

    public Optional<String> updateUser(String id, UserRequest updatedUserRequest) {
        return userRepo.findById(id)
                .map(existingUser -> {
                    mapFromUserRequest(existingUser, updatedUserRequest);
                    userRepo.save(existingUser);
                    return "User updated successfully";
                });
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setKeycloakId(user.getKeycloakId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            userResponse.setAddress(addressDTO);
        }

        return userResponse;
    }

    private void mapFromUserRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null) {
            AddressDTO addressDTO = userRequest.getAddress();
            Address address = new Address();
            address.setStreet(addressDTO.getStreet());
            address.setCity(addressDTO.getCity());
            address.setState(addressDTO.getState());
            address.setCountry(addressDTO.getCountry());
            address.setZipcode(addressDTO.getZipcode());
            user.setAddress(address);
        }
    }
}
