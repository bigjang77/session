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
import site.metacoding.red.web.dto.response.boards.PagingDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {

	private final HttpSession session;
	private final BoardsDao boardsDao;
	// @PostMapping("/boards/{id}/delete")
	// @PostMapping("/boards/{id}/update")

	@PostMapping("/boards/{id}/delete") // 글지우기 ,
	public String deleteBoards(@PathVariable Integer id) {
		Boards boardsPs = boardsDao.findById(id);// 영속화하는이유 트랜직션때문에
		//비정상 요청 체크
		if (boardsPs == null) { // if는 비정상 로직을 타게해서 걸러내는 필터 역할을 하는게 좋다
			return "redirect:/boards/" + id;
		}

		// 인증 체크
		Users principal = (Users) session.getAttribute("principal");
		if (principal == null) {
			return "redirect:/loginForm";
		}
		//권한 체크(세션princiapal,getId() 와 boardsPs의 userId를 비교
		if (principal.getId() != boardsPs.getUsersId()) {
			return "redirect:/boards/" + id;
		}

		boardsDao.delete(id);
		return "redirect:/";
	}

	@PostMapping("/boards") // 글쓰기
	public String writeBoards(WriteDto writeDto) {
		Users principal = (Users) session.getAttribute("principal");

		if (principal == null) {
			return "redirect:/loginForm";// 인증코드
		}

		boardsDao.insert(writeDto.toEntity(principal.getId()));// 보드스다오는 어떤걸 인서트?사용자로부터 받은값으로 엔티티화해서
		return "redirect:/";// 메인페이지로 리턴, 본코드
	}

	// http://locathost:8000/
	// http://localhost:8000/?page=0
	@GetMapping({ "/", "/boards" }) // 글전체목록보기
	public String getBoardList(Model model, Integer page) {// 0->, 1->10, 2->20
		if (page == null)
			page = 0;
		int startNum = page * 3;

		List<MainDto> boardsList = boardsDao.findAll(startNum);
		PagingDto paging = boardsDao.paging(page);

		paging.makeBlockInfo();

		model.addAttribute("boardsList", boardsList);
		model.addAttribute("paging", paging);
		return "boards/main";
	}

	@GetMapping("/boards/{id}") // 글한개보기
	public String getBoardDetail(@PathVariable Integer id, Model model) {
		model.addAttribute("boards", boardsDao.findById(id));
		return "boards/detail";
	}

	@GetMapping("/boards/writeForm") // 글쓰기전 인증하기
	public String writeForm() {
		Users principal = (Users) session.getAttribute("principal");// 글쓰기 페이지는 권한이 필요없다 인증만 필요하다
		if (principal == null) {
			return "redirect:/loginForm";
		}
		return "boards/writeForm";
	}

}
