package com.shopify.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.shopify.app.entities.User;
import com.shopify.app.repository.UserRepository;





@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String shopName) throws UsernameNotFoundException {
		User user = userRepository.findByShopName(shopName)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + shopName));

		return new UserDetailsImpl(user);
	}

}
