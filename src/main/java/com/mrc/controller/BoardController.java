package com.mrc.controller;

import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.board.BoardCond;
import com.mrc.db.dto.board.Board;
import com.mrc.db.dto.board.BoardRead;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.file.File;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardController extends BaseController {
	@ResponseBody
	@PostMapping("/board/getlist")
	public List<Board> getBoardList(@RequestBody BoardCond cond) {
		try {
			if (loginInfo.getIsLogin() == true)
			cond.setMemberCode(loginInfo.getMember().getMemberCode());
		} catch (Exception ex) {
			System.out.println("getBoardList >> " + ex.getMessage());
			cond.setMemberCode(null);
		}
		List<Board> rtn = GlobalDao.getBoardDao().getList(cond);
		return rtn;
	}

	@ResponseBody
	@PostMapping("/board/save")
	public ResultData boardSave(@RequestBody Board data) {
		if (loginInfo.getIsLogin() == true)
			data.setInsertCode(loginInfo.getMember().getMemberCode());
		else
			data.setInsertCode(1);
		return GlobalDao.getBoardDao().save(data);
	}
	@ResponseBody
	@PostMapping("/board/addFile")
	public ResultData addFile(@RequestBody File data) {
		data.setTableName("t_board");
		data.setTableName("t_board");
		if (loginInfo.getIsLogin() == true)
			data.setInsertCode(loginInfo.getMember().getMemberCode());
		else
			data.setInsertCode(1);
		return GlobalDao.getFileDao().save(data);
	}

	@ResponseBody
	@PostMapping("/board/read")
	public ResultData boardread(@RequestBody BoardRead data) {

		if (loginInfo.getIsLogin() == true)
			data.setInsertCode(loginInfo.getMember().getMemberCode());
		else
			data.setInsertCode(1);
		return GlobalDao.getBoardDao().readUpdate(data);
	}

	@ResponseBody
	@PostMapping("/board/delete")
	public ResultData boardDelete(@RequestBody Board data) {

		return GlobalDao.getBoardDao().delete(data);
	}
}
