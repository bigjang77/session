package site.metacoding.red.web.dto.response.boards;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class MainDto {
	private Integer id;
	private String title;
	private String username;
	private PagingDto paging;
}
