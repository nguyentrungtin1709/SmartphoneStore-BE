package online.shop.SmartphoneStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import online.shop.SmartphoneStore.entity.Enum.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "tai_khoan",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_email",
                        columnNames = "email"
                ),
                @UniqueConstraint(
                        name = "unique_phone",
                        columnNames = "so_dien_thoai"
                )
        }
)
public class Account implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "ma_tai_khoan")
    private Long id;

    @NotNull
    @NotBlank
    @Length(max = 100)
    @Column(name = "ten_tai_khoan")
    private String name;

    @Email
    @NotNull
    @NotBlank
    @Column(name = "email")
    private String email;

    @NotNull
    @NotBlank
    @Length(min = 6)
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @NotNull
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "role")
    @JsonIgnore
    private Role role;

    @NotNull
    @NotBlank
    @Length(max = 10)
    @Column(name = "so_dien_thoai")
    private String phone;

    @CreationTimestamp
    @Column(name = "ngay_tao")
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Address> addressList;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Order> orderList;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(
                        role.name()
                )
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
