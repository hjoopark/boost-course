package kr.or.connect.daoexam.dao;

import static kr.or.connect.daoexam.dao.RoleDaoSqls.DELETE_BY_ROLE_ID;
import static kr.or.connect.daoexam.dao.RoleDaoSqls.SELECT_ALL;
import static kr.or.connect.daoexam.dao.RoleDaoSqls.SELECT_BY_ROLE_ID;
import static kr.or.connect.daoexam.dao.RoleDaoSqls.UPDATE;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.daoexam.dto.Role;	
//쿼리문이 들어있는 클래스에서 쿼리문이 들어있는 변수를 클래스이름 없이 사용할 수 있다.

//저장소인 것을 알려주는 어노테이션
@Repository
public class RoleDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;	//insert를 쓸 수 있게 해주는 simplejdbc
	private RowMapper<Role> rowMapper = BeanPropertyRowMapper.newInstance(Role.class);
	//BeanPropertyRowMapper 이걸로 쿼리값을 자동으로 dto에 저장시킨다. DBMS와 JAVA의 이름을 정하는 규칙을 맞춰줌
	
	public RoleDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("role");	//role테이블에 넣을꺼다.
	}
	
	public List<Role> selectAll(){
		return jdbc.query(SELECT_ALL, Collections.emptyMap(), rowMapper);
		//세번째 파라미터는 select 한건한건을 DTO에 저장하는 목적으로 사용하게 된다
	}
	
	public int insert(Role role) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(role);
		return insertAction.execute(params);
	}

	public int update(Role role) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(role);
		return jdbc.update(UPDATE, params);
	}
	
	public int deleteById(Integer id) {
		Map<String, ?> params = Collections.singletonMap("roleId", id);
		//값이 딱 한건만 넣어서 사용할 때 singleton을 사용! 굳이 값 하나들어가는데 객체를 만들어서 사용하기는 불편함
		return jdbc.update(DELETE_BY_ROLE_ID, params);
	}
	
	public Role selectById(Integer id) {
		try {
			Map<String, ?> params = Collections.singletonMap("roleId", id);	
			//값이 딱 한건만 넣어서 사용할 때 singleton을 사용! 굳이 값 하나들어가는데 객체를 만들어서 사용하기는 불편함
			return jdbc.queryForObject(SELECT_BY_ROLE_ID, params, rowMapper);		
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
}
