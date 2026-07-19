package com.bank.service.impl.profile;
import com.bank.dto.profile.UpdateProfileRequest;
import com.bank.dto.profile.ProfileResponse;
import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import com.bank.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final CustomerRepository customerRepository;

    @Override
    public ProfileResponse getProfile() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Customer customer = customerRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return ProfileResponse.builder()
                .customerNumber(customer.getCustomerNumber())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .city(customer.getCity())
                .state(customer.getState())
                .pincode(customer.getPincode())
                .kycVerified(customer.getKycVerified())
                .build();
    }
    @Override
    public ProfileResponse updateProfile(UpdateProfileRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Customer customer = customerRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setPincode(request.getPincode());

        customerRepository.save(customer);

        ProfileResponse response = new ProfileResponse();

        response.setCustomerNumber(customer.getCustomerNumber());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setPhone(customer.getPhone());
        response.setAddress(customer.getAddress());
        response.setCity(customer.getCity());
        response.setState(customer.getState());
        response.setPincode(customer.getPincode());
        response.setKycVerified(customer.getKycVerified());

        return response;
    }
}