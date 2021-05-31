package com.se.dungcuthethao.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.dungcuthethao.entity.KhachHang;
import com.se.dungcuthethao.entity.Role;
import com.se.dungcuthethao.entity.TaiKhoan;
import com.se.dungcuthethao.entity.enumEntity.RoleEnum;
import com.se.dungcuthethao.jwt.JwtUtils;
import com.se.dungcuthethao.jwt.request.LoginRequest;
import com.se.dungcuthethao.jwt.request.PasswordChangeRequest;
import com.se.dungcuthethao.jwt.request.SignupRequest;
import com.se.dungcuthethao.jwt.response.JwtResponse;
import com.se.dungcuthethao.jwt.response.MessageResponse;
import com.se.dungcuthethao.service.RoleService;
import com.se.dungcuthethao.service.TaiKhoanService;
import com.se.dungcuthethao.service.impl.UserDetailsImpl;

/**
 * Quản lý đăng ký và đăng nhập tài khoản người dùng
 * 
 * @author Lại Văn Vượng
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RoleService roleService;

	@Autowired
	private TaiKhoanService taiKhoanService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			TaiKhoan taiKhoan = userDetails.getTaiKhoan();
			KhachHang khachHang = userDetails.getTaiKhoan().getKhachHang();
			
			return ResponseEntity.ok(new JwtResponse(jwt, khachHang, taiKhoan));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Tài khoản hoặc mật khẩu không chính xác!"));
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		// nếu tài khoản tồn tại
		if (taiKhoanService.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Tài khoản đã tồn tại!"));
		}

		// Tạo tài khoản
		TaiKhoan taiKhoan = new TaiKhoan();
		taiKhoan.setUsername(signUpRequest.getUsername());
		taiKhoan.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		taiKhoan.setKhachHang(new KhachHang(taiKhoan)); // tạo khách hàng với thông tin mặc định sau khi tạo tài khoản
		Set<Role> roles = new HashSet<>();
		Role role = roleService.findByRole(RoleEnum.ROLE_CUSTOMER); // tạo tài khoản với role mặc định là CUSTOMER
		roles.add(role);
		taiKhoan.setRoles(roles);
		taiKhoanService.save(taiKhoan);

		return ResponseEntity.ok(new MessageResponse("Đăng ký thành công!"));
	}

	@PreAuthorize("hasRole('CUSTOMER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PutMapping("/changePassword/{id}")
	public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody PasswordChangeRequest passwordChangeRequest) {
		TaiKhoan taiKhoan = taiKhoanService.findById(id);
		if (taiKhoan == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Tài khoản không tồn tại!"));
		} else {
			taiKhoan.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
			taiKhoanService.save(taiKhoan);
			return ResponseEntity.ok(new MessageResponse("Đổi mật khẩu thành công!"));
		}
	}

	/**
	 * update role => ROLE_MODERATOR
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/up-role/{id}")
	public ResponseEntity<?> upRole(@PathVariable Long id) {
		// tìm tài khoản cần tăng role => ROLE_MODERATOR
		TaiKhoan taiKhoan = taiKhoanService.findById(id);
		if (taiKhoan == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Tài khoản không tồn tại!"));
		} else {
			Set<Role> roles = new HashSet<>();
			Role role = roleService.findByRole(RoleEnum.ROLE_MODERATOR);
			roles.add(role);
			taiKhoan.setRoles(roles);
			taiKhoanService.save(taiKhoan);
			return ResponseEntity.ok(new MessageResponse(taiKhoan.getUsername() + " đã trở thành MODERATOR!"));
		}
	}

	/**
	 * update role => ROLE_CUSTOMER
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/down-role/{id}")
	public ResponseEntity<?> downRole(@PathVariable Long id) {
		// tìm tài khoản cần giảm role => ROLE_CUSTOMER
		TaiKhoan taiKhoan = taiKhoanService.findById(id);
		if (taiKhoan == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Tài khoản không tồn tại!"));
		} else {
			Set<Role> roles = new HashSet<>();
			Role role = roleService.findByRole(RoleEnum.ROLE_CUSTOMER);
			roles.add(role);
			taiKhoan.setRoles(roles);
			taiKhoanService.save(taiKhoan);
			return ResponseEntity.ok(new MessageResponse(taiKhoan.getUsername() + " đã trở thành CUSTOMER!"));
		}
	}

}
