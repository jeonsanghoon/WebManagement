package com.mrc.db.config;

import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.member.PersistentLogins;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RememberMeTokenRepository implements PersistentTokenRepository {

	// Logger logger = LoggerFactory.getLogger(getClass());


	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		PersistentLogins data = PersistentLogins.builder().saveType("N").series(token.getSeries())
				.userName(token.getUsername()).token(token.getTokenValue()).build();
		// TODO Auto-generated method stub
		GlobalDao.getPersistentLoginsDao().save(data);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		// TODO Auto-generated method stub
		PersistentLogins data = PersistentLogins.builder().saveType("U").series(series).token(tokenValue).build();
		GlobalDao.getPersistentLoginsDao().save(data);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		// TODO Auto-generated method stub
		PersistentLogins cond = PersistentLogins.builder().series(seriesId).build();
		PersistentLogins data = GlobalDao.getPersistentLoginsDao().getData(cond);

		try {
			PersistentRememberMeToken rtn = new PersistentRememberMeToken(data.getUserName(), data.getSeries(),
																		  data.getToken(), data.getLastUsed());
			return rtn;
		}catch(Exception ex){
			return null;
		}
	}

	@Override
	public void removeUserTokens(String username) {
		// TODO Auto-generated method stub
		PersistentLogins data = PersistentLogins.builder().saveType("D").userName(username).build();
		GlobalDao.getPersistentLoginsDao().save(data);
	}

}
