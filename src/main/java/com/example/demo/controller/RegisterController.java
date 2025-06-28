package com.example.demo.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.example.demo.entity.User;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@GetMapping("/checkEmail")
	@ResponseBody
	public boolean checkEmail(@RequestParam String email) {
		return !userService.existsByEmail(email);
	}

	@PostMapping(params = "redo")
	public String redoRegister(@ModelAttribute("user") User user) {
		return "register";
	}

	@PostMapping("/confirm")
	public String confirm(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register";
		}
		return "registerConfirm";
	}

	@PostMapping("/complete")
	public String complete(@ModelAttribute("user") @Valid User user,
			BindingResult result,
			RedirectAttributes ra) {
		if (result.hasErrors()) {
			return "registerConfirm";
		}

		if (userService.existsByEmail(user.getEmail())) {
			result.rejectValue("email", "duplicate", "このメールは既に使われています");
			return "registerConfirm";
		}

		try {
			userService.save(user);
		} catch (EmailAlreadyExistsException ex) {
			result.rejectValue("email", "duplicate", ex.getMessage());
			return "registerConfirm";
		}

		ra.addFlashAttribute("user", user);
		return "redirect:/register/complete";
	}

	@GetMapping("/complete")
	public String showComplete(HttpServletRequest request, Model model) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null && flashMap.containsKey("user")) {
			model.addAttribute("user", flashMap.get("user"));
		}
		return "registerComplete";
	}
}
