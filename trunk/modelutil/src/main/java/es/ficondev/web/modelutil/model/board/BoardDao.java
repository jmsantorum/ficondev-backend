package es.ficondev.web.modelutil.model.board;

import java.util.Calendar;
import java.util.List;

import es.ficondev.web.modelutil.dao.GenericDao;
import es.ficondev.web.modelutil.exceptions.InstanceNotFoundException;

public interface BoardDao extends GenericDao<Board, Long> {

	Board		findBoardByCode(String code) throws InstanceNotFoundException;
	List<Board>	getAllBoards(Integer startIndex, Integer count);
	int			countAllBoards();
	List<Board>	getAllFreeBoardsByDate(Calendar day, Integer people, Boolean share);
	
}
