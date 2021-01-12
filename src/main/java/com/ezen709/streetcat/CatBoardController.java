package com.ezen709.streetcat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ezen709.streetcat.model.CatBoardCommentDTO;
import com.ezen709.streetcat.model.CatBoardDTO;
import com.ezen709.streetcat.model.CatBoardLikeDTO;
import com.ezen709.streetcat.model.CatDTO;
import com.ezen709.streetcat.service.CatBoardMapper;

@Controller
public class CatBoardController {
	
	@Autowired
	private CatBoardMapper catBoardMapper;
	@Resource(name="uploadPath")
	private String uploadPath;
	
	@RequestMapping("/test_fileUpload.do")
	public String fileUpload(){
		return "testFileUpload";
	}
	@RequestMapping("/test_fileUpload_ok.do")
	public void fileUpload_Ok(HttpServletRequest req){
		String filename="";
		int filesize=0;
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest)req;
		MultipartFile file = mr.getFile("filename");
		File target = new File(uploadPath,file.getOriginalFilename());
		if(file.getSize() > 0) {
			try {
				file.transferTo(target);
				filename = file.getOriginalFilename();
				filesize = (int)file.getSize();
			}catch (IOException e) {
			e.printStackTrace();
			}
		}
		System.out.println("filename = " + filename);
		System.out.println("filensize = " + filesize + "bytes");
	}
	@RequestMapping("/cat_board.do")
	public ModelAndView catBoard(HttpServletRequest req) {
		String pageNum = req.getParameter("pageNum");
		
		if (pageNum == null) {
			pageNum = "1";
		}
		int pageSize = 12;
		int currentPage = Integer.parseInt(pageNum);
		int startRow = pageSize * currentPage - (pageSize - 1);
		int endRow = pageSize * currentPage;
		int count = catBoardMapper.getCount();//boardDAO.getCount();
		if (endRow>count) endRow = count;
		List<CatBoardDTO> listBoard = catBoardMapper.cat_listBoard(startRow, endRow);//boardDAO.listBoard(startRow, endRow);
		int startNum = count - ((currentPage-1) * pageSize);
		int pageBlock = 3;
		int pageCount = count/pageSize + (count%pageSize == 0 ? 0 : 1);
		int startPage = (currentPage - 1)/pageBlock * pageBlock + 1;
		int endPage = startPage + pageBlock - 1;
		if (endPage>pageCount) endPage = pageCount;
		
		ModelAndView mav = new ModelAndView("cat_board");
		mav.addObject("upPath",uploadPath);
		mav.addObject("count", count);
		mav.addObject("startNum", startNum);
		mav.addObject("pageCount", pageCount);
		mav.addObject("startPage", startPage);
		mav.addObject("endPage", endPage);
		mav.addObject("pageBlock", pageBlock);
		mav.addObject("cat_listBoard", listBoard);
		return mav;
	}
	@RequestMapping(value="/cat_board_write.do")
	public String catBoardWrite(HttpServletRequest req) {
		if(req.getParameter("cnum")!=null) {
        int cnum = Integer.parseInt(req.getParameter("cnum"));
        CatDTO getCat = catBoardMapper.getCat(cnum);
        req.setAttribute("getCat", getCat);}
		return "cat_board_write";
	}
	@RequestMapping(value="/cat_board_write_ok.do")
	public String catBoardWriteOk(HttpServletRequest req, @ModelAttribute CatBoardDTO dto, BindingResult result) {
		int filesize=0;
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest)req;
		
		MultipartFile file1 = mr.getFile("image1");
		MultipartFile file2 = mr.getFile("image2");
		MultipartFile file3 = mr.getFile("image3");
		MultipartFile file4 = mr.getFile("image4");
		MultipartFile file5 = mr.getFile("image5");
		String filename1=file1.getOriginalFilename();
		String filename2=file2.getOriginalFilename();
		String filename3=file3.getOriginalFilename();
		String filename4=file4.getOriginalFilename();
		String filename5=file5.getOriginalFilename();
		File target = new File(uploadPath,file1.getOriginalFilename());
		if(file1.getSize() > 0) {
			try {
				file1.transferTo(target);
				filename1 = file1.getOriginalFilename();
				filesize = (int)file1.getSize();
			}catch (IOException e) {
			e.printStackTrace();
			}
		}
		String msg = null;
		String url = null;
		if(mr.getParameter("cnum").equals("")) {
			dto.setCnum(0);	
		}else {
		dto.setCnum(Integer.parseInt(mr.getParameter("cnum")));}
		dto.setImage1(filename1);
		dto.setImage2(filename2);
		dto.setImage3(filename3);
		dto.setImage4(filename4);
		dto.setImage5(filename5);
		dto.setIp(req.getRemoteAddr());
		int res = catBoardMapper.insertBoard(dto);
		if(res>0) {
		    msg = "�۾��� ���� ��� �������� �̵��մϴ�";
			url = "cat_board.do";
		}else {
			msg = "�۾��� ���� ��� �������� �̵��մϴ�";
			url = "cat_board.do";
		}
		req.setAttribute("msg", msg);
		req.setAttribute("url", url);
		return "message";
	}
	
	@RequestMapping(value="/cat_find.do")
	public String catFind() {
		return "cat_find";
	}
	@RequestMapping(value="/cat_find_ok.do")
	public ModelAndView catFindOk(HttpServletRequest req) {
		String location = (String)req.getParameter("location");
		List<CatDTO> listCat = catBoardMapper.cat_list(location);
		ModelAndView mav = new ModelAndView("cat_find");
		mav.addObject("location",location);
		mav.addObject("cat_list",listCat);
		System.out.print(location);
		return mav;
	}
	@RequestMapping(value="/cat_board_content.do")
	public ModelAndView catBoardContent(HttpServletRequest req) {
        String pageNum = req.getParameter("pageNum");
		
		if (pageNum == null) {
			pageNum = "1";
		}
		String type = req.getParameter("type");
		int bnumCount = catBoardMapper.getCount();
		int bnum = Integer.parseInt(req.getParameter("bnum"));
		
		if(type.equals("next")) {
			bnum = catBoardMapper.nextBoard(bnum);
			System.out.print(bnum);
		}else if(type.equals("before")) {
			bnum = catBoardMapper.beforeBoard(bnum);
		}
		int count = catBoardMapper.getCommentCount(bnum);
		ModelAndView mav = new ModelAndView("cat_board_content");
		int pageSize = 12;
		int currentPage = Integer.parseInt(pageNum);
		int startRow = pageSize * currentPage - (pageSize - 1);
		int endRow = pageSize * currentPage;		
		
		CatBoardDTO getBoard = catBoardMapper.getBoard(bnum);
		List<CatBoardCommentDTO> boardComment = catBoardMapper.boardComment(bnum,startRow,endRow);
		if (endRow>count) endRow = count;
		int startNum = count - ((currentPage-1) * pageSize);
		int pageBlock = 3;
		int pageCount = count/pageSize + (count%pageSize == 0 ? 0 : 1);
		int startPage = (currentPage - 1)/pageBlock * pageBlock + 1;
		int endPage = startPage + pageBlock - 1;
		if (endPage>pageCount) endPage = pageCount;
		mav.addObject("upPath",uploadPath);
		mav.addObject("count", count);
		mav.addObject("startNum", startNum);
		mav.addObject("pageCount", pageCount);
		mav.addObject("startPage", startPage);
		mav.addObject("endPage", endPage);
		mav.addObject("pageBlock", pageBlock);
		mav.addObject("getBoard",getBoard);
		mav.addObject("boardComment",boardComment);
		return mav;
	}
	@RequestMapping(value="/cat_board_content_next.do")
	public String catBoardNext(HttpServletRequest req) {
		int bnum = Integer.parseInt(req.getParameter("bnum"));
		catBoardMapper.nextBoard(bnum);
		return "cat_board_content";
	}
	@RequestMapping(value="/cat_board_comment_write.do")
	public String catBoardComentWrite(HttpServletRequest req,@ModelAttribute CatBoardCommentDTO dto, BindingResult result) {
		String msg,url = null;
		if (result.hasErrors()) {
			dto.setComment_num(0);
			dto.setRe_step(0);
			dto.setRe_level(0);
		}
        int bnum = Integer.parseInt(req.getParameter("bnum"));
        dto.setBnum(bnum);
        int res = catBoardMapper.insertComment(dto);
        String type = (String)req.getParameter("type");
		if(res>0) {
		    msg = "��۾��� ���� �ۻ��������� �̵��մϴ�";
		    if(type.equals("popup")) {
		    	url= "popup";
		    }else {
			url = "cat_board_content.do?bnum="+bnum;}
		}else {
			msg = "��۾��� ���� �ۻ� �������� �̵��մϴ�";
			if(type.equals("popup")) {
		    	url= "popup";
		    }else {
			url = "cat_board_content.do?bnum="+bnum;}
		}
		System.out.print(type);
		req.setAttribute("msg", msg);
		req.setAttribute("url", url);
		return "message";
	}
	@RequestMapping(value="/reComment.do")
	public String reComment(HttpServletRequest req) {
		int num = Integer.parseInt(req.getParameter("comment_num"));
		CatBoardCommentDTO getComment = catBoardMapper.getComment(num);
		req.setAttribute("getComment", getComment);
		return "reComment";
	}
	@RequestMapping(value="/cat_board_like.do")
	public String catBoardLike(HttpServletRequest req,@ModelAttribute CatBoardLikeDTO dto) {
		String msg,url = null;
        int bnum = Integer.parseInt(req.getParameter("bnum"));
        String id = (String)req.getParameter("id");
        int res = catBoardMapper.boardLike(dto);
        req.setAttribute("bnum", bnum);
		return "cat_board_content";
	}
	@RequestMapping(value="/cat_board_unLike.do")
	public String catBoardUnLike(HttpServletRequest req) {
		String msg,url = null;
        int bnum = Integer.parseInt(req.getParameter("bnum"));
        int res = catBoardMapper.boardUnLike(bnum);
        req.setAttribute("bnum", bnum);
		return "cat_board_content";
	}
	@RequestMapping(value="/cat_board_delete.do")
	public String catBoardDelete(HttpServletRequest req) {
		String msg,url = null;
		int bnum = Integer.parseInt(req.getParameter("bnum"));
		int res =catBoardMapper.boardDelete(bnum);
		if(res>0) {
		    msg = "�ۻ��� ���� �Խ����������� �̵��մϴ�";
			url = "cat_board_content.do?bnum="+bnum;
		}else {
			msg = "�ۻ��� ���� �Խ����������� �̵��մϴ�";
			url = "cat_board_content.do?bnum="+bnum;
		}
		req.setAttribute("msg", msg);
		req.setAttribute("url", url);
		return "message";
	}
	@RequestMapping(value="/cat_board_edit.do")
	public String catBoardEdit(HttpServletRequest req) {
		int bnum = Integer.parseInt(req.getParameter("bnum"));
		CatBoardDTO getBoard = catBoardMapper.getBoard(bnum);
		req.setAttribute("bnum", bnum);
		req.setAttribute("getBoard", getBoard);
		return "cat_board_edit";
	}
}
