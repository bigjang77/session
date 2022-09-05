package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;

@RequiredArgsConstructor
@Controller
public class BoardsController {

	private final HttpSession session;
	// @PostMapping("/boards/{id}/delete")
	// @PostMapping("/boards/{id}/update")

	@GetMapping({ "/", "/boards" })
	public String getBoardList() {
		return "boards/main";
	}

	@GetMapping("/boards/{id}")
	public String getBoardList(@PathVariable Integer id) {
		return "boards/detail";
	}

	@GetMapping("/boards/writeForm")
	public String writeForm() {
		Users principal = (Users) session.getAttribute("principal");//글쓰기 페이지는 권한이 필요없다 인증만 필요하다
		if (principal == null) {
			return "redirect:/loginForm";
		} else {
			return "boards/writeForm";
		}

	}
}
