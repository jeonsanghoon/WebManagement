package com.mrc.db.mapper;

import com.mrc.db.dto.board.BoardCond;
import com.mrc.db.dto.board.Board;
import com.mrc.db.dto.board.BoardRead;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
	List<Board> getList(BoardCond cond);
	void save(Board data);
	void delete(Board data);
	void update(Board data);
	List<BoardRead> getReadList(BoardRead cond);
	void read_update(BoardRead data);
}
