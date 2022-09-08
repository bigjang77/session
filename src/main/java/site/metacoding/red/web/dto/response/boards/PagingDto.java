package site.metacoding.red.web.dto.response.boards;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagingDto {
	private Integer currentBlock;// 변수
	private Integer blockCount;//상수 한블록에 페이지 넘버개수(5) 1-5, 6-10 
	private Integer startPageNum;//변수 1 ->6 -> 11
	private Integer lastPageNum;//변수 5 ->10 -> 15
	private Integer startNum; //0 10 20
	private Integer totalCount; //23 23 23
	private Integer totalPage;//25/10 한페이지당 개수 , 3 3 3
	private Integer currentPage;// 0 1 2
	private boolean isLast; //false false true 불리언규칙 is(변수) 너 ~니? getter가 만들어지면 isLast() 이름으로 만들어짐. -> el에서는 last로 찾음
	private boolean isFirst; //true false false ->el에서는 firstst로 찾음
	private String keyword;
	
	public void makeBlockInfo(String keyword) {
		this.keyword= keyword;
		this.blockCount = 5;
		this.currentBlock = currentPage/blockCount;
		this.startPageNum = 1 + blockCount * currentBlock;
		this.lastPageNum = 5 + blockCount * currentBlock; // =startPageNum + blockCount -1
		
		if (totalPage < lastPageNum) {
			lastPageNum = totalPage;
		}
	}
}
