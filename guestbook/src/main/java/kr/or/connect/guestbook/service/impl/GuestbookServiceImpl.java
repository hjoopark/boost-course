package kr.or.connect.guestbook.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.guestbook.dao.GuestbookDao;
import kr.or.connect.guestbook.dao.LogDao;
import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.dto.Log;
import kr.or.connect.guestbook.service.GuestbookService;

@Service		// 여기가 서비스 레이어구나!
public class GuestbookServiceImpl implements GuestbookService {
	@Autowired
	GuestbookDao guestbookDao;
	
	@Autowired
	LogDao logDao;

	@Override
	@Transactional	//읽기만 할 때 사용하는 어노테이션
	public List<Guestbook> getGuestbooks(Integer start) {
		List<Guestbook> list = guestbookDao.selectAll(start, GuestbookService.LIMIT);
		return list;
	}

	@Override
	@Transactional(readOnly = false)	//readonly를 false로 둔다!
	public int deleteGuestbook(Long id, String ip) {
		int deleteCount = guestbookDao.deleteById(id);
		Log log = new Log();
		log.setIp(ip);
		log.setMethod("delete");
		log.setRegdate(new Date());
		logDao.insert(log);
		return deleteCount;
	}

	@Override
	@Transactional(readOnly=false)
	public Guestbook addGuestbook(Guestbook guestbook, String ip) {
		guestbook.setRegdate(new Date());
		Long id = guestbookDao.insert(guestbook);
		guestbook.setId(id);
		
		Log log = new Log();
		log.setIp(ip);
		log.setMethod("insert");
		log.setRegdate(new Date());
		logDao.insert(log);
		
		return guestbook;
	}

	//페이징 처리
	@Override
	public int getCount() {
		return guestbookDao.selectCount();
	}
}
