package site.metacoding.red.web.dto.response.boards;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasingDto {
	private Integer startNum; //0 10 20
	private Integer totalCount; //23 23 23
	private Integer totalPage;//25/10 한페이지당 개수 , 3 3 3
	private Integer currentPage;// 0 1 2
	private boolean isLast; //false false true
	private boolean isFirst; //true false false
}
