package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.request.boards.WriteDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {

	private final HttpSession session;
	private final BoardsDao boardsDao;
	// @PostMapping("/boards/{id}/delete")
	// @PostMapping("/boards/{id}/update")

	@PostMapping("/boards")
	public String writeBoards(WriteDto writeDto) {
		//1번 세션에 접근해서 세션값을 확인한다.그때 Users로 다운캐스팅한다. 키값은 principal로 한다.
		
		//2번 principal null인지 확인하고 null이면 loginForm 리다이렉션해준다.
		
		//3번 BoardsDao에 접근해서 insert 메서드를 호출한다.
		//조건:dto를 entity로 변환해서 인수로 담아준다.
		//조건:entity에는 세션의 principal에 getid가 필요하다.
		
		Users principal = (Users) session.getAttribute("principal");

		if (principal == null) {
			return "redirect:/loginForm";// 인증코드
		}

		boardsDao.insert(writeDto.toEntity(principal.getId()));// 보드스다오는 어떤걸 인서트?사용자로부터 받은값으로 엔티티화해서
		return "redirect:/";// 메인페이지로 리턴, 본코드
	}

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
		Users principal = (Users) session.getAttribute("principal");// 글쓰기 페이지는 권한이 필요없다 인증만 필요하다
		if (principal == null) {
			return "redirect:/loginForm";
		}
		return "boards/writeForm";
	}

}
