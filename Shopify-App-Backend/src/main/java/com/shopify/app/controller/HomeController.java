package com.shopify.app.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.app.dto.AccessTokenDto;
import com.shopify.app.dto.ErrorDto;
import com.shopify.app.dto.Login;
import com.shopify.app.dto.RestResponse;
import com.shopify.app.entities.User;
import com.shopify.app.repository.UserRepository;
import com.shopify.app.security.jwt.JwtUtils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@CrossOrigin("http://localhost:3001")
@Controller
@Slf4j
public class HomeController {
	Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	public final WebClient client = WebClient.create("https://lucenttrainingstore.myshopify.com/");

	@Value("${shopify.app.client.id}")
	private String id;

	@Value("${shopify.app.client.secret}")
	private String secret;

	@Value("${shopify.app.scopes}")
	private String scopes;

	@Value("${shopify.oauth.callback.uri}")
	private String redirect_uri;

//	@Value("${shopify.app.shop}")
//	private String shop;

	@Value("${shopify.app.nonce}")
	private String nonce;

	@Value("${shopify.app.access_mode}")
	private String access_mode;

	String shop = null;

	AccessTokenDto accessTokenDto = new AccessTokenDto();

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	User builder = new User();

	@PostMapping("/login")
	public RedirectView handleLogin(@ModelAttribute Login login) {
		log.info("Inside handleLogin HomeController" + login);
		shop = login.getShopname();
		String redirectUrl = "https://" + shop + ".myshopify.com/admin/oauth/authorize?client_id=" + id + "&scope="
				+ scopes + "&redirect_uri=" + redirect_uri + "&state=" + nonce + "&grant_options[]=" + access_mode;

		return new RedirectView(redirectUrl);

	}// end of handleLogin

//	@PostMapping("/webhook/")
//	public void products(@RequestBody Object requestBody) {
//
//		System.out.println(requestBody);
//	}

	@GetMapping("/api/auth")
	public RedirectView redirect(@RequestParam Map<String, String> params, Model model) {
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9\\-]*\\.myshopify\\.com$");
		Matcher matcher = pattern.matcher(params.get("shop"));
		log.info("Inside api auth HomeController");
		if (params.get("state").equals(nonce) && matcher.matches()) {

			log.info("check the condition");
			WebClient webClient = WebClient.builder().baseUrl("https://" + shop + ".myshopify.com").build();
			Map requestBody = new HashMap<>();
			requestBody.put("client_id", id);
			requestBody.put("client_secret", secret);
			requestBody.put("code", params.get("code"));
			// Send request to Shopify to exchange the authorization code for a permanent
			// access token
			String accessToken = webClient.post().uri("/admin/oauth/access_token")
					.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(requestBody)).retrieve()
					.bodyToMono(JsonNode.class).block().get("access_token").asText();

			accessTokenDto.setAccessToken(accessToken);
			log.info("Access Token !! " + accessToken);

			return new RedirectView("http://localhost:3001/home");
		}
		ErrorDto errorDto = new ErrorDto();
		model.addAttribute("error", errorDto);
		return new RedirectView("error");
	}

	@GetMapping("/token")
	@CrossOrigin(origins = "http://localhost:3001")
	public ResponseEntity<RestResponse> generateToken() throws JsonProcessingException {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("admin/api/2023-01/shop.json");
		log.info("Inside the generatetoken in HomeController");

		Mono<String> res = client.get().uri(builder.toUriString())
				.header("X-Shopify-Access-Token", accessTokenDto.getAccessToken()).retrieve().bodyToMono(String.class);

		log.info(res.block());

		ObjectMapper objectMapper = new ObjectMapper();
		String str = res.block();
		JsonNode jsonNode;
		try {
			jsonNode = objectMapper.readTree(str);
			String shop_id = String.valueOf(jsonNode.get("shop").get("id"));
			String shopName = String.valueOf(jsonNode.get("shop").get("name"));
			User user = User.builder().shopName("lucenttrainingstore").accessToken(accessTokenDto.getAccessToken())
					.password(encoder.encode(accessTokenDto.getAccessToken())).shopId(shop_id).build();

			User savedUserEntity = userRepository.save(user);

			log.info("Saved User Entity: " + savedUserEntity);

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken("lucenttrainingstore", accessTokenDto.getAccessToken()));

			if (authentication.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwtToken = jwtUtils.generateJwtToken(authentication);

				// UserDetailsImpl userDetails = (UserDetailsImpl)
				// authentication.getPrincipal();
				log.info("Authenticated : " + jwtToken);
				RestResponse<Object> restResponse = RestResponse.builder().isError(Boolean.FALSE).statusCode(200)
						.data(jwtToken).build();
				return ResponseEntity.ok(restResponse);
			}
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

//	@PostMapping("/handler")
//	public ResponseEntity<String> handleWebhook(@RequestBody String payload) {
//		// Parse the incoming payload and perform some business logic
//		String response = "Webhook received successfully";
//		return ResponseEntity.ok(response);
//	}
}
