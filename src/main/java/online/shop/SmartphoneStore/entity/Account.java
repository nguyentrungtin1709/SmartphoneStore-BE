package online.shop.SmartphoneStore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
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
    @NotBlank(message = "Không được bỏ trống")
    @Length(max = 100)
    @Column(name = "ten_tai_khoan")
    private String name;


    @NotNull
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Không được bỏ trống")
    @Column(name = "email")
    private String email;

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Length(min = 6, message = "Mật khẩu cần có ít nhất 6 kí tự")
    @Column(name = "password")
    private String password;

    @NotNull
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "role")
    private Role role;

    @NotNull
    @NotBlank(message = "Không được bỏ trống")
    @Length(max = 10)
    @Column(name = "so_dien_thoai")
    private String phone;

    @NotNull
    @CreationTimestamp
    @Column(name = "ngay_tao")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "account")
    private List<Address> addressList;

    @OneToMany(mappedBy = "account")
    private List<Order> orderList;

    @Override
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
