package com.promineotech.jeep.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;

import lombok.extern.slf4j.Slf4j;

//@Component
@Service
@Slf4j
public class DefaultJeepSalesDao implements JeepSalesDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Override
	public List<Jeep> fetchJeeps(JeepModel model, String trim) {
		log.info("DAO: mode= {}, trim = {}", model, trim);
		
		String sql = ""+ "Select * "
		+ " from models " 
		+ " where model_id = :model_id "
		+ " and trim_level = :trim_level";
		
		Map<String, Object> parms = Map.of("model_id", model.toString(), "trim_level", trim);
		
		return jdbcTemplate.query(sql,parms,new RowMapper<Jeep>() {

			@Override
			public Jeep mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return Jeep.builder()
						.basePrice(rs.getBigDecimal("base_price"))
						.modelId(JeepModel.valueOf(rs.getString("model_id")))
						.modelPk(rs.getObject("model_pk", Long.class))
						.numDoors(rs.getInt("num_doors"))
						.trimLevel(rs.getString("trim_level"))
						.wheelSize(rs.getInt("wheel_size"))
						.build();
			}});
	}

}
