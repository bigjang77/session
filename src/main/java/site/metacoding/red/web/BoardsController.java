package site.metacoding.red.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.MainDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {

	private final HttpSession session;
	private final BoardsDao boardsDao;
	// @PostMapping("/boards/{id}/delete")
	// @PostMapping("/boards/{id}/update")

	@PostMapping("/boards")//글쓰기
	public String writeBoards(WriteDto writeDto) {
		Users principal = (Users) session.getAttribute("principal");

		if (principal == null) {
			return "redirect:/loginForm";// 인증코드
		}

		boardsDao.insert(writeDto.toEntity(principal.getId()));// 보드스다오는 어떤걸 인서트?사용자로부터 받은값으로 엔티티화해서
		return "redirect:/";// 메인페이지로 리턴, 본코드
	}


	@GetMapping({ "/", "/boards" })//글전체목록보기
	public String getBoardList(Model model) {
		List<MainDto>boardsList = boardsDao.findAll();
		model.addAttribute("boardsList",boardsList);
		return "boards/main";
	}

	@GetMapping("/boards/{id}")//글한개보기
	public String getBoardList(@PathVariable Integer id, Model model) {
		model.addAttribute("boards",boardsDao.findById(id));
		return "boards/detail";
	}

	@GetMapping("/boards/writeForm")//글쓰기전 인증하기
	public String writeForm() {
		Users principal = (Users)session.getAttribute("principal");// 글쓰기 페이지는 권한이 필요없다 인증만 필요하다
		if (principal == null) {
			return "redirect:/loginForm";
		}
		return "boards/writeForm";
	}

}
