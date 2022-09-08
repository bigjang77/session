package site.metacoding.red.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;

@RequiredArgsConstructor
@Controller
public class UsersController {
	
	private final HttpSession session;//스프링이 서버시작시에 IoC컨테이너에 보관함
	private final UsersDao usersDao;

	
	@PostMapping("/users/{id}/delete")
	public String delete(@PathVariable Integer id) {
		session.invalidate();
		return "redirect:/";
	}
	
	@PostMapping("/users/{id}/update")
	
	@GetMapping("/users/{id}/updateForm")
	public String updateForm(@PathVariable Integer id, Model model) {
		Users UsersPS = usersDao.findById(id);
		Users principal = (Users)session.getAttribute("principal");
		
		if(UsersPS == null) {
			return "badPage";
		}
		
		return "users/updateForm";
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
	
	@PostMapping("/login") // 로그인만 예외로 select인데 post로 함.
	public String login(LoginDto loginDto) {
		Users usersPS = usersDao.login(loginDto);
		if (usersPS != null) {// 인증됨
			session.setAttribute("principal", usersPS);//principal = 인증된유저(일반적인 약속)
			return "redirect:/";
		} else {// 인증안됨
			return "redirect:/loginForm";
		}
	}

	@PostMapping("/join")
	public String join(JoinDto joinDto) {
		usersDao.insert(joinDto);
		return "redirect:/loginForm";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "users/loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "users/joinForm";
	}

}
