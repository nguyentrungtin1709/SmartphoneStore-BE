package online.shop.SmartphoneStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import online.shop.SmartphoneStore.entity.Enum.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.net.URI;
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

    @NotBlank
    @Column(name = "ten_tai_khoan", nullable = false, length = 50)
    private String name;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank
    @JsonIgnore
    @Length(min = 6)
    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "role", nullable = false)
    private Role role;

    @Pattern(regexp = "(0[3|5|7|8|9])+([0-9]{8})\\b")
    @Column(name = "so_dien_thoai", nullable = false, length = 10)
    private String phone;

    @Column(name = "hinh_anh")
    private URI imageUrl;

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
